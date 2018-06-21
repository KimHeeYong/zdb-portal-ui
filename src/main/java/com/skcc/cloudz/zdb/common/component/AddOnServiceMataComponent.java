package com.skcc.cloudz.zdb.common.component;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.skcc.cloudz.zdb.common.domain.vo.AddOnServiceMataVo;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AddOnServiceMataComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String userId;
    private List<AddOnServiceMataVo> addOnServiceMetaVoList;
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public List<AddOnServiceMataVo> getAddOnServiceMetaVoList() {
        return addOnServiceMetaVoList;
    }

    public void setAddOnServiceMetaVoList(List<AddOnServiceMataVo> addOnServiceMetaVoList) {
        this.addOnServiceMetaVoList = addOnServiceMetaVoList;
    }

    @PreDestroy
    public void cleanUp() throws Exception {
        this.userId = null;
        this.addOnServiceMetaVoList = null;
    }

}
