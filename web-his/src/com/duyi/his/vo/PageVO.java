package com.duyi.his.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 视图分页所需要的数据结构
 */
public class PageVO implements Serializable {
    private List<?> data ;
    private Long total ;
    private Integer rows ;
    private Integer page ;
    private Integer max ;
    private Integer start ;
    private Integer end ;

    /**咱们这个filter有些冗余*/
    private Map<String,Object> filter ;


    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Map<String, Object> getFilter() {
        return filter;
    }

    public void setFilter(Map<String, Object> filter) {
        this.filter = filter;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public PageVO(List<?> data, Long total, Integer rows, Integer page, Map<String, Object> filter, Integer max) {
        this.data = data;
        this.total = total;
        this.rows = rows;
        this.page = page;
        this.filter = filter;
        this.max = max;
    }

    public PageVO() {
    }

    public PageVO(List<?> data, Long total, Integer rows, Integer page, Integer max, Integer start, Integer end, Map<String, Object> filter) {
        this.data = data;
        this.total = total;
        this.rows = rows;
        this.page = page;
        this.max = max;
        this.start = start;
        this.end = end;
        this.filter = filter;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }
}
