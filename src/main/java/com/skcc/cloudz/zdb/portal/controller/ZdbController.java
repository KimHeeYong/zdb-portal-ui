package com.skcc.cloudz.zdb.portal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ZdbController {
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("redirect:/zdb02/zdb0200");
	}
	@GetMapping("/test")
	public ModelAndView test(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("message", "message hotdep");
		List list = new ArrayList();
		for(int i = 0 ; i< 10;i++) {
			Map map = new HashMap();
			map.put("aa", i+1);
			list.add(map);
		}
		mav.addObject("list", list);
		HttpSession session =  request.getSession();
		session.setAttribute("id", "idTest");
		return mav;
	}
	@GetMapping("/content")
	public ModelAndView content() {
		return new ModelAndView("content");
	}
	@GetMapping("/test1")
	public ModelAndView test1() {
		ModelAndView mav = new ModelAndView();
		//System.out.println(result);
		//http://127.0.0.1:8080/api/v1/service/services
		
		return mav;
	}
}
