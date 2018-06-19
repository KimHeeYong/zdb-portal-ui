package com.skcc.cloudz.zdb.common.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.skcc.cloudz.zdb.api.iam.service.IamApiService;
import com.skcc.cloudz.zdb.common.security.vo.OpenIdConnectUserDetails;

@Component
public class LogoutHandler implements LogoutSuccessHandler {
    
    @Autowired
    private IamApiService iamApiService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        if (authentication != null) {
        	OpenIdConnectUserDetails vo = (OpenIdConnectUserDetails)authentication.getPrincipal();
            iamApiService.logout(vo.getUserId());
        }
        
        String redirectUrl = request.getContextPath() + "/k8s-login";
        response.setStatus(HttpStatus.OK.value());
        response.sendRedirect(redirectUrl);
    }

}
