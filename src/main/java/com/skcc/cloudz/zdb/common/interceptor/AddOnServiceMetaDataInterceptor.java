package com.skcc.cloudz.zdb.common.interceptor;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcc.cloudz.zdb.common.component.AddOnServiceMataComponent;
import com.skcc.cloudz.zdb.common.domain.vo.AddOnServiceMataSubVo;
import com.skcc.cloudz.zdb.common.domain.vo.AddOnServiceMataVo;
import com.skcc.cloudz.zdb.common.security.service.SecurityService;
import com.skcc.cloudz.zdb.common.security.vo.AccessRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddOnServiceMetaDataInterceptor extends HandlerInterceptorAdapter {
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private AddOnServiceMataComponent addOnServiceMataComponent;
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView == null || !modelAndView.hasView()) {
            return;
        }
        
        String requestURI = request.getRequestURI().substring(request.getContextPath().length());
        if (log.isDebugEnabled()) {
            log.debug("requestURI : {}", requestURI);    
            log.debug("getAddOnServiceActivePathInfo : {}", this.getAddOnServiceActivePathInfo(requestURI));
        }
        
        List<AddOnServiceMataVo> resultList = new ArrayList<AddOnServiceMataVo>();
        if (addOnServiceMataComponent.getAddOnServiceMetaVoList() == null) {
            resultList = this.getAddOnServiceMetaData();
            
            addOnServiceMataComponent.setUserId(securityService.getUserDetails().getUserId());
            addOnServiceMataComponent.setAddOnServiceMetaVoList(resultList);
        } else {
            resultList = addOnServiceMataComponent.getAddOnServiceMetaVoList();
        }
        
        modelAndView.addObject("addOnServiceMataData", resultList);
        modelAndView.addObject("activePathInfo", this.getAddOnServiceActivePathInfo(requestURI));
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaData () {
        List<AddOnServiceMataVo> AddOnServiceMataVoList = new ArrayList<AddOnServiceMataVo>();
        ObjectMapper mapper = new ObjectMapper();
        
        try {
            String userAccessRole = securityService.getUserDetails().getAccessRole();
            log.debug("userAccessRole : {}", userAccessRole);
            
            InputStream inputStream = AddOnServiceMetaDataInterceptor.class.getClassLoader().getResourceAsStream("addOnServiceMetaData.json");
            if (inputStream == null) {
                throw new Exception();
            }
            
            List<AddOnServiceMataVo> addOnServiceMataList = mapper.readValue(inputStream, new TypeReference<List<AddOnServiceMataVo>>(){});
             
            for (AddOnServiceMataVo addOnServiceMataVo : addOnServiceMataList) {
            	AddOnServiceMataVoList.add(this.getAddOnServiceMetaDataSub(addOnServiceMataVo));
            	if(false) {//temp 권한 패쓰
            		for (AccessRole accessRole : addOnServiceMataVo.getAccessRoles()) {
            			if (userAccessRole.equals(accessRole.getName()) && addOnServiceMataVo.isEnable()) {
            				AddOnServiceMataVoList.add(this.getAddOnServiceMetaDataSub(addOnServiceMataVo));        
            			}
            		}
            	}
            }
            
            Collections.sort(AddOnServiceMataVoList);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("getAddOnServiceMetaData IOException : {}", e);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("getAddOnServiceMetaData Exception : {}", e);
        }
        
        return AddOnServiceMataVoList;
    }
    
    public AddOnServiceMataVo getAddOnServiceMetaDataSub(AddOnServiceMataVo addOnServiceMataVo) {
        if (addOnServiceMataVo.getSub() == null) return addOnServiceMataVo;
        
        List<AddOnServiceMataSubVo> addOnServiceMataSubVoList = new ArrayList<AddOnServiceMataSubVo>();
        for (AddOnServiceMataSubVo addOnServiceMataSubVo : addOnServiceMataVo.getSub()) {
            if (addOnServiceMataSubVo.isEnable()) {
                addOnServiceMataSubVoList.add(addOnServiceMataSubVo);
            }
        }
        
        addOnServiceMataVo.setSub(addOnServiceMataSubVoList);
        Collections.sort(addOnServiceMataVo.getSub());
        
        return addOnServiceMataVo;
    }
    
    public Map<String, Object> getAddOnServiceActivePathInfo(String uri) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        StringTokenizer st = new StringTokenizer(uri, "/");
        String uris[] = new String[st.countTokens()];
        
        int idx = 0;
        while (st.hasMoreTokens()) {
            uris[idx++] = st.nextToken();
        }
        
        String firstPath = null;
        if (uris.length > 1) {
            firstPath = "/" + uris[0];
        }
        
        resultMap.put("firstPath", firstPath);
        resultMap.put("fullPath", uri);
        
        return resultMap;
    }
}
