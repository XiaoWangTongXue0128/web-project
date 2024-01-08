package com.duyi.his.domain;

import java.io.Serializable;
import java.util.Date;

public class Fun implements Serializable {

    private Long fid ;
    private String fname ;
    private Integer ftype ;
    private String furl ;
    private Long pid ;
    private String auth_flag ;
    private Date create_time;
    private Long create_uid ;
    private Date update_time ;
    private Long update_uid ;
    private Integer delete_flag ;
    private String yl1 ; //赋予新的含义， 存储功能图标的class选择器 "layui-icon-user"
    private String yl2 ;
    private String yl3 ;
    private String yl4 ;

    private String create_uname ;
    private String update_uname ;
    private String pname ;

    public Fun(Long fid, String fname, Integer ftype, String furl,Long pid, String auth_flag, Date create_time, Long create_uid, String yl1, String yl2, String yl3, String yl4) {
        this.fid = fid;
        this.fname = fname;
        this.ftype = ftype;
        this.furl = furl;
        this.pid = pid;
        this.auth_flag = auth_flag;
        this.create_time = create_time;
        this.create_uid = create_uid;
        this.yl1 = yl1;
        this.yl2 = yl2;
        this.yl3 = yl3;
        this.yl4 = yl4;
    }

    public Fun() {
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public Integer getFtype() {
        return ftype;
    }

    public void setFtype(Integer ftype) {
        this.ftype = ftype;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getAuth_flag() {
        return auth_flag;
    }

    public void setAuth_flag(String auth_flag) {
        this.auth_flag = auth_flag;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Long getCreate_uid() {
        return create_uid;
    }

    public void setCreate_uid(Long create_uid) {
        this.create_uid = create_uid;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Long getUpdate_uid() {
        return update_uid;
    }

    public void setUpdate_uid(Long update_uid) {
        this.update_uid = update_uid;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getYl1() {
        return yl1;
    }

    public void setYl1(String yl1) {
        this.yl1 = yl1;
    }

    public String getYl2() {
        return yl2;
    }

    public void setYl2(String yl2) {
        this.yl2 = yl2;
    }

    public String getYl3() {
        return yl3;
    }

    public void setYl3(String yl3) {
        this.yl3 = yl3;
    }

    public String getYl4() {
        return yl4;
    }

    public void setYl4(String yl4) {
        this.yl4 = yl4;
    }

    public String getCreate_uname() {
        return create_uname;
    }

    public void setCreate_uname(String create_uname) {
        this.create_uname = create_uname;
    }

    public String getUpdate_uname() {
        return update_uname;
    }

    public void setUpdate_uname(String update_uname) {
        this.update_uname = update_uname;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFurl() {
        return furl;
    }

    public void setFurl(String furl) {
        this.furl = furl;
    }
}
