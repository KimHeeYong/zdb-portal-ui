package com.skcc.cloudz.zdb.portal.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class NamespaceResource {

	private String name;
	private String status;
	private Date creationDate;
	private int userCount;
	private BigDecimal cpuRequestsPercentage;
	private BigDecimal memoryRequestsPercentage;
	private BigDecimal cpuLimitsPercentage;
	private BigDecimal memoryLimitsPercentage;
	private String hardCpuRequests;
	private String usedCpuRequests;
	private String hardMemoryRequests;
	private String usedMemoryRequests;
	private String hardCpuLimits;
	private String usedCpuLimits;
	private String hardMemoryLimits;
	private String usedMemoryLimits;
}
