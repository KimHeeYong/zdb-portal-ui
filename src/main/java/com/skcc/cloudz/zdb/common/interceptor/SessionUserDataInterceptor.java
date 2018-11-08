package com.skcc.cloudz.zdb.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.security.vo.OpenIdConnectUserDetailsVo;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.portal.service.ZdbApiService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SessionUserDataInterceptor extends HandlerInterceptorAdapter{
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private ZdbApiService apiService;
    
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		HttpSession session = request.getSession();
		//if(session.getAttribute(CommonConstants.USER_INFO)== null) {
			OpenIdConnectUserDetailsVo userInfo = securityService.getUserDetails();
			if(userInfo != null) {
				session.setAttribute(CommonConstants.USER_INFO, userInfo);
				
				try {
					updateUserNamespaces();
				} catch(Exception e) {
					//do nothing.
				}
			}
		//};
	}
	
	public void updateUserNamespaces() {
		apiService.updateUserNamespaces();
	}

}
