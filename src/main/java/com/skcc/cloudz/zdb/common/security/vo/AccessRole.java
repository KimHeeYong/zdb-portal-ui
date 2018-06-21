package com.skcc.cloudz.zdb.common.security.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessRole {
    CLUSTER_ADMIN("cluster-admin", 5),
    ADMIN("admin", 4),
    EDITOR("editor", 3),
    VIEW("view", 2),
    NONE("none", 1)
    ;
    
    private String name;
    private int value;

    private AccessRole(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
