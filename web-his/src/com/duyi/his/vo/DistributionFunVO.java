package com.duyi.his.vo;

import com.duyi.his.domain.Role;
import com.duyi.his.domain.User;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class DistributionFunVO implements Serializable {
    private Role role ;
    private List<Map> funs ;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Map> getFuns() {
        return funs;
    }

    public void setFuns(List<Map> funs) {
        this.funs = funs;
    }

    public DistributionFunVO(Role role, List<Map> funs) {
        this.role = role;
        this.funs = funs;
    }

    public DistributionFunVO() {
    }
}
