function gfn_convertServiceDatas(datas){
	if(!datas) return datas;
	
	for(var i = 0 ; i < datas.length;i++){
		datas[i] = gfn_convertServiceData(datas[i]);
	};
	return datas;	
}
function gfn_convertServiceData(data){
	var ob = {};
	if(!data)return {};
	if(data.serviceName){
		ob.shortServiceName = data.serviceName.replace(data.namespace+'-','');
	};
	//상태값 GRAY 설정
	if(data.deploymentStatus!='DEPLOYED'){
		ob.status = 'GRAY';
	};
	
	//생성 시간 설정
	if(data.statefulSets[0]){
		ob.creationTimestamp = data.statefulSets[0].metadata.creationTimestamp;
		if(ob.creationTimestamp){
			ob.crtTime = moment(ob.creationTimestamp).format('YYYY-MM-DD HH:mm:ss');
		}
	};
	var masterPod = (function(pods){
		var result = {};
		
		for(var i = 0; i < pods.length;i++){
			var pod = pods[i];
			if(data.serviceType == cServiceType.mariadb && pod.metadata.labels.component === 'master'){
				result = pod;
				break;
			}else if(data.serviceType == cServiceType.redis && pod.metadata.labels.role === 'master'){
				result = pod;
				break;
			};
		};
		return result;
	}(data.pods));
	
	if(masterPod && masterPod.metadata){
		ob.podName = masterPod.metadata.name;
		var readyCondition = gfn_getReadyCondition(masterPod);
		ob.lastTransitionTime = readyCondition ? readyCondition.lastTransitionTime:'';
		 //마지막 구동시간 설정
		if(ob.lastTransitionTime){ 
			ob.updTime = moment().diff(moment(ob.lastTransitionTime),'hours') + 'hrs ago';
		};
	}
	//cpu 값 설정
	if(data.resourceSpecOfPodMap){
		ob.resourceSpecOfPodMap = data.resourceSpecOfPodMap;
		for(var idx in ob.resourceSpecOfPodMap){
			var pm = ob.resourceSpecOfPodMap[idx];
			if(pm.cpu.indexOf('m') < 0){
				pm.cpu = (pm.cpu * 1000) + 'm'; 
			};
		};
	};
	var tagList = [];
	for(var i = 0 ; i < data.tags.length;i++){
		tagList.push(data.tags[i].tagName);
	};
	ob.tagNames = tagList.join();
	ob.status = (data.status||'').toLowerCase();
	return $.extend({},data,ob);
}
function gfn_getReadyCondition(pod){
	var result = null;
	if(!pod || !pod.status)return result;
	for(var i = 0 ; i < pod.status.conditions.length;i++){
		var condition = pod.status.conditions[i];
		if(condition.type === 'Ready'){
			result = condition; 
			break;
		};
	};
	return result;
}

//서비스 템플릿
function gfn_getServiceTemplate(ob,msg){    
	var guid = gCommon.getUid();	
	var template = '' 
			+'	<div class="Panel service-panel">                                                        '
			+'		<div class="Panel-content" link id="'+guid+'">'
			+'			<span class="state {{status}}"></span>                                             '
			+ (ob.statusMessage?'<div class="Tooltip wd300">{{statusMessage}}</div>':'') 
			+'			<div class="service-logo">                                                       '
			+'				<img src="/styles/images/service-img-{{serviceType}}.png" class="service-img">               '
			+'				<p class="ver-info">{{version}}</p>                                              '
			+'			</div>                                                                           '
			+'			<dl class="service-info">                                                        '
			+'				<dt class="proc_serviceDetail link" key="'+guid+'"><p class="service-name">{{namespace}}</p>{{shortServiceName}}</dt>      '
			+'				<dd>                                                                         '
			+'					<div class="tag-wrap">                                                   '
			+'						{{tags}}                                                             '
			+'					<button class="Label add-label tagAdd hide" key="'+guid+'">'+msg['add']+'</button>					 '
			+'					<span class="add-label__input hide"><input class="Textinput tagAddInput"  key="'+guid+'" ></input></span>'
			+'					</div>                                                                   '
			+'				</dd>                                                                        '
			+'				<dd class="time-info">                                                       '
			+'					<strong>'+msg['elapsedTime']+' </strong> :                                                '
			+'					<span>{{elapsedTime}}</span>                                                '
			+'				</dd>                                                                        '
			+'			</dl>                                                                            '
			+'			<div class="service-btn__wrap">                                                  ';
			if(gIsZdbAdmin && ob.status == 'gray'){
				template = template 
				+'              <button class="btn-ico btn-del__typeb proc_serviceDelete" key="'+guid+'" title="'+msg['del']+'">'+msg['del']+'</button>         '
				+'				<button class="btn-ico btn-refresh proc_serviceRestart" key="'+guid+'" title="'+msg['restart']+'">'+msg['restart']+'</button>                '
			}
			template = template 
			+'				<button class="btn-ico btn-viewdetail viewMonitor" key="'+guid+'" title="'+msg['monitoring']+'">'+msg['monitor']+'</button>             '
			+'			</div>                                                                           '
			+'		</div>                                                                               '
			+'	</div> 		                                                                             ';
	for(var key in ob){
		template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	};	
	var tags = '';
	var ml = 3;//태그 최대 갯수
	if(ob.tagList){
		for(var i=0;i < ob.tagList.length;i++){
			if(--ml < 0)break;
			var tag = ob.tagList[i];
			tags = tags + '<span class="Label Default" data-id="'+tag.id+'">'+tag.tagName+'</span>'
		};
	};
	template = template.replaceAll('{{tags}}',tags);
	return template;		
}

//상세 서비스 템플릿
function gfn_getServiceDetailTemplate(ob,msg){
	var template = '' 
		+'			<div class="Panel-content">'
		+'				<div class="state {{status}}"></div>'
		+'				<div class="service-logo">'
		+'					<img src="/styles/images/service-img-big-{{serviceType}}.png" class="service-img">'
		+'					<p class="ver-info">{{version}}</p>'
		+'				</div>'
		+'				<dl class="service-info">'
		+'					<dt>{{serviceName}}</dt>'
		+'					<dd>'
		+'						<div class="tag-wrap">'
		+'							{{tags}}'
		+'							<button class="Label add-label tagAdd">'+msg['add']+'</button>'
		+'					        <span class="add-label__input hide"><input type="hidden"/><input class="Textinput tagAddInput" name="tagN" autocomplete="false"></input></span>'
		+'						</div>'
		+'					</dd>'
		+'					<ul class="time-info">'
		+'						<li>'+msg['createTime']+': <span>{{crtTime}}</span> </li>'
		+'						<li>'+msg['elapsedTime']+': <span>{{elapsedTime}}</span> </li>'
		+'					</ul>'
		+'				</dl>'
		+'				<div class="service-btn__wrap">'
		+'					<button class="Button nobg-btn btn-refresh hide">'+msg['restart']+'</button>'
		+'					<button class="Button nobg-btn btn-viewdetail hide">'+msg['detail']+'</button>'
		+'				</div>'
		+'			</div>'
	for(var key in ob){
		template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	};		        
	var tags = '';
	var nDelMsg = '#redis#mariadb#session#data';
	if(ob.tagList){
		for(var i=0;i < ob.tagList.length;i++){
			var tag = ob.tagList[i];
			tags = tags + '<span class="Label Default" data-id="'+tag.id+'">'+tag.tagName+'</span>';
			if(nDelMsg.indexOf('#'+tag.tagName) < 0){
				tags = tags + '<button class="btn-ico label-del tagDel" data-id="'+tag.id+'">삭제</button>';
			};			
		};
	};
	template = template.replaceAll('{{tags}}',tags);
	return template;
}

//생성 서비스 템플릿
function gfn_getMakeServiceTemplate(ob){
	var template = '' 
          +'<div class="Panel service-panel make-box" make>					'
	        +'	<div class="Panel-content"  namespace="{{namespace}}">                                                             '
	        +'		<a href="#" class="new-make">서비스생성 <span class="Icon make-plus"></span></a>    '
	        +'	</div>                                                                                  '
	        +'</div> ';
	 for(var key in ob){
		 template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	 };		        
	 return template;
}

//네임스페이스 리소스 템플릿
function gfn_getNamespaceResourceTemplate(ob){
	if(!ob)return '';
	
	var template = ''
		+'<ul class="name-progress__wrap2">                                                           '
		+'	<li>                                                                                     '
		+'		<p class="name-pro__title">CPU</p>                                           '
		+'		<span class="name-pro__info2">{{usedCpuRequests}}/{{hardCpuRequests}}</span>                                              '
		+'		<div class="Progressbar name-typec" data-default="{{cpuRequestsPercentage}}" data-type="progressbar"></div> '
		+'	</li>                                                                                    '
		+'	<li>                                                                                     '
		+'		<p class="name-pro__title">Memory</p>                                        '
		+'		<span class="name-pro__info2">{{usedMemoryRequests}}/{{hardMemoryRequests}}</span>                                       '
		+'		<div class="Progressbar name-typec" data-default="{{memoryRequestsPercentage}}" data-type="progressbar"></div> '
		+'	</li>                                                                                    '
		+'</ul>                                                                                      '
	 for(var key in ob){
		 template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	 };		        
	 return template;	
}
function gfn_getConnectionTemplate(ob){
	var template = ''                                                                                                     
	               +'<tr>                                                                                                 '
	               +'	<th rowspan="2">{{role}}({{connectionType}})</th>                                                                       '
	               +'	<th>Connection String</th>                                                                        '
	               +'	<td>                                                                                              '
	               +'		<input class="Textinput Width-70" name="connectionString" readonly="readonly" value="{{connectionString}}"></input>     '
	               +'		<button class="Button line-btn connectionInfoCopy">복사</button>                              '
	               +'		<p class="hide connectionInfoCopyed">                                                         '
	               +'			<span class="Color-warning">복사되었습니다.</span>                                        '
	               +'		</p>                                                                                          '
	               +'	</td>                                                                                             '
	               +'</tr>                                                                                                '
	               +'<tr>                                                                                                 '
	               +'	<th>Connection Line</th>                                                                          '
	               +'	<td>                                                                                              '
	               +'		<input class="Textinput Width-70" name="connectionLine" readonly="readonly" value="{{connectionLine}}"></input>        '
	               +'		<button class="Button line-btn connectionInfoCopy">복사</button>                              '
	               +'		<p class="hide connectionInfoCopyed">                                                         '
	               +'			<span class="Color-warning">복사되었습니다.</span>                                        '
	               +'		</p>                                                                                          '
	               +'	</td>                                                                                             '
	               +'</tr>                                                                                                ';
	 for(var key in ob){
		 template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	 };	
	return template;
}

function fn_setSelected2010Tab(val){
	gCommon.setCookie('selected2010tab',val);
}
function fn_setSelected2000ListType(val){
	gSelectedListType = val;
	gCommon.setCookie('selected2000listType',val);
	
	$('#divServiceListBtns').find('button').removeClass('Checked');
	$('#divServiceListBtns').find('button[data-list-type='+val+']').addClass('Checked');
	
	$('#divServiceList').find('div[data-list-type]').hide();
	$('#divServiceList').find('div[data-list-type='+val+']').show();
	$("#divServiceListGrid").alopexGrid( "viewUpdate" );
}