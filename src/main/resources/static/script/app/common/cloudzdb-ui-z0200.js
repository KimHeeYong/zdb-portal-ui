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
	if(data.statefulSets[0]){
		ob.creationTimestamp = data.statefulSets[0].metadata.creationTimestamp; 
	};
	if(data.deploymentStatus!='DEPLOYED' || data.statusMessage != null){
		ob.status = 'GRAY';
	}
	var masterPod = (function(pods){
		var result = {};
		
		for(var i = 0; i < pods.length;i++){
			var pod = pods[i];
			if(data.serviceType == G_SERVICE_TYPE_MARIA && pod.metadata.labels.component === 'master'){
				result = pod;
				break;
			}else if(data.serviceType == G_SERVICE_TYPE_REDIS && pod.metadata.labels.role === 'master'){
				result = pod;
				break;
			};
		};
		return result;
	}(data.pods));
	var readyCondition = gfn_getReadyCondition(masterPod);
	
	ob.lastTransitionTime = readyCondition ? readyCondition.lastTransitionTime:'';
	
	if(ob.creationTimestamp){
		ob.crtTime = moment(ob.creationTimestamp).format('YYYY-MM-DD HH:mm:ss');
	}
	if(ob.lastTransitionTime){
		ob.updTime = moment().diff(moment(ob.lastTransitionTime),'hours') + 'hrs ago';
	}
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
function gfn_getServiceTemplate(ob){    
	var guid = gCommon.getUid();
	var template = '' 
			+'	<div class="Panel service-panel">                                                        '
			+'		<div class="Panel-content" link id="'+guid+'">' //data="{namespace:\'{{namespace}}\',serviceType:\'{{serviceType}}\',serviceName:\'{{serviceName}}\'}"
			+'			<div class="state {{status}}" title="{{statusMessage}}"></div>                                              '
			+'			<div class="service-logo">                                                       '
			+'				<img src="/styles/images/service-img-{{serviceType}}.png" class="service-img">               '
			+'				<p class="ver-info">{{version}}</p>                                              '
			+'			</div>                                                                           '
			+'			<dl class="service-info">                                                        '
			+'				<dt class="proc_serviceDetail link" key="'+guid+'">{{serviceName}}</dt>                                            '
			+'				<dd>                                                                         '
			+'					<div class="tag-wrap">                                                   '
			+'						{{tags}}                                                             '
			+'					<button class="Label add-label tagAdd" key="'+guid+'">추가</button>					 '
			+'					</div>                                                                   '
			+'				</dd>                                                                        '
			+'				<dd class="time-info">                                                       '
			+'					<strong>Uptime</strong> :                                                '
			+'					<span>{{updTime}}</span>                                                '
			+'				</dd>                                                                        '
			+'			</dl>                                                                            '
			+'			<div class="service-btn__wrap">                                                  '
			+'				<button class="Button nobg-btn btn-refresh proc_serviceRestart hide" key="'+guid+'">새로고침</button>                '
			+'				<button class="Button nobg-btn btn-viewdetail">상세보기</button>             '
			+'			</div>                                                                           '
			+'		</div>                                                                               '
			+'	</div> 		                                                                             ';
	for(var key in ob){
		template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	};	
	var tags = '';
	if(ob.tagList){
		for(var i=0;i < ob.tagList.length;i++){
			var tag = ob.tagList[i];
			tags = tags + '<span class="Label Default" data-id="'+tag.id+'">'+tag.tagName+'</span><button class="btn-ico label-del tagDel" data-id="'+tag.id+'">삭제</button>';
		};
	};
	template = template.replaceAll('{{tags}}',tags);
	return template;		
}

//상세 서비스 템플릿
function gfn_getServiceDetailTemplate(ob){
	var template = '' 
		+'			<div class="Panel-content">'
		+'				<div class="state {{status}}" title="{{statusMessage}}"></div>'
		+'				<div class="service-logo">'
		+'					<img src="/styles/images/service-img-{{serviceType}}.png" class="service-img">'
		+'					<p class="ver-info">{{version}}</p>'
		+'				</div>'
		+'				<dl class="service-info">'
		+'					<dt>{{serviceName}}</dt>'
		+'					<dd>'
		+'						<div class="tag-wrap">'
		+'							<span class="Label Default">Tworld</span>'
		+'							<span class="Label Default">html,css</span>'
		+'							<span class="Label Default">html,css</span>'
		+'							<button class="Label add-label">추가</button>'
		+'						</div>'
		+'					</dd>'
		+'					<dd class="time-info">'
		+'						<strong>Uptime</strong> :'
		+'						<span>{{updTime}}</span>'
		+'						<strong class="Margin-left-40">생성일시</strong> :'
		+'						<span>{{crtTime}}</span>'
		+'					</dd>'
		+'				</dl>'
		+'				<div class="service-btn__wrap">'
		+'					<button class="Button nobg-btn btn-refresh hide">새로고침</button>'
		+'					<button class="Button nobg-btn btn-viewdetail">상세보기</button>'
		+'				</div>'
		+'			</div>'
	for(var key in ob){
		template = template.replaceAll('{{'+key+'}}',ob[key]||'');
	};		        
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