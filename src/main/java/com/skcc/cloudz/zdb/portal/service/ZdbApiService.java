package com.skcc.cloudz.zdb.portal.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zdb.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zdb.common.component.ZdbRestConnector;
import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.util.StringUtil;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.config.URIConstants;
import com.skcc.cloudz.zdb.portal.domain.dto.ApiTemplate;
import com.skcc.cloudz.zdb.portal.domain.dto.NamespaceResource;
import com.skcc.cloudz.zdb.portal.domain.dto.Result;
import com.skcc.cloudz.zdb.portal.domain.dto.ZdbRestDTO;
import com.zdb.core.domain.BackupEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.DBUser;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.RequestEvent;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ScheduleInfoEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;
import com.zdb.core.domain.ZDBConfig;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.dsl.PodResource;

@Component
public class ZdbApiService{

	@Value("${zdb-api-server.url}") String apiServer;
	@Value("${zdb-demon-server.url}") String demonServer;
	@Value("${props.iam.baseUrl}") String iamBaseUrl;
    @Autowired SecurityService securityService;
    @Autowired ZdbRestConnector connector;
    
	public List<Namespace> getNamespaces(Map<String,String> param) {
		List<Namespace> list = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_GET_NAMESPACES, HttpMethod.GET, null,  ZdbRestDTO.class).getBody();
		
		if(zdbRestDTO != null && zdbRestDTO.getResult() != null) {
			list = zdbRestDTO.getResult().getNamespaces();
		};
		
		return list;
	}

	public NamespaceResource getNamespaceResource(Map<String, String> param) {
		NamespaceResource result = null;
		List<NamespaceResource> list = Collections.emptyList();
		param.put(CommonConstants.USER_ID, securityService.getUserDetails().getUserId());
		ApiResponseVo responseDTO = connector.getForObject(iamBaseUrl + URIConstants.URI_GET_NAMESPACE_RESOURCE, ApiResponseVo.class,param);
		
		if(responseDTO != null && responseDTO.getData() != null) {
			ObjectMapper mapper = new ObjectMapper();
			list = mapper.convertValue(responseDTO.getData().get("items"), new TypeReference<List<NamespaceResource>>(){});
			result = list.stream()
					.filter((namespaceResource)-> param.get(CommonConstants.NAMESPACE).equals(namespaceResource.getName()))
					.findAny()
					.orElse(null);
		};
		
		return result;
	}
	
	public List<ServiceOverview> getServices(Map<String,String> param) {
		List<ServiceOverview> list = Collections.emptyList();
		String url = URIConstants.URI_GET_SERVICES_WITH_NAMESPACE; 
		if(CommonConstants.NAMESPACE_ALL.equals(param.get(CommonConstants.NAMESPACE))) {
			//dwtemp 임시 네임스페이스 적용
			String namespaces = connector.getSessionNamespaces();

			param.put(CommonConstants.NAMESPACE, namespaces);
		};
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + url, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceoverviews();
		};
		
		return list;
	}

	public List<Tag> getTags(Map<String,String> param) {
		List<Tag> list = Collections.emptyList();
		String namespaces = param.get(CommonConstants.NAMESPACE);
		String url = CommonConstants.NAMESPACE_ALL.equals(namespaces) ? URIConstants.URI_GET_TAGS : URIConstants.URI_GET_TAGS_WITH_NAMESPACE;
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + url, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getTags();
		};
		
		return list;
	}
	public ServiceOverview getServiceoverview(Map<String, String> param) {
		ServiceOverview ob = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_SERVICE, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getServiceoverview();
		};
		
		return ob;
	}
	
	public ConnectionInfo getConnectionInfo(Map<String, String> param) {
		ConnectionInfo ob = null;
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_CONNECTION_INFO, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getConnectionInfo();
		};
		return ob;
	}
	
	public int getNodeCount(Map<String,String> param) {
		int ob = 0;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_NODE_COUNT, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getNodes();
		};
		return ob;
	}
	
	//restartService
	public ZdbRestDTO restartService(Map<String, String> param) {
		return connector.getForObject(apiServer + URIConstants.URI_RESTART_SERVICE, ZdbRestDTO.class,param);
	}
	//restartService
	public ZdbRestDTO restartPod(Map<String, String> param) {
		return connector.getForObject(apiServer + URIConstants.URI_RESTART_POD, ZdbRestDTO.class,param);
	}

	@SuppressWarnings("rawtypes")
	public PodResource getPodResource(Map<String, String> param) {
		PodResource ob = null;
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_POD_RESOURCE, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getPodResource();
		};
		return ob;
	}
	
	public Result getPodMetrics(Map<String, String> param) {
		Result ob = null;
		
		try {
			ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_POD_METRICS, ZdbRestDTO.class,param);
			if(zdbRestDTO != null) {
				ob = zdbRestDTO.getResult();
			};
		} catch (Exception e) {
			// podMetrics error 무시하도록 처리
		}
		return ob;
	}

	public Result getDBVariables(Map<String, String> param) {
		Result ob = null;
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_DB_VARIABLES, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult();
		};
		return ob;
	}

	public Result updateDBVariables(Map<String, String> param) {
		Result ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_UPDATE_DB_VARIABLES, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	    
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult();
		}; 
		return ob;
	}

	public Result updateConfig(Map<String, String> param) {
		Result ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_UPDATE_CONFIG, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	    
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult();
		}; 
		return ob;
	}
	public ZdbRestDTO createDeployment(Map<String, String> param) {
	    StringBuffer createTemplate = new StringBuffer();
	    Pattern expr = Pattern.compile(ApiTemplate.MATCHER_PATTERN);
		Matcher mat = expr.matcher(ApiTemplate.getCreateDeployment(param.get(CommonConstants.SERVICE_TYPE)));
		while (mat.find()) {
			mat.appendReplacement(createTemplate, StringUtil.doubleQuote(param.get(mat.group(1))));
		};
		mat.appendTail(createTemplate);
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<String> entity = new HttpEntity<>(createTemplate.toString(), headers);
		return connector.exchange(apiServer + URIConstants.URI_CREATE_DEPLOYMENT, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
	}
	public ZdbRestDTO updateScaleUp(Map<String, String> param) {
		StringBuffer updateScaleTemplate = new StringBuffer();
		Pattern expr = Pattern.compile(ApiTemplate.MATCHER_PATTERN);
		Matcher mat = expr.matcher(ApiTemplate.getUpdateScaleUpTemplate(param.get(CommonConstants.SERVICE_TYPE)));
		while (mat.find()) {
			mat.appendReplacement(updateScaleTemplate, StringUtil.doubleQuote(param.get(mat.group(1))));
		};
		mat.appendTail(updateScaleTemplate);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(updateScaleTemplate.toString(), headers);
		return connector.exchange(apiServer + URIConstants.URI_UPDATE_SCALE_UP, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	}
	public ZdbRestDTO updateScaleOut(Map<String, String> param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,String>> entity = new HttpEntity<>(param, headers);
		return connector.exchange(apiServer + URIConstants.URI_UPDATE_SCALE_OUT, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	}

	public ZdbRestDTO setNewPassword(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = connector.exchange(apiServer + URIConstants.URI_SET_NEW_PASSWORD, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO deleteServiceInstance(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = connector.exchange(apiServer + URIConstants.URI_DELETE_SERVICE_INSTANCE, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO createTag(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = connector.exchange(apiServer + URIConstants.URI_CREATE_TAG, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO deleteTag(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = connector.exchange(apiServer + URIConstants.URI_DELETE_TAG, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}
	
	public ZdbRestDTO createPublicService(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
		ob = connector.exchange(apiServer + URIConstants.URI_CREATE_PUBLIC_SERVICE, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}
	
	public ZdbRestDTO deletePublicService(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
		ob = connector.exchange(apiServer + URIConstants.URI_DELETE_PUBLIC_SERVICE, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public List<EventMetaData> getEvents(Map<String, String> param) {
		List<EventMetaData> list = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_EVENTS,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceEvents();
		};
		
		return list;
	}
	public List<DBUser> getUserGrants(Map<String, String> param) {
		List<DBUser> list = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_USER_GRANTS,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getUserGrants();
		};
		
		return list;
	}

	public List<RequestEvent> getOperationEvents(Map<String, String> param) {
		List<RequestEvent> list = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_OPERATION_EVENTS,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getOperationEvents();
		};
		
		return list;
	}
	
	public String[] getPodLog(Map<String, String> param) {
		String[] result = {};
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_POD_LOG,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getPodLog();
		};
		return result;
	}

	public String[] getSlowLog(Map<String, String> param) {
		String[] result = {};
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_SLOW_LOG,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getSlowLog();
		};
		return result;
	}	

	public String[] getMycnf(Map<String, String> param) {
		String[] result = {};
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_MY_CNF,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getMycnf();
		};
		return result;
	}
	public List<String> getAllDBVariables(Map<String, String> param) {
		List<String>  result = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_ALL_DB_VARIABLES,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = (List<String>)zdbRestDTO.getResult().getRedisConfig();
		};
		return result;
	}
	
	public List<BackupEntity> getBackupList(Map<String, String> param) {
		List<BackupEntity> result = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_BACKUP_LIST,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getBackupList();
		};
		return result;
	}

	public List<ScheduleInfoEntity> getScheduleInfoList(Map<String, String> param) {
		List<ScheduleInfoEntity> result = null;
		
		if(CommonConstants.NAMESPACE_ALL.equals(param.get(CommonConstants.NAMESPACE))) {
			param.put(CommonConstants.NAMESPACE, "all");
		};
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_SCHEDULE_INFO_LIST,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getScheduleInfoList();
		};
		return result;
	}
	
	public ScheduleEntity getSchedule(Map<String, String> param) {
		ScheduleEntity result = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_SCHEDULE,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getSchedule();
		};
		return result;
	}

	public ZdbRestDTO updateSchedule(Map<String, String> param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,String>> entity = new HttpEntity<>(param, headers);
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_UPDATE_SCHEDULE, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
		
		return zdbRestDTO;		
	}

	public ZdbRestDTO updateBackup(Map<String, String> param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,String>> entity = new HttpEntity<>(param, headers);
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_UPDATE_BACKUP, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
		
		return zdbRestDTO;		
	}

	public ZdbRestDTO deleteBackup(Map<String, String> param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,String>> entity = new HttpEntity<>(param, headers);
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_DELETE_BACKUP, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		
		return zdbRestDTO;	
	}

	public ResponseEntity<byte[]> downloadBackup(Map<String, String> param) {
		 connector.getMessageConverters().add( new ByteArrayHttpMessageConverter());

		 HttpHeaders headers = new HttpHeaders();
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		 HttpEntity<String> entity = new HttpEntity<String>(headers);
		 
		 ResponseEntity<byte[]> response = connector.exchange(demonServer + URIConstants.URI_DOWNLOAD_BACKUP, HttpMethod.GET, entity, byte[].class, param);
		 return response;
	}

	public ZdbRestDTO restoreFromBackup(Map<String, String> param) {
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_RESTORE_FROM_BACKUP,ZdbRestDTO.class,param);
		
		return zdbRestDTO;
	}	
	public ZdbRestDTO isAvailable(Map<String, String> param) {
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_IS_AVAILABLE,ZdbRestDTO.class,param);

		return zdbRestDTO;
	}
	public ZdbRestDTO updateUserNamespaces() {
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_UPDATE_USER_NAMESPACES, HttpMethod.POST,null,ZdbRestDTO.class).getBody();
		return zdbRestDTO;
	}

	public List<ZDBConfig> getZDBConfig(Map<String, String> param) {
		List<ZDBConfig> result = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_ZDB_CONFIG,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getZdbConfig();
		};
		return result;
	}

	public ZdbRestDTO createZDBConfig(Map<String, String> param) {
		StringBuffer template = new StringBuffer();
		Pattern expr = Pattern.compile(ApiTemplate.MATCHER_PATTERN);
		Matcher mat = expr.matcher(ApiTemplate.getUpdateZDBConfig());
		while (mat.find()) {
			mat.appendReplacement(template, StringUtil.doubleQuote(param.get(mat.group(1))));
		};
		mat.appendTail(template);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> entity = new HttpEntity<>(template.toString(), headers);
		return connector.exchange(apiServer + URIConstants.URI_CREATE_ZDB_CONFIG, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
	}
	
	public ZdbRestDTO updateZDBConfigs(Map<String, String> param) {
		StringBuffer template = new StringBuffer();
	    Pattern expr = Pattern.compile(ApiTemplate.MATCHER_PATTERN);
		Matcher mat = expr.matcher(ApiTemplate.getUpdateZDBConfig());
		
		while (mat.find()) {
			mat.appendReplacement(template, StringUtil.doubleQuote(param.get(mat.group(1))));
		};
		mat.appendTail(template);
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
	    HttpEntity<String> entity = new HttpEntity<>(template.toString(), headers);
	    
		return connector.exchange(apiServer + URIConstants.URI_UPDATE_ZDB_CONFIGS, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	}

}

