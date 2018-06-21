package com.skcc.cloudz.zdb.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {

	public static Map<String, String> getMapFromRequest(HttpServletRequest request) {
		Enumeration<String> enumeration = request.getParameterNames();
		Map<String, String> map = new HashMap<>();
		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();
			map.put(key, request.getParameter(key));
		}
		return map;
	}

}
