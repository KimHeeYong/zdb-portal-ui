package com.skcc.cloudz.zdb.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/zdb08/")
public class Zdb08Controller {

	@GetMapping(value="/zdb0800")
	public ModelAndView zdb0800(@PathVariable String url) {
		return new ModelAndView();
	}	
}
