package com.skcc.cloudz.zdb.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.skcc.cloudz.zdb.common.interceptor.AddOnServiceMetaDataInterceptor;
import com.skcc.cloudz.zdb.common.interceptor.SessionUserDataInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter implements WebMvcConfigurer{
	
    @Bean
    public AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor() {
        AddOnServiceMetaDataInterceptor addOnServiceMetaDataInterceptor = new AddOnServiceMetaDataInterceptor();
        return addOnServiceMetaDataInterceptor;
    }

    @Bean
    public SessionUserDataInterceptor addSessionUserDataInterceptor() {
    	SessionUserDataInterceptor sessionInterceptor = new SessionUserDataInterceptor();
    	return sessionInterceptor;
    }
    
    @Bean
    public LocaleResolver localeResolver() {
    	CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
    	cookieLocaleResolver.setDefaultLocale(Locale.KOREAN);
    	cookieLocaleResolver.setCookieName("Accept-Language");
    	
		// 세션 기준으로 로케일을 설정 한다.
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		// 최초 기본 로케일을 강제로 설정이 가능 하다.
		localeResolver.setDefaultLocale(Locale.KOREAN);
		
    	return cookieLocaleResolver;
    }
    
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
    	LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
    	
    	System.out.println("localeChangeInterceptor===="+lci.getParamName());
    	lci.setParamName("locale");
    	return lci;
    }    
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	String[] pageUrlPattern = {"/zdb*/**"};
    	String[] ajaxAndComPattern = {"/zdbapi/**","/zdbcom/**"};
    	
    	System.out.println("addInterceptors====");
    	
        registry.addInterceptor(localeChangeInterceptor());    	
        registry.addInterceptor(addOnServiceMetaDataInterceptor())        	
		    .addPathPatterns(pageUrlPattern)
		    .excludePathPatterns(ajaxAndComPattern);
        registry.addInterceptor(addSessionUserDataInterceptor())
        	.addPathPatterns(pageUrlPattern)
        	.excludePathPatterns(ajaxAndComPattern);
    }
    
    @Override
    public void addCorsMappings(CorsRegistry registry) {
      registry
          .addMapping("/*")
          .allowedOrigins("*")
          .allowedMethods("GET, POST, PUT, DELETE")
          .allowedHeaders("Content-Type")
          .allowCredentials(false)
          .maxAge(3600);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
            .addResourceHandler("/**")
            .addResourceLocations("classpath:/static/")
            .setCachePeriod(3000)
            .resourceChain(true)
            .addResolver(new PathResourceResolver());
    }
}
