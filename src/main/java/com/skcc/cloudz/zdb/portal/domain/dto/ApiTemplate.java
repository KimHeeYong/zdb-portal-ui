package com.skcc.cloudz.zdb.portal.domain.dto;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.skcc.cloudz.zdb.config.CommonConstants;

public class ApiTemplate {

	public static String MATCHER_PATTERN = "\\$\\{([a-zA-Z]+)\\}";

	public static String getCreateDeployment(String serviceType) {
		String templateUrl = null;
		String inputJson = "";
		try {
			templateUrl = String.format("createDeployment-%s.template",serviceType); 	
			InputStream is = new ClassPathResource(CommonConstants.TEMPLATE_PATH + templateUrl).getInputStream();
			inputJson = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return inputJson.toString();
	}
	
	public static String getUpdateScaleUpTemplate(String serviceType) {
		String templateUrl = null;
		String inputJson = "";
		try {
			templateUrl = String.format("updateScaleUp-%s.template",serviceType); 	
			InputStream is = new ClassPathResource(CommonConstants.TEMPLATE_PATH + templateUrl).getInputStream();
			inputJson = IOUtils.toString(is, StandardCharsets.UTF_8.name());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return inputJson.toString();
	}
}
