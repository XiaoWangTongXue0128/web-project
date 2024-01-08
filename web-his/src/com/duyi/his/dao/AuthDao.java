package com.duyi.his.dao;

import java.util.Map;

public interface AuthDao {

    public void removeRelationshipForUserAndRole(Long uid);

    /**
     *
     * @param param {uid , rid , create_uid}
     */
    public void addRelationshipForUserAndRole(Map<String,Object> param);

    public void removeRelationshipForRoleAndFun(Long rid);

    /**
     *
     * @param param {rid , fid , create_uid}
     */
    public void addRelationshipForRoleAndFun(Map<String,Object> param);
}
