package com.skcc.cloudz.zdb.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.dto.Result;
import com.skcc.cloudz.zdb.dto.ZdbRestDTO;
import com.skcc.cloudz.zdb.service.ZdbApiService;
import com.skcc.cloudz.zdb.util.RequestUtil;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.IResult;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/zdb/")
public class ZdbApiController {

	@Autowired ZdbApiService zdbApiService;
	
	@RequestMapping("getNamespaces")
	public ModelAndView getNamespaces() {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		
		List<Namespace> namespaces = zdbApiService.getNamespaces(); 
		mav.addObject(IResult.NAMESPACES ,namespaces);
		return mav;
	}
	
	@RequestMapping("getServiceoverviews")
	public ModelAndView getServiceoverviews(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<ServiceOverview> services = zdbApiService.getServices(param);
		List<Tag> tags = zdbApiService.getTags(param);
		mav.addObject(IResult.SERVICEOVERVIEWS,services);
		mav.addObject(IResult.TAGS,tags);
		return mav;
	}
	
	@RequestMapping(value = "getServiceoverview")
	public ModelAndView getServiceoverview(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ServiceOverview serviceoverview = zdbApiService.getServiceoverview(param);
		mav.addObject(IResult.SERVICEOVERVIEW,serviceoverview);
		return mav;
	}
	
	@RequestMapping(value = "getConnectionInfo")
	public ModelAndView getConnectionInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ConnectionInfo connectionInfo = zdbApiService.getConnectionInfo(param);
		mav.addObject(IResult.CONNECTION_INFO,connectionInfo);
		return mav;
	}
	
	@RequestMapping(value = "restartService")
	public ModelAndView restartService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		zdbApiService.restartService(param);
		return mav;
	}
	
	@RequestMapping(value = "getPodResource")
	public ModelAndView getPodResource(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
	    PodResource podResource= zdbApiService.getPodResource(param);
		mav.addObject(CommonConstants.POD_RESOURCE,podResource);
		return mav;
	}
	
	@RequestMapping(value = "getPodMetrics")
	public ModelAndView getPodMetrics(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result podMetrics = zdbApiService.getPodMetrics(param);
		mav.addObject(CommonConstants.POD_METRICS ,podMetrics);
		return mav;
	}
	
	@RequestMapping(value = "getDBVariables")
	public ModelAndView getDBVariables(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result dbConfigurations = zdbApiService.getDBVariables(param);
		if(CommonConstants.SERVICE_TYPE_MARIA.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,dbConfigurations.getMariaDBConfig());	
		}else if(CommonConstants.SERVICE_TYPE_REDIS.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,dbConfigurations.getRedisConfig());	
		};
		
		return mav;
	}
	
	@RequestMapping(value = "updateDBVariables")
	public ModelAndView updateDBVariables(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result result = zdbApiService.updateDBVariables(param);
		if(CommonConstants.SERVICE_TYPE_MARIA.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,result.getMariaDBConfig());	
		}else if(CommonConstants.SERVICE_TYPE_REDIS.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,result.getRedisConfig());	
		};
		
		return mav;
	}
	@RequestMapping(value = "updateConfig")
	public ModelAndView updateConfig(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result result = zdbApiService.updateConfig(param);
		if(CommonConstants.SERVICE_TYPE_MARIA.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,result.getMariaDBConfig());	
		}else if(CommonConstants.SERVICE_TYPE_REDIS.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,result.getRedisConfig());	
		};
		
		return mav;
	}
	@RequestMapping(value = "updateScale")
	public ModelAndView updateScale(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateScale(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "createDeployment")
	public ModelAndView createDeployment(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.createDeployment(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	
	@RequestMapping(value = "setNewPassword")
	public ModelAndView setNewPassword(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.setNewPassword(param);
		mav.addObject(CommonConstants.RESULT ,result);
		
		return mav;
	}
	
	@RequestMapping(value = "deleteServiceInstance")
	public ModelAndView deleteServiceInstance(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.deleteServiceInstance(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	
	@RequestMapping(value = "createTag")
	public ModelAndView createTag(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.createTag(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "deleteTag")
	public ModelAndView deleteTag(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.deleteTag(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}

	@RequestMapping(value = "getEventsWithService")	
	public ModelAndView getEventsWithService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<EventMetaData> result = zdbApiService.getEventsWithService(param);
		mav.addObject(IResult.SERVICE_EVENTS,result);
		
		return mav;
	}
}
