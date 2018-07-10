package com.skcc.cloudz.zdb.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class HttpErrorConfiguration  implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {
  
    public void customize(ConfigurableServletWebServerFactory container) {        
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, CommonConstants.EXCEPTION_URL_STATUS_400));
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, CommonConstants.EXCEPTION_URL_STATUS_404));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, CommonConstants.EXCEPTION_URL_STATUS_500));

    }
}


