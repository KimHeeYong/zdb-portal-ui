package com.skcc.cloudz.zdb.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils{

	@Nullable
	public static String doubleQuote(@Nullable String str) {
		return (str != null ? "\"" + str + "\"" : null);
	}
}
