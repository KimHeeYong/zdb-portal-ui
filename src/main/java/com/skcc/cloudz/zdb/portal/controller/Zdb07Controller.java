package com.skcc.cloudz.zdb.portal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/zdb07/")
public class Zdb07Controller {

	@GetMapping("zdb0710")
	public ModelAndView zdb0710() {
		return new ModelAndView();
	}

	@GetMapping("zdb0720")
	public ModelAndView zdb0720() {
		return new ModelAndView();
	}
	@GetMapping("zdb0721")
	public ModelAndView zdb0721() {
		return new ModelAndView();
	}

	@GetMapping("zdb0730")
	public ModelAndView zdb0730(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@GetMapping(value="zdb0740")
	public ModelAndView zdb0740() {
		return new ModelAndView();
	}	
	
	@GetMapping("zdb0750")
	public ModelAndView zdb0750(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}
