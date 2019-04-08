package com.skcc.cloudz.zdb.common.domain.vo;

import lombok.Data;

@Data
public class AddOnServiceMataSubVo implements Comparable<AddOnServiceMataSubVo> {
    private String id;
    private String name;
    private int order;
    private String url;
    private String target;
    private boolean enable;
    private String folder;
    
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
