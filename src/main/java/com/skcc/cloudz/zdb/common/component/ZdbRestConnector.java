package com.skcc.cloudz.zdb.common.component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.security.vo.OpenIdConnectUserDetailsVo;

@Component
public class ZdbRestConnector extends RestTemplate{
	
	@Autowired
	SecurityService securityService;
	
	@Override
	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType, Map<String, ?> uriVariables) {
		HttpHeaders tempHeaders = new HttpHeaders();
		Object tempBody = null;
		
		if(requestEntity == null || requestEntity.getHeaders() == null) {
			tempHeaders.setContentType(MediaType.APPLICATION_JSON);
		}else{
			tempBody = requestEntity.getBody();
			tempHeaders.putAll(requestEntity.getHeaders());
		};
		if(uriVariables == null) {
			uriVariables = new HashMap<String,Object>();
		}
		addHeaderSession(tempHeaders);
		
		HttpEntity<?> newRequestEntity = new HttpEntity<>(tempBody,tempHeaders);
		ResponseEntity<T> result = super.exchange(url, method, newRequestEntity, responseType, uriVariables);
		
		return result;
	};

	public <T> ResponseEntity<T> exchange(String url, HttpMethod method, @Nullable HttpEntity<?> requestEntity, Class<T> responseType) {
		return this.exchange(url, HttpMethod.GET, requestEntity,responseType,(Map<String, ?>)null);
	};

	
	public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) {
		T result = (T) this.exchange(url, HttpMethod.GET, null,responseType,uriVariables).getBody();
		return result;
	}
	
	@Autowired
	Environment env;
	
	// 임시 세션 dwtemp 
	public String getSessionNamespaces() {
		OpenIdConnectUserDetailsVo userInfo = securityService.getUserDetails();
		List<String> userNamespaces = null;

		if (userInfo != null && userInfo.getNamespaces() != null) {
			userNamespaces = userInfo.getNamespaces().stream().collect(Collectors.toList());
		}

		if (Arrays.asList(env.getActiveProfiles()).contains("local")) {
			if(userNamespaces == null) {
				userNamespaces = new ArrayList<>();
			}
			userNamespaces.add("zdb-redis");
			userNamespaces.add("lwk");
			userNamespaces.add("zdb-maria");
		}

		return userNamespaces == null || userNamespaces.isEmpty() ? ""
				: userNamespaces.stream().map(i -> i.toString()).collect(Collectors.joining(","));
	}

	private void addHeaderSession(HttpHeaders headers) {
		OpenIdConnectUserDetailsVo userInfo = securityService.getUserDetails();
		if(headers != null) {
			headers.set("userId", userInfo.getUserId());
			headers.set("userName", userInfo.getUsername());
			headers.set("email", userInfo.getEmail());
			headers.set("firstName", userInfo.getFirstName());
			headers.set("accessRole", userInfo.getAccessRole());
			headers.set("namespaces", getSessionNamespaces());
			headers.set("defaultNamespace", userInfo.getDefaultNamespace());
		}
	}
	
}