package com.skcc.cloudz.zdb.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class KeyCloakOpenIdConnectConfiguration {
    @Value("${props.keycloak.zcp.clientId}")
    private String clientId;
 
    @Value("${props.keycloak.zcp.clientSecret}")
    private String clientSecret;
 
    @Value("${props.keycloak.zcp.accessTokenUri}")
    private String accessTokenUri;
 
    @Value("${props.keycloak.zcp.userAuthorizationUri}")
    private String userAuthorizationUri;
 
    @Value("${props.keycloak.zcp.redirectUri}")
    private String redirectUri;
 
    @Bean
    public OAuth2ProtectedResourceDetails keycloakOpenId() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setAccessTokenUri(accessTokenUri);
        details.setUserAuthorizationUri(userAuthorizationUri);
        details.setScope(Arrays.asList("openid", "email"));
        details.setPreEstablishedRedirectUri(redirectUri);
        details.setUseCurrentUri(false);
        
        return details;
    }
 
    @Bean(name="keycloakOpenIdTemplate")
    public OAuth2RestTemplate keycloakOpenIdTemplate(OAuth2ClientContext clientContext) {
        return new OAuth2RestTemplate(keycloakOpenId(), clientContext);
    }
}
