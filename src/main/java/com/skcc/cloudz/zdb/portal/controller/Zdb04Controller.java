package com.skcc.cloudz.zdb.portal.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequestMapping("/zdb04/")
public class Zdb04Controller {

	@Autowired ZdbApiService zdbApiService;
	
	@GetMapping("zdb0400")
	public ModelAndView zdb0200(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		Enumeration<String> it = session.getAttributeNames();
		while(it.hasMoreElements()) {
			String key = it.nextElement();
			
		};
		return mav;
	}
}
