package com.duyi.his.service;

import com.duyi.his.vo.DistributionFunVO;
import com.duyi.his.vo.DistributionRoleVO;

public interface AuthService {

    public DistributionRoleVO findDistributionInfoForRole(Long uid);

    public void distributionRole(Long uid , String ridStr,Long create_uid) ;

    public DistributionFunVO findDistributionInfoForFun(Long rid);


    public void distributionFun(Long rid , String fidStr,Long create_uid) ;
}
