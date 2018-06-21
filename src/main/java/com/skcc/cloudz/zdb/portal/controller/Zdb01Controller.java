package com.skcc.cloudz.zdb.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.portal.service.ZdbApiService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dw
 * 서비스 생성 Controller
 */
@Slf4j
@Controller
@RequestMapping("/zdb01/")
public class Zdb01Controller {

	@Autowired ZdbApiService zdbApiService;
	
	@GetMapping("zdb0110")
	public ModelAndView zdb0110(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@GetMapping("zdb0111")
	public ModelAndView zdb0111(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@GetMapping("zdb0112")
	public ModelAndView zdb0112(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
}
