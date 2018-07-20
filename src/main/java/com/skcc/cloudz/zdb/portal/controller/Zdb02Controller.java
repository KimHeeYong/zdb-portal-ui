package com.skcc.cloudz.zdb.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.portal.service.ZdbApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dw
 * 서비스 목록 Controller
 */
@Slf4j
@Controller
@RequestMapping("/zdb02/")
public class Zdb02Controller {

	@Autowired ZdbApiService zdbApiService;
	@Value("${grafana.url}") String grafanaUrl;
	@Value("${zdb-api-server.url}") String apiServerUrl;
	
	@GetMapping("zdb0200")
	public ModelAndView zdb0200(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("apiServerUrl",apiServerUrl);
		return mav;
	}
	@RequestMapping("zdb0210")
	public ModelAndView zdb0210R(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("apiServerUrl",apiServerUrl);
		mav.addObject("grafanaUrl",grafanaUrl);
		return mav;
	}
	@RequestMapping("zdb0200p01")
	public ModelAndView zdb0200p01(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@RequestMapping("zdb0200p02")
	public ModelAndView zdb0200p02(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}
