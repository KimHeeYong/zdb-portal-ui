package com.skcc.cloudz.zdb.portal.service;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

@Configuration
public class ZdbApiService{

	@Autowired RestTemplate restTemplate;
	@Value("${zdb-api-server.url}") String apiServer;
	
	public List<Namespace> getNamespaces() {
		List<Namespace> list = null;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_NAMESPACES, ZdbRestDTO.class);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getNamespaces();
		};
		
		return list;
	}
	
	public List<ServiceOverview> getServices(Map<String,String> param) {
		List<ServiceOverview> list = null;
		String url = CommonConstants.NAMESPACE_ALL.equals(param.get(CommonConstants.NAMESPACE)) ? URIConstants.URI_GET_SERVICES : URIConstants.URI_GET_SERVICES_WITH_NAMESPACE; 
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + url, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceoverviews();
		};
		
		return list;
	}

	public List<Tag> getTags(Map<String,String> param) {
		List<Tag> list = null;
		String url = CommonConstants.NAMESPACE_ALL.equals(param.get(CommonConstants.NAMESPACE)) ? URIConstants.URI_GET_TAGS : URIConstants.URI_GET_TAGS_WITH_NAMESPACE;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + url, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getTags();
		};
		
		return list;
	}
	public ServiceOverview getServiceoverview(Map<String, String> param) {
		ServiceOverview ob = null;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_SERVICE, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getServiceoverview();
		};
		
		return ob;
	}
	
	public ConnectionInfo getConnectionInfo(Map<String, String> param) {
		ConnectionInfo ob = null;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_CONNECTION_INFO, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getConnectionInfo();
		};
		return ob;
	}
	
	public int getNodeCount(Map<String,String> param) {
		int ob = 0;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_NODE_COUNT, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getNodes();
		};
		return ob;
	}
	
	//restartService
	public ZdbRestDTO restartService(Map<String, String> param) {
		return restTemplate.getForObject(apiServer + URIConstants.URI_RESTART_SERVICE, ZdbRestDTO.class,param);
	}
	//restartService
	public ZdbRestDTO restartPod(Map<String, String> param) {
		return restTemplate.getForObject(apiServer + URIConstants.URI_RESTART_POD, ZdbRestDTO.class,param);
	}

	@SuppressWarnings("rawtypes")
	public PodResource getPodResource(Map<String, String> param) {
		PodResource ob = null;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_POD_RESOURCE, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult().getPodResource();
		};
		return ob;
	}
	
	public Result getPodMetrics(Map<String, String> param) {
		Result ob = null;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_POD_METRICS, ZdbRestDTO.class,param);
		if(zdbRestDTO != null) {
			ob = zdbRestDTO.getResult();
		};
		return ob;
	}

	public Result getDBVariables(Map<String, String> param) {
		Result ob = null;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_DB_VARIABLES, ZdbRestDTO.class,param);
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
	    ZdbRestDTO zdbRestDTO = restTemplate.exchange(apiServer + URIConstants.URI_UPDATE_DB_VARIABLES, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	    
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
	    ZdbRestDTO zdbRestDTO = restTemplate.exchange(apiServer + URIConstants.URI_UPDATE_CONFIG, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	    
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
		return restTemplate.exchange(apiServer + URIConstants.URI_CREATE_DEPLOYMENT, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
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
		return restTemplate.exchange(apiServer + URIConstants.URI_UPDATE_SCALE_UP, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	}
	public ZdbRestDTO updateScaleOut(Map<String, String> param) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Map<String,String>> entity = new HttpEntity<>(param, headers);
		return restTemplate.exchange(apiServer + URIConstants.URI_UPDATE_SCALE_OUT, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
	}

	public ZdbRestDTO setNewPassword(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = restTemplate.exchange(apiServer + URIConstants.URI_SET_NEW_PASSWORD, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO deleteServiceInstance(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = restTemplate.exchange(apiServer + URIConstants.URI_DELETE_SERVICE_INSTANCE, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO createTag(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = restTemplate.exchange(apiServer + URIConstants.URI_CREATE_TAG, HttpMethod.POST, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public ZdbRestDTO deleteTag(Map<String, String> param) {
		ZdbRestDTO ob = null;
		
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Map<String, String>> entity = new HttpEntity<>(param, headers);
	    ob = restTemplate.exchange(apiServer + URIConstants.URI_DELETE_TAG, HttpMethod.DELETE, entity,  ZdbRestDTO.class,param).getBody();
		return ob;
	}

	public List<EventMetaData> getEvents(Map<String, String> param) {
		List<EventMetaData> list = null;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_EVENTS,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceEvents();
		};
		
		return list;
	}

	public String[] getPodLog(Map<String, String> param) {
		String[] result = {};
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_POD_LOG,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getPodLog();
		};
		return result;
	}

	public List<BackupEntity> getBackupList(Map<String, String> param) {
		List<BackupEntity> result = null;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_BACKUP_LIST,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getBackupList();
		};
		return result;
	}

	public ScheduleEntity getSchedule(Map<String, String> param) {
		ScheduleEntity result = null;
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_SCHEDULE,ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			result = zdbRestDTO.getResult().getGetSchedule();
		};
		return result;
	}

}

