package com.skcc.cloudz.zdb.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.security.vo.OpenIdConnectUserDetailsVo;
import com.skcc.cloudz.zdb.portal.service.ZdbApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dw
 * 서비스 목록 Controller
 */
@Slf4j
@Controller
@RequestMapping("/zdb03/")
public class Zdb03Controller {

	@Autowired ZdbApiService zdbApiService;
	@Value("${grafana.url}") String grafanaUrl;
    @Autowired SecurityService securityService;
    
	@GetMapping("zdb0300")
	public ModelAndView zdb0200(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		OpenIdConnectUserDetailsVo userInfo = securityService.getUserDetails();
		mav.setViewName("redirect:"+grafanaUrl+"/d/namespace/namespace-overview?refresh=10s&orgId=1&var-user=" + userInfo.getUserId());
		return mav;
	}
}
