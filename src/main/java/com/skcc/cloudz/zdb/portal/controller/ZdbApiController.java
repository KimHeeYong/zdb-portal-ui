package com.skcc.cloudz.zdb.portal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.common.exception.ZdbPortalException;
import com.skcc.cloudz.zdb.common.util.RequestUtil;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.portal.domain.dto.Result;
import com.skcc.cloudz.zdb.portal.domain.dto.ZdbRestDTO;
import com.skcc.cloudz.zdb.portal.service.ZdbApiService;
import com.zdb.core.domain.BackupEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.IResult;
import com.zdb.core.domain.RequestEvent;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/zdbapi/")
public class ZdbApiController {

	@Autowired ZdbApiService zdbApiService;
	
	@RequestMapping(value="getNamespaces", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE })
	@ResponseBody
	public ModelAndView getNamespaces(HttpServletRequest request)throws Exception {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<Namespace> namespaces = zdbApiService.getNamespaces(param); 
		if(namespaces == null || namespaces.size() < 1) {
			throw new ZdbPortalException("네임 스페이스가 존재하지 않습니다.");
		}
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
	
	@RequestMapping(value = "getNodeCount")
	public ModelAndView getNodeCount(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		int nodes = zdbApiService.getNodeCount(param);
		mav.addObject(IResult.NODES,nodes);
		return mav;
	}
	
	@RequestMapping(value = "restartService")
	public ModelAndView restartService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.restartService(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "restartPod")
	public ModelAndView restartPod(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.restartPod(param);
		mav.addObject(CommonConstants.RESULT ,result);		
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
	@RequestMapping(value = "updateScaleUp")
	public ModelAndView updateScaleUp(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateScaleUp(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	@RequestMapping(value = "updateScaleOut")
	public ModelAndView updateScaleOut(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateScaleOut(param);
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

	@RequestMapping(value = "getEvents")	
	public ModelAndView getEvents(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<EventMetaData> result = zdbApiService.getEvents(param);
		mav.addObject(IResult.SERVICE_EVENTS,result);
		
		return mav;
	}
	
	@RequestMapping(value = "getOperationEvents")	
	public ModelAndView getOperationEvents(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<RequestEvent> result = zdbApiService.getOperationEvents(param);
		mav.addObject(IResult.OPERATION_EVENTS,result);
		
		return mav;
	}

	@RequestMapping(value = "getPodLog")	
	public ModelAndView getPodLog(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		String [] result = zdbApiService.getPodLog(param);
		mav.addObject(IResult.POD_LOG,result);
		
		return mav;
	}
	
	@RequestMapping(value = "getBackupList")	
	public ModelAndView getBackupList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<BackupEntity> backupList = zdbApiService.getBackupList(param);
		mav.addObject("backupList",backupList);
		
		return mav;
	}
	
	@RequestMapping(value = "getSchedule")	
	public ModelAndView getSchedule(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ScheduleEntity schedule = zdbApiService.getSchedule(param);
		mav.addObject("schedule",schedule);
		
		return mav;
	}
	@RequestMapping(value = "updateSchedule")
	public ModelAndView updateSchedule(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateSchedule(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "updateBackup")
	public ModelAndView updateBackup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateBackup(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "deleteBackup")
	public ModelAndView deleteBackup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.deleteBackup(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "isAvailable")
	public ModelAndView isAvaliable(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.isAvailable(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}

	@RequestMapping(value = "downloadBackup")
	public ResponseEntity<byte[]> downloadBackup(HttpServletRequest request) {
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ResponseEntity<byte[]> result = zdbApiService.downloadBackup(param);
		
        return result;
	}
}
