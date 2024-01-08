package com.duyi.his.vo;

import com.duyi.his.util.ServiceProxyFactory;

import java.io.Serializable;
import java.util.List;

public class AuthMenuVO implements Serializable {
    private String fname ;
    private String furl ;
    private List<AuthMenuVO> children ;
    private String icon ;  // fun.yl1

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }

    public List<AuthMenuVO> getChildren() {
        return children;
    }

    public void setChildren(List<AuthMenuVO> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public AuthMenuVO() {
    }
}
