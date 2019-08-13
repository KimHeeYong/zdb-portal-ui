package com.skcc.cloudz.zdb.common.domain.vo;

import java.util.List;

import com.skcc.cloudz.zdb.api.iam.domain.vo.ClusterRole;


public class AddOnServiceMataVo implements Comparable<AddOnServiceMataVo> {
    private String id;
    private String name;
    private int order;
    private String url;
    private String target;
    private List<AddOnServiceMataSubVo> sub;
    private List<ClusterRole> clusterRoles;
    private boolean enable;
    private String folder;
    
    public AddOnServiceMataVo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<AddOnServiceMataSubVo> getSub() {
        return sub;
    }

    public void setSub(List<AddOnServiceMataSubVo> sub) {
        this.sub = sub;
    }
    
    public List<ClusterRole> getClusterRoles() {
        return clusterRoles;
    }

    public void setClusterRoles(List<ClusterRole> clusterRoles) {
        this.clusterRoles = clusterRoles;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int compareTo(AddOnServiceMataVo addOnServiceMataVo) {
        if (this.order < addOnServiceMataVo.getOrder()) {
            return -1;
        } else if (this.order > addOnServiceMataVo.getOrder()) {
            return 1;
        }
        
        return 0;
    }

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}
    
}
