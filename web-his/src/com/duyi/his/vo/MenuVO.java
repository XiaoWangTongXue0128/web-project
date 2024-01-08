package com.duyi.his.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 功能菜单在页面展示为tree时的数据结构
 */
public class MenuVO implements Serializable {
    private Long id ;
    private String title ;
    private Boolean spread = true ;
    private List<MenuVO> children ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public List<MenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<MenuVO> children) {
        this.children = children;
    }

    public MenuVO() {
    }
}
