package com.skcc.cloudz.zdb.service;

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
import org.springframework.web.client.RestTemplate;

import com.skcc.cloudz.zdb.config.ApiTemplate;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.config.URIConstants;
import com.skcc.cloudz.zdb.dto.Result;
import com.skcc.cloudz.zdb.dto.ZdbRestDTO;
import com.skcc.cloudz.zdb.util.StringUtil;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.EventMetaData;
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
	
	//restartService
	public void restartService(Map<String, String> param) {
		restTemplate.getForObject(apiServer + URIConstants.URI_RESTART_SERVICE, ZdbRestDTO.class,param);
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
	public ZdbRestDTO updateScale(Map<String, String> param) {
		StringBuffer updateScaleTemplate = new StringBuffer();
		Pattern expr = Pattern.compile(ApiTemplate.MATCHER_PATTERN);
		Matcher mat = expr.matcher(ApiTemplate.getUpdateScaleTemplate(param.get(CommonConstants.SERVICE_TYPE)));
		while (mat.find()) {
			mat.appendReplacement(updateScaleTemplate, StringUtil.doubleQuote(param.get(mat.group(1))));
		};
		mat.appendTail(updateScaleTemplate);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(updateScaleTemplate.toString(), headers);
		return restTemplate.exchange(apiServer + URIConstants.URI_UPDATE_SCALE, HttpMethod.PUT, entity,  ZdbRestDTO.class,param).getBody();
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

	public List<EventMetaData> getEventsWithService(Map<String, String> param) {
		List<EventMetaData> list = null;
		
		ZdbRestDTO zdbRestDTO = restTemplate.getForObject(apiServer + URIConstants.URI_GET_EVENTS_WITH_SERVICE, ZdbRestDTO.class,param);
		
		if(zdbRestDTO!=null) {
			list = zdbRestDTO.getResult().getServiceEvents();
		};
		
		return list;
	}



}

