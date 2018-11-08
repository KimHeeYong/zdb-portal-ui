package com.skcc.cloudz.zdb.config.security;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import com.skcc.cloudz.zdb.common.security.filter.OpenIdConnectFilter;
import com.skcc.cloudz.zdb.common.security.handler.LogoutHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Resource(name="keycloakOpenIdTemplate")
    private OAuth2RestTemplate restTemplate;
    
    @Autowired
    private LogoutHandler logoutHandler;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .addFilterAfter(new OAuth2ClientContextFilter(), AbstractPreAuthenticatedProcessingFilter.class)
            .addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class);
    
        http
            .httpBasic()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutHandler)
            .and()
                .csrf().disable();
    
        http
            .authorizeRequests()
                .antMatchers("/error/accessDenied").permitAll()
                .anyRequest()
                .authenticated()
            .and()
                .headers()
                .addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"));
    
        http
            .exceptionHandling()
            .accessDeniedPage("/error/accessDenied");
    }
    
    @Bean
    public OpenIdConnectFilter openIdConnectFilter() {
        final OpenIdConnectFilter filter = new OpenIdConnectFilter("/login");
        filter.setRestTemplate(restTemplate);
        
        return filter;
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
        web.ignoring().antMatchers("/actuator/**");
    }
    
    /*@Bean
    public SessionRegistry sessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }
    
    @Bean
    public static ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }*/

}
