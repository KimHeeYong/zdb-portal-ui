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

	public static final String URI_GET_PODS = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/pods";

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
	
	public static final String URI_GET_OPERATION_EVENTS = "/api/v1/operationEvents?namespace={namespace}&serviceName={serviceName}&startTime={startTime}&endTime={endTime}&keyword={keyword}&type={type}&backupEventExceptYn={backupEventExceptYn}";

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
	
	public static final String URI_RESTORE_FROM_BACKUP = "/api/v1/restoreBackup/{backupId}";

	public static final String URI_GET_BACKUP_RESULT = "/api/v1/getBackupResult/{backupId}";

	public static final String URI_GET_BACKUP_LOG = "/api/v1/getBackupLog/{backupId}";
	
	public static final String URI_GET_ZDB_CONFIG = "/api/v1/{namespace}/zdbconfigs";

	public static final String URI_UPDATE_ZDB_CONFIGS = "/api/v1/zdbconfig";

	public static final String URI_CREATE_ZDB_CONFIG = "/api/v1/zdbconfig";
	
	public static final String URI_UPDATE_AUTO_FAIL_OVER_ENABLE = "/api/v1/failover/{namespace}/{serviceType}/{serviceName}/{enable}";

	public static final String URI_GET_AUTO_FAIL_OVER_ENABLED_SERVICES = "/api/v1/failover/{namespace}/enabled-services"; 

	public static final String URI_GET_SERVICE_FAIL_OVER_STATUS = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/svc/status";
	
	public static final String URI_UPDATE_SERVICE_CHANGE_MASTERTOSLAVE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/svc/masterToslave";

	public static final String URI_UPDATE_SERVICE_CHANGE_SLAVETOMASTER = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/svc/slaveTomaster";
																						
	public static final String URI_UPDATE_SERVICE_FAILBACK = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/failback";

	public static final String URI_UPDATE_CHANGE_PORT = "/api/v1/{namespace}/{serviceType}/{serviceName}/changePort";

	public static final String URI_SERVICE_ON = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{stsName}/on";
	
	public static final String URI_SERVICE_OFF = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/{stsName}/off";

	public static final String URI_GET_WORKERPOOLS = "/api/v1/workerpools";

	public static final String URI_CREATE_DB_USER = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/accounts/{accountId}";
	
	public static final String URI_UPDATE_DB_USER = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/accounts/{accountId}";
	
	public static final String URI_DELETE_DB_USER = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/accounts/{accountId}";
	
	public static final String URI_GET_DATABASES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/databases";

	public static final String URI_CREATE_DATABASE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/database/{databaseName}";

	public static final String URI_DELETE_DATABASE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/database/{databaseName}";

	public static final String URI_GET_FILE_LOG = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/fileLogs/{logType}/{startDate}/{endDate}";

	public static final String URI_GET_ALL_SERVICES = "/api/v1/allServices";

	public static final String URI_GET_ALERT_RULES = "/api/v1/{namespace}/alert/rules";

	public static final String URI_GET_ALERT_RULES_IN_SERVICE = "/api/v1/{namespace}/{serviceName}/alert/rules";

	public static final String URI_GET_ALERT_RULE = "/api/v1/{namespace}/alert/rule/{alert}";

	public static final String URI_UPDATE_ALERT_RULE = "/api/v1/{namespace}/{serviceType}/{serviceName}/alert/rule";

	public static final String URI_UPDATE_DEFAULT_ALERT_RULE = "/api/v1/{namespace}/{serviceType}/{serviceName}/alert/defaultRule";
	
	public static final String URI_GET_PROCESSES = "/api/v1/{namespace}/{serviceType}/process/{podName}";

	public static final String URI_DELETE_PROCESS = "/api/v1/{namespace}/{serviceType}/process/{podName}/{pid}";

	public static final String URI_GET_STORAGES = "/api/v1/storages?namespace={namespace}&keyword={keyword}&app={app}&storageClassName={storageClassName}"
													+"&billingType={billingType}&phase={phase}&stDate={stDate}&edDate={edDate}";	

	public static final String URI_GET_STORAGES_DATA = "/api/v1/storages/data";

	public static final String URI_ADD_BACKUP_DISK = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/addBackupDisk/{backupDiskSize}";

	public static final String URI_REMOVE_BACKUP_DISK = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/removeBackupDisk";

	public static final String URI_GET_DATABASE_STATUS = "/api/v1/{namespace}/{serviceType}/database/status/{podName}";

	public static final String URI_GET_DATABASE_CONNECTION = "/api/v1/{namespace}/{serviceType}/database/connection/{podName}";

	public static final String URI_GET_DATABASE_STATUS_VARIABLES = "/api/v1/{namespace}/{serviceType}/database/statusVariables/{podName}";
	
	public static final String URI_GET_DATABASE_SYSTEM_VARIABLES = "/api/v1/{namespace}/{serviceType}/database/systemVariables/{podName}";

	public static final String URI_GET_DATABASE_VARIABLES = "/api/v1/{serviceType}/database/variables";

	public static final String URI_GET_USE_DATABASE_VARIABLES = "/api/v1/{namespace}/{serviceType}/{serviceName}/database/useVariables";

	public static final String URI_UPDATE_USE_DATABASE_VARIABLES = "/api/v1/{namespace}/{serviceType}/{serviceName}/database/useVariables";

	public static final String URI_GET_USER_PRIVILEGES = "/api/v1/{namespace}/{serviceType}/{serviceName}/database/userPrivileges";

	public static final String URI_CREATE_USER_PRIVILEGES = "/api/v1/{namespace}/{serviceType}/{serviceName}/database/userPrivileges";
	
	public static final String URI_UPDATE_USER_PRIVILEGES = "/api/v1/{namespace}/{serviceType}/{serviceName}/database/userPrivileges";

	public static String URI_GET_NODES_INFO = "/api/v1/nodesInfo";
	
	public static final String URI_GET_CREDENTIAL_CONFIRM = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/credentialConfirm";
	
	public static final String URI_GET_USER_PRIVILEGES_FOR_SCHEMA = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/userPrivileges/{user}/{host}/{schema}";
	
	public static final String URI_GET_LAST_FAIL_OVER_INFO = "/api/v1/failover/{namespace}/{serviceName}/info"; 

	public static final String URI_GET_FAIL_OVER_SERVICES_WITH_NAMESPACES = "/api/v1/{namespace}/failover/services";
	
	public static final String URI_GET_MIGRATION_BACKUP = "/api/v1/migrationBackup";
	
	public static final String URI_GET_MIGRATION_BACKUP_SERVICE_LIST = "/api/v1/migrationBackupServiceList?namespace={namespace}&serviceType={serviceType}&type={type}";
	
	public static final String URI_GET_MIGRATION_BACKUP_LIST = "/api/v1/migrationBackupList?namespace={namespace}&serviceType={serviceType}&serviceName={serviceName}";
	
	public static final String URI_GET_MIGRATION_BACKUP_AGENT = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/migration/{backupId}";
	
	public static final String URI_PUT_WORKERPOOL_NODE = "/api/v1/nodes/{node}/workerpool/{workerpool}";
	
	public static final String URI_ABORT_BACKUP = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/abortBackup/{txId}";
	
	public static final String URI_GET_PURGE_BINLOG = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/purgeBinlog";
	
}