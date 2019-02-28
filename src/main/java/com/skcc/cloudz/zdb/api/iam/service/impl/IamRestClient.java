package com.skcc.cloudz.zdb.api.iam.service.impl;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zdb.api.iam.domain.vo.ApiResponseVo;

@Service
public class IamRestClient {
    
    private static final Logger log = LoggerFactory.getLogger(IamRestClient.class);
    
    @Value("${props.iam.baseUrl}")
    private String iamBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;

    public ApiResponseVo request(HttpMethod method, String targetUrl, Object data) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path(targetUrl).build().toString();
            log.info("===> Request Url : {}", url);
            
            HttpEntity<String> requestEntity = null;
            if(data != null) {
            	ObjectMapper objectMapper = new ObjectMapper();
	            String requestBody = objectMapper.writeValueAsString(data);
	            log.info("===> Request Body : {}", requestBody);
	            
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            requestEntity = new HttpEntity<String>(requestBody, headers); 
            }else {
            	HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);
	            requestEntity = new HttpEntity<String>(headers);
            }
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, method, requestEntity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException | IOException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }

    

}
