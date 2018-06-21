package com.skcc.cloudz.zdb.common.security.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zdb.common.security.vo.JwtIdToken;
import com.skcc.cloudz.zdb.common.security.vo.OpenIdConnectUserDetailsVo;

@Service
public class SecurityService {
    public OpenIdConnectUserDetailsVo getUserDetails() {
        OpenIdConnectUserDetailsVo userDetailsVo = (OpenIdConnectUserDetailsVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        return userDetailsVo;
    }
    
    public OAuth2AccessToken getAccessToken() {
        OpenIdConnectUserDetailsVo userDetailsVo = this.getUserDetails();
        
        return userDetailsVo.getToken();
    }
    
    public JwtIdToken getIdToken() {
        ObjectMapper objectMapper = new ObjectMapper();
        JwtIdToken jwtIdTokenVo = null;
        
        try {
            OAuth2AccessToken accessToken = this.getAccessToken();
            String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
            
            final Jwt tokenDecoded = JwtHelper.decode(idToken);
            
            jwtIdTokenVo = objectMapper.readValue(tokenDecoded.getClaims(), JwtIdToken.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return jwtIdTokenVo;
    }
}
