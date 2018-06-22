package com.skcc.cloudz.zdb.config;

public class URIConstants {

	// namespace list
	public static final String URI_GET_NAMESPACES = "/api/v1/namespaces";
	
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

	public static final String URI_GET_EVENTS = "/api/v1/events?namespace={namespace}&kind={kind}&serviceName={serviceName}&startTime={startTime}&endTime={endTime}&keyword={keyword}";

	public static final String URI_GET_POD_LOG = "/api/v1/{namespace}/log/{podname}";

	public static final String URI_GET_BACKUP_LIST = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/backup-list";

	public static final String URI_GET_SCHEDULE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/schedule";

}