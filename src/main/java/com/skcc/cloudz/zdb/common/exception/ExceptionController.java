package com.skcc.cloudz.zdb.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.portal.domain.dto.AjaxResultDto;
import com.skcc.cloudz.zdb.portal.domain.dto.ZdbRestDTO;
import com.zdb.core.domain.IResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ExceptionController {

	@RequestMapping(value = CommonConstants.EXCEPTION_URL_STATUS_400)
	public ModelAndView handleError(HttpServletRequest request, Exception e) {
		return new ModelAndView();
	}

	@RequestMapping(value = CommonConstants.EXCEPTION_URL_STATUS_404)
	public ModelAndView handleError404(HttpServletRequest request, Exception e) {
		return new ModelAndView();
	}

	@RequestMapping(value = CommonConstants.EXCEPTION_URL_STATUS_500)
	public ModelAndView sampleTest(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String message = (String) request.getAttribute(CommonConstants.MESSAGE);
		mav.addObject("message", message);
		return mav;
	}

	@RequestMapping(value = CommonConstants.EXCEPTION_URL_STATUS_500, consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public ModelAndView handleCustomException(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		AjaxResultDto ajaxResultDto = new AjaxResultDto(); 
		ajaxResultDto.setResultCode("exception");
		ajaxResultDto.setResultMessage((String)request.getAttribute(CommonConstants.MESSAGE));
		mav.addObject(CommonConstants.RESULT, ajaxResultDto);
		return mav;
	}
}