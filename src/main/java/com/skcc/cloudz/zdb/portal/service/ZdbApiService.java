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

import com.skcc.cloudz.zdb.common.component.ZdbRestConnector;
import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.util.StringUtil;
import com.skcc.cloudz.zdb.config.ApiTemplate;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.config.URIConstants;
import com.skcc.cloudz.zdb.portal.domain.dto.Result;
import com.skcc.cloudz.zdb.portal.domain.dto.ZdbRestDTO;
import com.zdb.core.domain.BackupEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.dsl.PodResource;

@Component
public class ZdbApiService{

	@Value("${zdb-api-server.url}") String apiServer;
	@Value("${zdb-demon-server.url}") String demonServer;
    @Autowired SecurityService securityService;
    @Autowired ZdbRestConnector connector;
    
	public List<Namespace> getNamespaces(Map<String,String> param) {
		List<Namespace> list = Collections.emptyList();
		ZdbRestDTO zdbRestDTO = connector.exchange(apiServer + URIConstants.URI_GET_NAMESPACES, HttpMethod.GET, null,  ZdbRestDTO.class).getBody();
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getNamespaces();
		};
		
		return list;
	}
	
	public List<ServiceOverview> getServices(Map<String,String> param) {
		List<ServiceOverview> list = null;
		String url = URIConstants.URI_GET_SERVICES_WITH_NAMESPACE; 
		if(CommonConstants.NAMESPACE_ALL.equals(param.get(CommonConstants.NAMESPACE))) {
			String namespaces = connector.getSessionNamespaces();
			//dwtemp 임시 네임스페이스 적용

			param.put(CommonConstants.NAMESPACE, namespaces);
		};
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + url, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceoverviews();
		};
		
		return list;
	}

	public List<Tag> getTags(Map<String,String> param) {
		List<Tag> list = null;
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
		
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_POD_METRICS, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult();
		};
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

	public List<EventMetaData> getEvents(Map<String, String> param) {
		List<EventMetaData> list = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_EVENTS,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceEvents();
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

	public List<BackupEntity> getBackupList(Map<String, String> param) {
		List<BackupEntity> result = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_BACKUP_LIST,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getBackupList();
		};
		return result;
	}

	public ScheduleEntity getSchedule(Map<String, String> param) {
		ScheduleEntity result = null;
		ZdbRestDTO zdbRestDTO = connector.getForObject(apiServer + URIConstants.URI_GET_SCHEDULE,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getGetSchedule();
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

}

