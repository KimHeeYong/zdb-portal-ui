package com.skcc.cloudz.zdb.api.iam.domain.vo;

import java.util.Map;

public class ApiResponseVo {
    private String msg;
    private String code;
    private Map<String, Object> data;
    
    public ApiResponseVo() {}

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
    
}
