package com.skcc.cloudz.zdb.api.iam.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.skcc.cloudz.zdb.api.iam.domain.vo.ApiResponseVo;
import com.skcc.cloudz.zdb.api.iam.domain.vo.ZcpUserResVo;
import com.skcc.cloudz.zdb.api.iam.service.IamApiService;

@Service
public class IamApiServiceImpl implements IamApiService {
    
    private static final Logger log = LoggerFactory.getLogger(IamApiServiceImpl.class);
    
    @Value("${props.iam.baseUrl}")
    private String iamBaseUrl;
    
    @Autowired
    private RestTemplate restTemplate;

   

    @Override
    public ApiResponseVo logout(String userId) {
        ApiResponseVo apiResponseVo = new ApiResponseVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user/{id}/logout").buildAndExpand(userId).toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ApiResponseVo> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ApiResponseVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                apiResponseVo.setCode(responseEntity.getBody().getCode());
                apiResponseVo.setMsg(responseEntity.getBody().getMsg());
                apiResponseVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return apiResponseVo;
    }



    @Override
    public ZcpUserResVo getUser(String userId) {
        ZcpUserResVo zcpUserResVo = new ZcpUserResVo();
        
        try {
            String url = UriComponentsBuilder.fromUriString(iamBaseUrl).path("/iam/user/{id}").buildAndExpand(userId).toString();
            log.info("===> Request Url : {}", url);
            
            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> requestEntity = new HttpEntity<String>(headers); 
            
            ResponseEntity<ZcpUserResVo> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, ZcpUserResVo.class);
            
            log.info("===> Response status : {}", responseEntity.getStatusCode().value());
            log.info("===> Response body msg : {}", responseEntity.getBody().getMsg());
            log.info("===> Response body code : {}", responseEntity.getBody().getCode());
            log.info("===> Response body data : {}", responseEntity.getBody().getData());
            
            if (responseEntity!= null && responseEntity.getStatusCode() == HttpStatus.OK) {
                zcpUserResVo.setCode(responseEntity.getBody().getCode());
                zcpUserResVo.setMsg(responseEntity.getBody().getMsg());
                zcpUserResVo.setData(responseEntity.getBody().getData());    
            }
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        
        return zcpUserResVo;
    }
    

    

}
