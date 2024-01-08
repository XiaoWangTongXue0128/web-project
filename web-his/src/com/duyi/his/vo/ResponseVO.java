package com.duyi.his.vo;

import java.io.Serializable;

/**
 * 前端ajax请求所需要的响应结构
 */
public class ResponseVO implements Serializable {
    private Integer code ;
    private String msg ;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResponseVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVO() {
    }
}
