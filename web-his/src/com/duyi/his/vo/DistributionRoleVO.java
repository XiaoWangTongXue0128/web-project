package com.duyi.his.vo;

import com.duyi.his.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DistributionRoleVO implements Serializable {
    private User user ;
    private List<Map> roles ;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Map> getRoles() {
        return roles;
    }

    public void setRoles(List<Map> roles) {
        this.roles = roles;
    }

    public DistributionRoleVO(User user, List<Map> roles) {
        this.user = user;
        this.roles = roles;
    }

    public DistributionRoleVO() {
    }
}
