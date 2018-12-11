package com.skcc.cloudz.zdb.portal.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ZdbRestDTO {

	private int code;
	
	private String message;
	
	private String txId;
	
	private Throwable throwable;
	
	private long timestamp;
	
	private Result result;
	
	public ZdbRestDTO(int code,String message,String txId) {
		this.code = code;
		this.message = message;
		this.txId = txId;
		this.timestamp = System.currentTimeMillis();
	}
	
}
