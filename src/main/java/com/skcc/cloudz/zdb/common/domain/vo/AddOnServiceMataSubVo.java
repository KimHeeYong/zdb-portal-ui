package com.skcc.cloudz.zdb.common.domain.vo;

public class AddOnServiceMataSubVo implements Comparable<AddOnServiceMataSubVo> {
    private String id;
    private String name;
    private int order;
    private String url;
    private String target;
    private boolean enable;
    
    public AddOnServiceMataSubVo() {}

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public int compareTo(AddOnServiceMataSubVo addOnServiceMataSubVo) {
        if (this.order < addOnServiceMataSubVo.getOrder()) {
            return -1;
        } else if (this.order > addOnServiceMataSubVo.getOrder()) {
            return 1;
        }
        
        return 0;
    }
    
}
