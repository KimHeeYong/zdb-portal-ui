package com.skcc.cloudz.zdb.portal.domain.dto;

import lombok.Data;

@Data
public class ZdbRestDTO {

	private int code;

	private String message;

	private String txId;

	private Throwable throwable;

	private long timestamp;

	private Result result;
}
