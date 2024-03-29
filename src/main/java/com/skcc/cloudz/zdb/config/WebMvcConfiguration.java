package com.skcc.cloudz.zdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.skcc.cloudz.zdb.common.interceptor.AddOnServiceMetaDataInterceptor;
import com.skcc.cloudz.zdb.common.interceptor.SessionUserDataInterceptor;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {
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
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	String[] pageUrlPattern = {"/zdb*/**"};
    	String[] ajaxAndComPattern = {"/zdbapi/**","/zdbcom/**"};
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
