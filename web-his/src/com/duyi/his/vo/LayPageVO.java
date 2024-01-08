package com.duyi.his.vo;

import java.io.Serializable;
import java.util.List;

/**
 * layui-table组件所需要的数据结构
 * treetable也可以使用
 */
public class LayPageVO implements Serializable {

    private List<?> data ;
    private String msg ;
    private Integer code ;
    private Long count ;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LayPageVO() {
    }
}
