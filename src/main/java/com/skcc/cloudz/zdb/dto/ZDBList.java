package com.skcc.cloudz.zdb.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ZDBList {

	private int code;
	private String txId;
	private Date timestamp;
	
	private String apiVersion;
	private String kind;
	private String metadata;
	private String annotations;
	private String creationTimestamp;
	private String generation;
	private String name;
	private String namespace;
	
}
