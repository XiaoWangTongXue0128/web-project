package com.duyi.his.service;

import com.duyi.his.domain.Fun;
import com.duyi.his.vo.MenuVO;

import java.util.List;

public interface FunService {

    public List<Fun> findAll();

    public String save(Fun fun);

    public Fun findById(Long fid) ;

    public String update(Fun fun) ;

    public List<MenuVO> findParentMenuForChange(Long fid);

    public long childrenCount(long fid);

    public void delete(long fid,long update_uid) ;
}
