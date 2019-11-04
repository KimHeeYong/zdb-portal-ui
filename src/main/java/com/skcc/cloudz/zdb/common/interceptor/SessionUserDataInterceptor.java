package com.skcc.cloudz.zdb.common.interceptor;

import java.net.URLDecoder;

import javax.servlet.http.Cookie;
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
		Cookie[] cookies = request.getCookies();
		String locale = "ko";
		if(cookies != null) {
			for(Cookie cookie : cookies) {
				if("Accept-Language".equals(cookie.getName())) {
					locale = URLDecoder.decode(cookie.getValue(),"UTF-8");
				}
			}
		}		
		//if(session.getAttribute(CommonConstants.USER_INFO)== null) {
			OpenIdConnectUserDetailsVo userInfo = securityService.getUserDetails();
			if(userInfo != null) {
				session.setAttribute(CommonConstants.USER_INFO, userInfo);
				session.setAttribute("Accept-Language", locale);
				
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
