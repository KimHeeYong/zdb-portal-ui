package com.skcc.cloudz.zdb.api.iam.domain.vo;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ClusterRole {
	NONE("none"), 
	VIEW("view"), 
	EDIT("edit"), 
	MEMBER("member"),
	ADMIN("admin"), 
	CLUSTER_ADMIN("cluster-admin"),
	ZDB_ADMIN("zdb-admin"),
	ZDB_USER("zdb-user"),	
	@Deprecated
	DEPLOY_MANAGER("deploy-manager"),
	CICD_MANAGER("cicd-manager"),
	DEVELOPER("developer");

	private String role;

	private ClusterRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	@JsonValue
	public String jsonValue() {
		return this.role;
	}

	public String toString() {
		return this.role;
	}

	@JsonCreator
	public static ClusterRole getClusterRole(String role) {
		for (ClusterRole s : values()) {
			if (s.getRole().equals(role)) {
				return s;
			}
		}

		throw new IllegalArgumentException("[" +role + "] is invalid");
	}

	// Group Function
	private static final ClusterRole[] CLUSTER_ROLES = {CLUSTER_ADMIN, MEMBER};
	private static final ClusterRole[] NAMESPACE_ROLES = {ADMIN, CICD_MANAGER, DEVELOPER};
	private static final ClusterRole[] METRIC_ROLES = {CLUSTER_ADMIN, MEMBER, ADMIN, CICD_MANAGER, DEVELOPER, NONE};
	private static final ClusterRole[] ZDB_ROLES = {CLUSTER_ADMIN, ZDB_ADMIN , ZDB_USER};

	public static ClusterRole[] getClusterGroup() {
		return CLUSTER_ROLES;
	}

	public static ClusterRole[] getNamespaceGroup() {
		return NAMESPACE_ROLES;
	}

	public static ClusterRole[] getMetricGroup() {
		return METRIC_ROLES;
	}
	
	public static ClusterRole[] getZdbGroup() {
		return ZDB_ROLES;
	}
}