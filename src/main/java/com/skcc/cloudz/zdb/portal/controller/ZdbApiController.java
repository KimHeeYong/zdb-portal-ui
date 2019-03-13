package com.skcc.cloudz.zdb.portal.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.skcc.cloudz.zdb.common.exception.ZdbPortalException;
import com.skcc.cloudz.zdb.common.util.RequestUtil;
import com.skcc.cloudz.zdb.config.CommonConstants;
import com.skcc.cloudz.zdb.portal.domain.dto.NamespaceResource;
import com.skcc.cloudz.zdb.portal.domain.dto.Result;
import com.skcc.cloudz.zdb.portal.domain.dto.ZdbRestDTO;
import com.skcc.cloudz.zdb.portal.service.ZdbApiService;
import com.zdb.core.domain.BackupEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.DBUser;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.IResult;
import com.zdb.core.domain.RequestEvent;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ScheduleInfoEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;
import com.zdb.core.domain.ZDBConfig;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/zdbapi/")
public class ZdbApiController {

	@Autowired ZdbApiService zdbApiService;
	
	@RequestMapping(value="getNamespaces", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE }, method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getNamespaces(HttpServletRequest request)throws Exception {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<Namespace> namespaces = zdbApiService.getNamespaces(param); 
		if(namespaces == null || namespaces.size() < 1) {
			throw new ZdbPortalException("네임스페이스가 존재하지 않습니다.<br>네임스페이스 등록 및 권한 할당이 필요합니다.");
		}
		mav.addObject(IResult.NAMESPACES ,namespaces);
		return mav;
	}
	@RequestMapping(value="getNamespaceResource", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getNamespaceResource(HttpServletRequest request)throws Exception {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		NamespaceResource namespaceResource = zdbApiService.getNamespaceResource(param); 
		mav.addObject(IResult.NAMESPACE_RESOURCE ,namespaceResource);
		return mav;
	}
	
	@RequestMapping(value="getServiceoverviews", method = RequestMethod.GET)
	public ModelAndView getServiceoverviews(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<ServiceOverview> services = zdbApiService.getServices(param);
		List<Tag> tags = zdbApiService.getTags(param);
		mav.addObject(IResult.SERVICEOVERVIEWS,services);
		mav.addObject(IResult.TAGS,tags);
		return mav;
	}
	
	@RequestMapping(value = "getServiceoverview", method = RequestMethod.GET)
	public ModelAndView getServiceoverview(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ServiceOverview serviceoverview = zdbApiService.getServiceoverview(param);
		mav.addObject(IResult.SERVICEOVERVIEW,serviceoverview);
		return mav;
	}
	
	@RequestMapping(value = "getConnectionInfo", method = RequestMethod.GET)
	public ModelAndView getConnectionInfo(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ConnectionInfo connectionInfo = zdbApiService.getConnectionInfo(param);
		mav.addObject(IResult.CONNECTION_INFO,connectionInfo);
		return mav;
	}
	
	@RequestMapping(value = "getNodeCount", method = RequestMethod.GET)
	public ModelAndView getNodeCount(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		int nodes = zdbApiService.getNodeCount(param);
		mav.addObject(IResult.NODES,nodes);
		return mav;
	}
	
	@RequestMapping(value = "restartService", method = RequestMethod.GET)
	public ModelAndView restartService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.restartService(param);
		mav.addObject(CommonConstants.RESULT ,result);
		return mav;
	}
	
	@RequestMapping(value = "restartPod", method = RequestMethod.GET)
	public ModelAndView restartPod(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.restartPod(param);
		mav.addObject(CommonConstants.RESULT ,result);		
		return mav;
	}
	
	@RequestMapping(value = "getPodResource", method = RequestMethod.GET)
	public ModelAndView getPodResource(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
	    PodResource podResource= zdbApiService.getPodResource(param);
		mav.addObject(CommonConstants.POD_RESOURCE,podResource);
		return mav;
	}
	
	@RequestMapping(value = "getPods", method = RequestMethod.GET)
	public ModelAndView getPods(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<Pod> podList = zdbApiService.getPods(param);
		mav.addObject(IResult.PODS ,podList);
		return mav;
	}
	
	@RequestMapping(value = "getPodMetrics", method = RequestMethod.GET)
	public ModelAndView getPodMetrics(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result podMetrics = zdbApiService.getPodMetrics(param);
		mav.addObject(CommonConstants.POD_METRICS ,podMetrics);
		return mav;
	}
	
	@RequestMapping(value = "getDBVariables", method = RequestMethod.GET)
	public ModelAndView getDBVariables(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		Result dbConfigurations = zdbApiService.getDBVariables(param);
		if(CommonConstants.SERVICE_TYPE_MARIA.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,dbConfigurations.getMariaDBConfig());	
		}else if(CommonConstants.SERVICE_TYPE_REDIS.equals(param.get(CommonConstants.SERVICE_TYPE))) {
			mav.addObject(CommonConstants.DB_CONFIGURATIONS ,(Map<String,String>)dbConfigurations.getRedisConfig());	
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
	
	@RequestMapping(value = "deleteServiceInstance", method = RequestMethod.DELETE)
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
	
	@RequestMapping(value = "createPublicService")
	public ModelAndView createPublicService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.createPublicService(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "deletePublicService")
	public ModelAndView deletePublicService(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.deletePublicService(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}

	@RequestMapping(value = "getEvents", method = RequestMethod.GET)	
	public ModelAndView getEvents(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<EventMetaData> result = zdbApiService.getEvents(param);
		mav.addObject(IResult.SERVICE_EVENTS,result);
		
		return mav;
	}
	@RequestMapping(value = "getUserGrants", method = RequestMethod.GET)	
	public ModelAndView getUserGrants(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<DBUser> result = zdbApiService.getUserGrants(param);
		mav.addObject(IResult.USER_GRANTS,result);
		
		return mav;
	}
	
	@RequestMapping(value = "getOperationEvents", method = RequestMethod.GET)	
	public ModelAndView getOperationEvents(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<RequestEvent> result = zdbApiService.getOperationEvents(param);
		mav.addObject(IResult.OPERATION_EVENTS,result);
		
		return mav;
	}

	@RequestMapping(value = "getPodLog", method = RequestMethod.GET)	
	public ModelAndView getPodLog(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		String [] result = zdbApiService.getPodLog(param);
		mav.addObject(IResult.POD_LOG,result);
		
		return mav;
	}
	@RequestMapping(value = "getSlowLog", method = RequestMethod.GET)	
	public ModelAndView getSlowLog(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		String [] result = zdbApiService.getSlowLog(param);
		mav.addObject(IResult.SLOW_LOG,result);
		
		return mav;
	}
	
	@RequestMapping(value = "getMycnf", method = RequestMethod.GET)	
	public ModelAndView getMycnf(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		String [] result = zdbApiService.getMycnf(param);
		mav.addObject(IResult.MY_CNF,result);
		
		return mav;
	}
	@RequestMapping(value = "getAllDBVariables", method = RequestMethod.GET)	
	public ModelAndView getAllDBVariables(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<String> result = zdbApiService.getAllDBVariables(param);
		mav.addObject(IResult.REDIS_CONFIG,result);
		
		return mav;
	}
	
	@RequestMapping(value = "getBackupList", method = RequestMethod.GET)	
	public ModelAndView getBackupList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<BackupEntity> backupList = zdbApiService.getBackupList(param);
		mav.addObject("backupList",backupList);
		
		return mav;
	}
	@RequestMapping(value = "getScheduleInfoList", method = RequestMethod.GET)	
	public ModelAndView getScheduleInfoList(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<ScheduleInfoEntity> scheduleInfoList = zdbApiService.getScheduleInfoList(param);
		mav.addObject("scheduleInfoList",scheduleInfoList);
		
		return mav;
	}
	
	@RequestMapping(value = "getSchedule", method = RequestMethod.GET)	
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
	
	@RequestMapping(value = "restoreFromBackup")
	public ModelAndView restoreFromBackup(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.restoreFromBackup(param);
		mav.addObject(CommonConstants.RESULT ,result);
		
		return mav;
	}

	@RequestMapping(value = "getZDBConfig", method = RequestMethod.GET)
	public ModelAndView getZDBConfig(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		List<ZDBConfig> zdbConfig = zdbApiService.getZDBConfig(param);
		mav.addObject("zdbConfig",zdbConfig);
		
		return mav;
	}
	@RequestMapping(value = "createZDBConfig", method = RequestMethod.POST)
	public ModelAndView createZDBConfig(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.createZDBConfig(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "updateZDBConfigs", method = RequestMethod.PUT)
	public ModelAndView updateZDBConfigs(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateZDBConfigs(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "updateAutoFailoverEnable", method = RequestMethod.PUT)
	public ModelAndView updateAutoFailoverEnable(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.updateAutoFailoverEnable(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}

	@RequestMapping(value = "serviceFailOverStatus", method = RequestMethod.GET)
	public ModelAndView serviceFailOverStatus(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.serviceFailOverStatus(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	
	@RequestMapping(value = "serviceChangeMasterToSlave", method = RequestMethod.PUT)
	public ModelAndView serviceChangeMasterToSlave(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.serviceChangeMasterToSlave(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "serviceChangeSlaveToMaster", method = RequestMethod.PUT)
	public ModelAndView serviceChangeSlaveToMaster(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.serviceChangeSlaveToMaster(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "changePort", method = RequestMethod.PUT)
	public ModelAndView changePort(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.changePort(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	
	
	@RequestMapping(value = "serviceOn", method = RequestMethod.PUT)
	public ModelAndView serviceOn(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.serviceOn(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "serviceOff", method = RequestMethod.PUT)
	public ModelAndView serviceOff(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.serviceOff(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
	@RequestMapping(value = "workerpools", method = RequestMethod.GET)
	public ModelAndView workerpools(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView(CommonConstants.JSON_VIEW);
		Map<String,String> param = RequestUtil.getMapFromRequest(request);
		
		ZdbRestDTO result = zdbApiService.workerpools(param);
		mav.addObject(CommonConstants.RESULT,result);
		
		return mav;
	}
}
