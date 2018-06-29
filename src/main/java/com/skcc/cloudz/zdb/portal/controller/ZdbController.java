package com.skcc.cloudz.zdb.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ZdbController {
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("redirect:/zdb02/zdb0200");
	}
	@RequestMapping("/zdbcom/confirm")
	public ModelAndView confirm(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	@RequestMapping("/zdbcom/alert")
	public ModelAndView alert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	@GetMapping("/test1")
	public ModelAndView test1() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}
