package com.skcc.cloudz.zdb.portal.domain.dto;

import java.util.List;
import java.util.Map;

import com.zdb.core.domain.BackupEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.DBUser;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.Mycnf;
import com.zdb.core.domain.RequestEvent;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ScheduleInfoEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;

import io.fabric8.kubernetes.api.model.Namespace;
import io.fabric8.kubernetes.client.dsl.PodResource;
import lombok.Data;

@Data
public class Result {

	private List<ServiceOverview> serviceoverviews;

	private List<Tag> tags;
	
	private ServiceOverview serviceoverview;
	
	private List<Namespace> namespaces;
	
	private ConnectionInfo connectionInfo;

	private int nodes;

	private PodResource podResource;
	
	private List<Mycnf> mariaDBConfig;
	
	private Map<String,String> redisConfig;
	
	private List<Usage> metricsCpuUsage;

	private List<Usage> metricsMemoryUsage;
	
	private Map<String,Object> changePassword;
	
	private List<EventMetaData> serviceEvents;

	private List<RequestEvent> operationEvents;

	private List<BackupEntity> backupList;

	private List<ScheduleInfoEntity> scheduleInfoList;
	
	private String[] podLog;

	private String[] slowLog;
	
	private ScheduleEntity schedule; 
	
	private String[] mycnf;

	private List<DBUser> userGrants;
}
