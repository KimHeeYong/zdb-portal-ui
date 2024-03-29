$a.page(function(){
	this.init = function(){
		var btnToggle = $('.Button.btn-toggle');
		$(btnToggle).click(function(e){
			e.preventDefault();

			if(btnToggle.hasClass('lnb-close')){
				$(this).parent().parent().addClass('close');
				$(this).parents().find(".lnb-wrap").addClass('close');
				$(this).removeClass('lnb-close').addClass('lnb-open');
				$(this).text('펼치기');
			}else if(btnToggle.hasClass('lnb-open')){
				$(this).parent().parent().removeClass('close');
				$(this).parents().find(".lnb-wrap").removeClass('close');
				$(this).removeClass('lnb-open').addClass('lnb-close');
				$(this).text('접기');
			};
		});
		
		//left sub menu toggle
		var lnbSub = $('nav > ul > li');
		$(lnbSub).find('.lnb-sub').parent().addClass('expandable');
		
		if($(lnbSub).hasClass('expandable')){
			var lnbSubExpand = $('nav > ul > li.expandable > a');
			$(lnbSubExpand).click(function(e){
				e.preventDefault();
				$(this).parent().find('.lnb-sub').slideToggle();
				$(this).parent().toggleClass('expanded');
				$(this).toggleClass('selected');
			});
		}; 
		$('.Accordion >li').on('click', function(e, index){
			$($(e.currentTarget).find('div:visible').find('.alopexgrid')).alopexGrid( "viewUpdate" ); 
		 });
	
		//user box click event
		var user = $('.user');
		var userBox = $('.user-menus');
		userBox.hide();
		$(user).click(function(e){
			e.preventDefault();
			if(userBox.css('display') === 'none') {
				userBox.show();
				$(user).addClass('close');
			} else {
				userBox.hide();
				$(user).removeClass('close');
			}
		});
	}
});
var gOverlay = null;
var gParam = {};
var G_NAMESPACE_ALL = "_DEFAULT_ALL";
var G_GLOBAL = 'global';
var gConfigColumn = ['free_resource_check','public_network_enabled','backup_duration','backup_time','auto_failover_enabled'];
var gSelectedNamespace = null;
var gPopData = '';
var gIgnoreOverlay = false;
var gNamespaceList;
var gCommon = $a.page(function(){ 
	this.init = function(){ //초기화 루틴 수행
		gSelectedNamespace = $.cookie('selectedNamespace')||G_NAMESPACE_ALL;
		$a.ajax = function(opt){
			var defOpt = {
				type:"get",
				dataType:'json',
			    error: function(res) { // if error occured
			    	if(res.status == 0){
		    			location.href = location.href;
			    	}else{
			    		gCommon.alert('시스템 에러가 발생하였습니다. <br/>');
			    	}
			    },
			    beforeSend:function(bopt){
			    	var loadingMessage = '정보 로딩중입니다.';
			    	if(opt && opt.loadingMessage){
			    		loadingMessage = opt.loadingMessage;
			    	};
			    	$('#loading-message').html(loadingMessage);
			    	gIgnoreOverlay = opt.ignoreOverlay;
			    },
			    complete:function(copt){
			    	
			    },
			    fail: function(res){
			    	console.log(res);
			    }
			};
			var option = $.extend({},defOpt,opt);
			let errMessage = '서비스가 일시적으로 사용이 불가능 합니다.<br/> 장기간 현상이 발생할 경우에 '+
		      '<a href="https://support.cloudz.co.kr/support/tickets/new" target="_blank">티켓</a>을 통해서 <br/>접수를 해주시기 바랍니다.';
			if(opt.success){
				option.success = function(res){
					if(res && res.result && (res.result.resultCode == 'exception')){
						let re = res.result.resultMessage|| errMessage;
						if(re.indexOf('java.lang')> -1){
							re = errMessage; 
						}
						gCommon.alert(re);							
					}else if(res && res.result && (res.result.code == 4)){
						let re = res.result.message|| errMessage;
						if(re.indexOf('java.lang')> -1){
							re = errMessage;
						} else {
							re = res.result.message;
						}
						gCommon.alert(re);													
                        opt.success(res);
					}else{
						opt.success(res);
					}
				};
			};
			return $.ajax(option);
		}
		$(document).ajaxStart(function(as){
			if(gIgnoreOverlay != true){
				gCommon.overlay();
			}
		}).ajaxStop(function(ae) { 
			gCommon.removeOverlay();
		});
		//gParam 에 get파라미터 셋팅
		gParam = this.getParameterJson();
		//AlopexGrid 디폴트 셋팅 ( contextMenu disable)
		AlopexGrid.setup({
			enableContextMenu : false,
			enableDefaultContextMenu : false,
			fitTableWidth :true
		});
	};
	this.setSelectedNamespace = function(val){
		gSelectedNamespace = val;
		gCommon.setCookie('selectedNamespace',val);
	};
	this.setCookie = function(key,val){
		$.cookie(key,val,{path:'/'});
	};
	this.movePage = function(url,param,isPost){
		var method = isPost ? 'post': 'get';
		if(!url){
			console.log('빈 url');
			return;
		};
		var form = document.createElement('form');
		for(var key in param){
			var objs = document.createElement('input');
			objs.setAttribute('type', 'hidden');
			objs.setAttribute('name',  key);
			objs.setAttribute('value', param[key]);
			form.appendChild(objs);
		};
		form.setAttribute('method', method);
		form.setAttribute('action', url);
		document.body.appendChild(form);
		form.submit();

	};
	this.getParameterJson = function(){
	    var vars = {};
	    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
	        vars[key] = value;
	    });
	    return vars;
	}
	this.overlay = function(){
		gOverlay = $('body #loading').show();
	};
	this.removeOverlay = function(){
		$('body #loading').hide();
	};
	this.setDataAttrs = function(selector,data){
		for(var key in data){
			selector.attr("data-"+ key.replace(/([A-Z])/g, function($1){return "-"+$1.toLowerCase();}),data[key])
		}
	};
	//네임스페이스 제외 
	var hardFilterList = ['default','ibm-cert-store','ibm-system','kube-public','kube-system'];
	this.getNamespaceCombo = function(selector,options){
		let deferred = $.Deferred();
		var defOpt = {incAll:true,incAdminAll:false};
		var opt = $.extend({},defOpt,options);
		//return selector.setDataSource([{id:'fsk-db',text:'fsk-db'}]); //dwtemp
		$a.ajax({
			url: '/zdbapi/getNamespaces',
			success:function(res){
				var list = res.namespaces;
				var namespaceList = [];
				if(list && list.length > 0){
					for(var i = 0 ; i < list.length; i++){
						var ob = list[i];
						if(hardFilterList.indexOf(ob.metadata.name) > -1){
							continue;
						};
						namespaceList.push({
							id:ob.metadata.name,
							text:ob.metadata.name
						});
					};
					gNamespaceList = namespaceList.slice();
					if(opt.incAdminAll){//admin일 경우에만 전체 보이도록 
						if(gIsClusterAdmin){
							namespaceList.push({
								id:G_NAMESPACE_ALL,
								text:'전체'
							});
						}
					} else if(opt.incAll){
						namespaceList.push({
							id:G_NAMESPACE_ALL,
							text:'전체'
						});
					}
				};
				if(selector){
					selector.setDataSource(namespaceList);
				}
				deferred.resolve(selector);
			}
		});	
		return deferred.promise();
	};
	
	this.getConfigData = function(pNamespace){
		let deferred = $.Deferred();
		let namespace = pNamespace||gSelectedNamespace;
		if(namespace == G_NAMESPACE_ALL) namespace = G_GLOBAL;
		let re = {};
		
		gCommon.getConfigDataAjax(namespace).done(function(result){
			if(result == null){
				gCommon.getConfigDataAjax(G_GLOBAL).done(function(result){
					re = result || {};
					re.isExists = false;
					deferred.resolve(re);
				});				
			}else{
				re = result;
				re.isExists = true;
				deferred.resolve(re);
			}			
		});
		return deferred.promise();
	}	
	this.getConfigDataAjax = function(namespace){
		let deferred = $.Deferred();
		let result = null;
		$a.ajax({
			url : '/zdbapi/getZDBConfig',
			data : {
				namespace : namespace
			},
			success : function(res) {
				let list = res.zdbConfig || [];
				
				if(list.length == 0){
					result = null;
				}else{
					result = {};
					for(let i = 0 ; i < list.length;i++){
						let ob = list[i];
						if(gConfigColumn.indexOf(ob.config) > -1){
							result[ob.config] = ob.value;	
						}
					}
				}
				deferred.resolve(result);
			}
		});	
		return deferred.promise();
	}
	this.copyToClipboard = function(selector) {
		if(!selector)return;
		if(selector.val()==''){
			selector.val(' ')
		};
		
		var result = '';
		var befType = selector.attr('type');
		var befVal = selector.val();
		if(befType=='password'){
			selector.attr('type','text');
		};
		selector.val(selector.val().replace('[password]',$('#credential').val()));
		selector.get(0).select();
		result = document.execCommand('copy')
		selector.attr('type',befType);
		selector.val(befVal);
		return result;
	};
	this.getUid = function(){
	    var d = new Date().getTime();
	    var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	        var r = (d + Math.random()*16)%16 | 0;
	        d = Math.floor(d/16);
	        return (c=='x' ? r : (r&0x3|0x8)).toString(16);
	    });
	    return uuid;
	};
	this.getObjectByIdx = function(obj,idx){
		var result = null;
		var i = 0;
		for(var key in obj){
			if(i++ == idx){
				result = obj[key];
			};
		};
		return result;
	};
	this.findObjectByKeyOb = function(listOb,keyOb){
		var result = null;
		if(listOb){
			for(var i = 0 ; i < listOb.length;i++){
				var ob = listOb[i];
				var isEqual = true;
				for(var key in keyOb){
					if(ob[key]!=keyOb[key]){
						isEqual = false;
						break;
					};
				};
				if(isEqual){
					result = ob;
					break;
				};
			};
		}
		return result;
	};
	this.findObjectByKeyObUnder = function(listOb,keyOb){		
		var result = null;
		if(listOb){
			for(var i = 0 ; i < listOb.length;i++){
				var ob = listOb[i];
				var isEqual = true;
				for(var key in keyOb){
					var obName = String(ob[key]).replaceAll('-','_');
					var keyName = String(keyOb[key]).replaceAll('-','_');					
					if(obName != keyName){
						isEqual = false;
						break;
					};
				};
				if(isEqual){
					result = ob;
					break;
				};
			};
		}
		return result;
	};		
	let isConfirmPopExists = false;
	this.confirm = function(message,trueCallback,opt){
		if(isConfirmPopExists)return;
		gPopData = {};
		opt = opt||{};
		gPopData.message = message;
		gPopData.okBtnMsg = opt.okBtnMsg; 
		gPopData.noBtnMsg = opt.noBtnMsg; 
	    $a.popup({
	        url: "/zdbcom/confirm",
	        iframe: false,
	        width: 500,
	        height: 300,
	        title : '',
	        callback : function(res){
	        	if(res == "Y"){
	        		trueCallback(res);
	        	}else{
		        	if(opt.falseCallback){
		        		opt.falseCallback(res);	
		        	}
	        	};
	        	isConfirmPopExists = false;
	        }
	    });
	    isConfirmPopExists = true;
	};
	this.confirmYn = function(message,callback,opt){
		if(isConfirmPopExists)return;
		gPopData = {};
		opt = opt||{};
		gPopData.message = message;
		gPopData.okBtnMsg = opt.okBtnMsg; 
		gPopData.noBtnMsg = opt.noBtnMsg; 

		let vWidth = 500 ;
		let vHeight = 300 ;
		
		if(opt.vWidth != null && opt.vWidth != '' ){
			vWidth =  opt.vWidth ;
		}
		
		if(opt.vHeight != null && opt.vHeight != '' ){
			vHeight =  opt.vHeight ;
		}
		
		
	    $a.popup({
	        url: "/zdbcom/confirm",
	        iframe: false,
	        width: vWidth,
	        height: vHeight,
	        title : '',
	        callback : function(res){
	        	callback(res);
	        	isConfirmPopExists = false;
	        }
	    });
	    isConfirmPopExists = true;
	};	
	this.credentialConfirm = function(option){
		let defOpt = {
		        url: "/zdb02/zdb0200p03",
		        data:{credential:$("#credential").val(), msg:'설정을 변경하시려면'},
		        iframe: false,
		        width: 500,
		        height: 300,
		        movable:true,
		        title : ''
		    };		
		let opt = $.extend({},defOpt,option);
		if(opt.callback){
			opt.callback=function(res){
				if(res == 'Y'){
					option.callback(res);
				}
			}
		}
	    $a.popup(opt);		
	}
	this.credentialApiConfirm = function(option){
		let defOpt = {
		        url: "/zdbcom/credentialConfirm",
		        data:{namespace:option.data.namespace ,serviceType:option.data.serviceType ,serviceName:option.data.serviceName, msg:'설정을 변경하시려면'},
		        iframe: false,
		        width: 500,
		        height: 300,
		        movable:true,
		        title : ''
		    };		
		let opt = $.extend({},defOpt,option);
		if(opt.callback){
			opt.callback=function(res){
				if(res == 'Y'){
					option.callback(res);
				}
			}
		}
	    $a.popup(opt);
	}	
	this.adminPwConfirm = function(option){
		let defOpt = {
		        url: "/zdb02/zdb0200p10",
		        data:{password:'1234', msg:'설정을 변경하시려면'},
		        iframe: false,
		        width: 500,
		        height: 300,
		        movable:true,
		        title : ''
		    };		
		let opt = $.extend({},defOpt,option);
		if(opt.callback){
			opt.callback=function(res){
				if(res == 'Y'){
					option.callback(res);
				}
			}
		}
	    $a.popup(opt);		
	}
	let isAlertPopExists = false;
	this.alert = function(message,fnCallback,option){
		if(isAlertPopExists)return;
		let defOpt = {
		        url: "/zdbcom/alert",
		        data:message,
		        iframe: false,	        
		        width: 500,
		        height: 230,
		        title : '',
		        callback: function(){
		        	isAlertPopExists = false;
		        	if(fnCallback){
		        		fnCallback();
		        	}
		        }
		    };
		let opt = $.extend({},defOpt,option);
		gPopData = {};
		gPopData.message = message;
		isAlertPopExists = true;
		$a.popup(opt);
	};
	
	this.backupAlert = function(message,fnCallback,option){
		if(isAlertPopExists)return;
		let defOpt = {
		        url: "/zdbcom/backupAlert",
		        data:message,
		        iframe: false,	        
		        width: 500,
		        height: 230,
		        title : '',
		        callback: function(){
		        	isAlertPopExists = false;
		        	if(fnCallback){
		        		fnCallback();
		        	}
		        }
		    };
		let opt = $.extend({},defOpt,option);
		gPopData = {};
		gPopData.message = message;
		isAlertPopExists = true;
		$a.popup(opt);
	};	
});

$.fn.extend({ 
    zdbui_setContents :  function(data){ //셀렉터의 영역에서 key 값을 가진 컴포넌트들에 값을 자동으로 매핑
    	if(!data)return;
    	for(var key in data){
    		var ob = $(this).find('[data='+key+'],[name='+key+']');
    		if(ob.size() > 0){
    			ob.each(function(index,item){
    				$(item).zdbui_setComponent(data[key]);
    			});
    		}
    	}
    	return this;
    },
    zdbui_setComponent : function(val){
    	var item = this;
    	
    	var tagName = item[0].tagName.toLowerCase();
    	if(tagName == 'div' && item.hasClass('Divselect')){
    		tagName='divSelect';
    	};
		if('div,span,td,textarea'.indexOf(tagName) > -1){
			item.html(val);
		}else if('input' == tagName){
			if(item.attr('type') == 'text' || item.attr('type') == 'password'){
				item.val(val);
			}else if(item.attr('type') == 'radio'){
				item.filter('[value='+val+']').prop('checked',true);
			}else if(item.attr('type') == 'checkbox'){
				item.filter('[value='+val+']').prop('checked',true);
			}
		}else if('select' == tagName){
			item.val(val+'');
		}else if('divSelect' == tagName){
			item.setSelected(val+'');
		}else{
			item.val(val);
		};
		return this;
    },
    serializeObject : function() {
    	var a = {}, b = function(b, c) {
    		var d = a[c.name];
    		"undefined" != typeof d && d !== null ? $.isArray(d) ? d.push(c.value)
    				: a[c.name] = [ d, c.value ] : a[c.name] = c.value
    	};
    	return $.each(this.serializeAll(), b), a;
    },
    serializeAll : function () {
	    var data = $(this).serializeArray();
	
	    $(':disabled[name]', this).each(function () { 
	        data.push({ name: this.name, value: $(this).val() });
	    });
	    $('div.Divselect', this).each(function () { 
	    	var tob = $(this).find('select');
	    	data.push({name:this.id||$(this).attr('name'),value:tob.val()});
	    });
	    
	    return data;
	},
	pressEnter : function(fn){
		this.each(function(){
			$(this).on('keypress',function(evt){
				if(evt.which == '13' && fn){
					fn(evt);
				};
			})
		})
		return this;
	}
});
