package com.skcc.cloudz.zdb.portal.domain.dto;

import java.util.List;
import java.util.Map;

import com.zdb.core.domain.AlertingRuleEntity;
import com.zdb.core.domain.ConnectionInfo;
import com.zdb.core.domain.DBUser;
import com.zdb.core.domain.Database;
import com.zdb.core.domain.EventMetaData;
import com.zdb.core.domain.MariaDBVariable;
import com.zdb.core.domain.Mycnf;
import com.zdb.core.domain.PersistentVolumeClaimEntity;
import com.zdb.core.domain.RequestEvent;
import com.zdb.core.domain.ScheduleEntity;
import com.zdb.core.domain.ScheduleInfoEntity;
import com.zdb.core.domain.ServiceOverview;
import com.zdb.core.domain.Tag;
import com.zdb.core.domain.UserPrivileges;
import com.zdb.core.domain.ZDBConfig;
import com.zdb.core.domain.ZDBNode;

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

	private List<ZDBNode> nodeList;

	private PodResource podResource;
	
	private List<Mycnf> mariaDBConfig;
	
	private Map<String,String> redisConfig;
	
	private List<Usage> metricsCpuUsage;

	private List<Usage> metricsMemoryUsage;
	
	private Map<String,Object> changePassword;
	
	private List<EventMetaData> serviceEvents;

	private List<RequestEvent> operationEvents;

	private List<Map<String,String>> backupList;

	private Map<String,String> backupResult;

	private List<ScheduleInfoEntity> scheduleInfoList;
	
	private String[] podLog;

	private String[] slowLog;

	private String[] backupLog;
	
	private ScheduleEntity schedule; 
	
	private String[] mycnf;

	private List<DBUser> userGrants;
	
	private List<ZDBConfig> zdbConfig;
	
	private String status;
	
	private List<String> workerPools;
	
	private List<Map<String,Object>> pods;
	
	private List<Database> databases;
	
	private String fileLog;
	
	private List<AlertingRuleEntity> alertRules;

	private AlertingRuleEntity alertRule;

	private List<Map<String,String>> processes;
	
	private List<String> services;
	
	private List<PersistentVolumeClaimEntity> storages;
	
	private Map<String,List<String>> storagesData;
	
	private Map<String,String> databaseStatus;
	
	private Map<String,String> databaseConnection;
	
	private Map<String,String> databaseStatusVariables;
	
	private Map<String,String> databaseSystemVariables;
	
	private List<MariaDBVariable> databaseVariables;

	private List<UserPrivileges> userPrivileges;
}
