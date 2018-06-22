package com.skcc.cloudz.zdb.api.iam.domain.vo;

public class ZcpUserResVo {
    private String msg;
    private String code;
    private ZcpUserVo data;
    
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
    public ZcpUserVo getData() {
        return data;
    }
    public void setData(ZcpUserVo data) {
        this.data = data;
    }
}
