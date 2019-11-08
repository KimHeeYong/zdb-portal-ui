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
	/**
	 * 2019.07.03.  추가
	 * @author kumocomms
	 * @param request
	 * @return
	 */
	@RequestMapping("/zdbcom/credentialConfirm")
	public ModelAndView credentialConfirm(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	@RequestMapping("/zdbcom/alert")
	public ModelAndView alert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	/**
	 * 2019.11.08.  추가
	 * @author kumocomms
	 * @param request
	 * @return
	 */
	@RequestMapping("/zdbcom/errorAlert")
	public ModelAndView errorAlert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	/**
	 * 2019.07.05.  추가
	 * @author kumocomms
	 * @param request
	 * @return
	 */
	@RequestMapping("/zdbcom/backupAlert")
	public ModelAndView backupAlert(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
	
	
	@GetMapping("/zdb/dashboard")
	public ModelAndView dashBoard() {
		return new ModelAndView();
	}
	
	@GetMapping("/zdb/test1")
	public ModelAndView test1() {
		ModelAndView mav = new ModelAndView();
		return mav;
	}
}
