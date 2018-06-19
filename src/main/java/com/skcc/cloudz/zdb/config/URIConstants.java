package com.skcc.cloudz.zdb.config;

public class URIConstants {

	// namespace list
	public static final String URI_GET_NAMESPACES = "/api/v1/namespaces";
	
	public static final String URI_GET_SERVICES = "/api/v1/service/services";

	public static final String URI_GET_SERVICES_WITH_NAMESPACE = "/api/v1/{namespace}/service/services";
	
	public static final String URI_GET_TAGS = "/api/v1/tags";

	public static final String URI_GET_TAGS_WITH_NAMESPACE = "/api/v1/{namespace}/tags";

	public static final String URI_GET_CONNECTION_INFO = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/connection";

	public static final String URI_GET_SERVICE = "/api/v1/{namespace}/{serviceType}/service/services/{serviceName}";

	public static final String URI_GET_POD_RESOURCE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/pods/resource";
	
	public static final String URI_RESTART_SERVICE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/restart";

	public static final String URI_GET_POD_METRICS = "/api/v1/{namespace}/service/{podName}/metrics";

	public static final String URI_GET_DB_VARIABLES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/variables";
	
	public static final String URI_UPDATE_DB_VARIABLES = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/variables"; 

	public static final String URI_UPDATE_CONFIG = "/api/v1/{namespace}/mariadb/service/{serviceName}/config"; 

	public static final String URI_UPDATE_SCALE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}"; 
	
	public static final String URI_CREATE_DEPLOYMENT = "/api/v1/{namespace}/{serviceType}/service";
	
	public static final String URI_SET_NEW_PASSWORD = "/api/v1/{namespace}/{serviceType}/service/{serviceName}/passwd"; 

	public static final String URI_DELETE_SERVICE_INSTANCE = "/api/v1/{namespace}/{serviceType}/service/{serviceName}"; 

	public static final String URI_CREATE_TAG = "/api/v1/{namespace}/tag/{serviceName}";
	
	public static final String URI_DELETE_TAG = "/api/v1/{namespace}/tag/{serviceName}";

	public static final String URI_GET_EVENTS_WITH_SERVICE = "/api/v1/{namespace}/events/{kind}/{serviceName}";

}