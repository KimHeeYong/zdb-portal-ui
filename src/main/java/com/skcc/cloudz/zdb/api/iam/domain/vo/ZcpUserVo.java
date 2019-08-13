package com.skcc.cloudz.zdb.api.iam.domain.vo;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ZcpUserVo {
    private String id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+9")
    private Date createdDate;
    private Boolean enabled;
    private Boolean emailVerified;
    private List<String> namespaces;
    private String defaultNamespace;
    private int usedNamespace;
    
	private Boolean totp;
	private ClusterRole clusterRole;
	private List<CredentialActionType> requiredActions;
	private ClusterRole namespacedRole;
	private Boolean zdbAdmin;
}
