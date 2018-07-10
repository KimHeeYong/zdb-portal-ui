package com.skcc.cloudz.zdb.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.config.CommonConstants;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler
	public ModelAndView handleError(Exception exception, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("forward:/exception/500");
        request.setAttribute(CommonConstants.MESSAGE, exception.getMessage());
        
        return mav;
	}

}
