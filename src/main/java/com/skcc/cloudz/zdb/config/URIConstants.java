package com.skcc.cloudz.zdb.config;

public class URIConstants {

	// namespace list
	public static final String URI_GET_NAMESPACES = "/api/v1/namespaces";

	public static final String URI_GET_NAMESPACE_RESOURCE = "/iam/metrics/namespaces?userId={userId}";
	
	public static final String URI_GET_SERVICES = "/api/v1/service/services";

	public static final String URI_GET_SERVICES_WITH_NAMESPACE = "/api/v1/{namespace}/service/services";
	
	public static final String URI_GET_TAGS = "/api/v1/tags";

	public static final String URI_GET_TAGS_WITH_NAMESPACE = "/api/v1/{namespace}/tags";

	public static final String URI_GET_CONNECTION_INFO = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/connection";
	
	public static final String URI_GET_NODE_COUNT = "/api/v1/nodeCount";

	public static final String URI_GET_SERVICE = "/api/v1/{namespace}/{serviceType}/service/services/{serviceName}";

	public static final String URI_GET_POD_RESOURCE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/pods/resource";
	
	public static final String URI_RESTART_SERVICE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/restart";

	public static final String URI_RESTART_POD = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/pod/{podName}/restart";

	public static final String URI_GET_POD_METRICS = "/api/v1/{namespace}/service/{podName}/metrics";

	public static final String URI_GET_DB_VARIABLES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/variables";
	
	public static final String URI_UPDATE_DB_VARIABLES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/variables"; 

	public static final String URI_UPDATE_CONFIG = "/api/v1/{namespace}/mariadb/service/{serviceName}/config"; 

	public static final String URI_UPDATE_SCALE_UP = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/scaleUp"; 
	
	public static final String URI_UPDATE_SCALE_OUT = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/scaleOut";

	public static final String URI_CREATE_DEPLOYMENT = "/api/v1/{namespace}/{serviceType}/service";
	
	public static final String URI_SET_NEW_PASSWORD = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/passwd"; 

	public static final String URI_DELETE_SERVICE_INSTANCE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}"; 

	public static final String URI_CREATE_TAG = "/api/v1/{namespace}/tag/{serviceName}";
	
	public static final String URI_DELETE_TAG = "/api/v1/{namespace}/tag/{serviceName}";

	public static final String URI_CREATE_PUBLIC_SERVICE = "/api/v1/{namespace}/{serviceType}/{serviceName}/public-service";
	
	public static final String URI_DELETE_PUBLIC_SERVICE = "/api/v1/{namespace}/{serviceType}/{serviceName}/public-service";

	public static final String URI_GET_EVENTS = "/api/v1/events?namespace={namespace}&kind={kind}&serviceName={serviceName}&startTime={startTime}&endTime={endTime}&keyword={keyword}";

	public static final String URI_GET_USER_GRANTS = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/userGrants";
	
	public static final String URI_GET_OPERATION_EVENTS = "/api/v1/operationEvents?namespace={namespace}&serviceName={serviceName}&startTime={startTime}&endTime={endTime}&keyword={keyword}";

	public static final String URI_GET_POD_LOG = "/api/v1/{namespace}/log/{podname}";

	public static final String URI_GET_SLOW_LOG = "/api/v1/{namespace}/slowlog/{podname}";

	public static final String URI_GET_MY_CNF = "/api/v1/{namespace}/{serviceName}/mycnf";

	public static final String URI_GET_ALL_DB_VARIABLES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/allVariables";
	
	public static final String URI_GET_BACKUP_LIST = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/backup-list";

	public static final String URI_GET_SCHEDULE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/schedule";

	public static final String URI_GET_SCHEDULE_INFO_LIST = "/api/v1/scheduleInfo-list?namespace={namespace}";
	
	public static final String URI_IS_AVAILABLE = "/api/v1/{namespace}/avaliable?memory={memory}&cpu={cpu}&clusterEnabled={clusterEnabled}";

	public static final String URI_UPDATE_USER_NAMESPACES = "/api/v1/updateUserNamespaces";
	
	public static final String URI_UPDATE_SCHEDULE = "/api/v1/{namespace}/{serviceType}/service/schedule";

	public static final String URI_UPDATE_BACKUP = "/api/v1/{namespace}/{serviceType}/service/backup";

	public static final String URI_DELETE_BACKUP = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{backupId}/delete";

	public static final String URI_DOWNLOAD_BACKUP = "/api/v1/download/{backupId}";
	
	public static final String URI_RESTORE_FROM_BACKUP = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{backupId}/restore";

	public static final String URI_GET_ZDB_CONFIG = "/api/v1/{namespace}/zdbconfigs";

	public static final String URI_UPDATE_ZDB_CONFIGS = "/api/v1/zdbconfig";

	public static final String URI_CREATE_ZDB_CONFIG = "/api/v1/zdbconfig";
	
	public static final String URI_UPDATE_AUTO_FAIL_OVER_ENABLE = "/api/v1/failover/{namespace}/{serviceType}/{serviceName}/{enable}";

	public static final String URI_GET_SERVICE_FAIL_OVER_STATUS = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/svc/status";
	
	public static final String URI_UPDATE_SERVICE_CHANGE_MASTERTOSLAVE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/svc/masterToslave";

	public static final String URI_UPDATE_CHANGE_PORT = "/api/v1/{namespace}/{serviceType}/{serviceName}/changePort";

	public static final String URI_SERVICE_ON = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{stsName}/on";
	
	public static final String URI_SERVICE_OFF = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{stsName}/off";
	
}