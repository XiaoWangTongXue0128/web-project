package com.duyi.his.domain;

import java.io.Serializable;
import java.util.Date;

public class Role implements Serializable {

    private Long rid ;
    private String rname ;
    private String description ;
    private Date create_time;
    private Long create_uid ;
    private Date update_time ;
    private Long update_uid ;
    private Integer delete_flag ;
    /** 赋予新的含义，表示角色使用状态 1启用，2禁用 */
    private String yl1 ;
    private String yl2 ;

    private String create_uname ;
    private String update_uname ;

    /**
     * 保存时使用的构造器（没有rid）
     */
    public Role( String rname, String description, Date create_time, Long create_uid, String yl1, String yl2) {
        this.rname = rname;
        this.description = description;
        this.create_time = create_time;
        this.create_uid = create_uid;
        this.yl1 = yl1;
        this.yl2 = yl2;
    }

    /**
     * 修改时使用的构造器（有rid）
     * @param rid
     * @param rname
     * @param description
     * @param update_time
     * @param update_uid
     * @param yl1
     * @param yl2
     */
    public Role(Long rid, String rname, String description, Date update_time, Long update_uid, String yl1, String yl2) {
        this.rid = rid;
        this.rname = rname;
        this.description = description;
        this.update_time = update_time;
        this.update_uid = update_uid;
        this.yl1 = yl1;
        this.yl2 = yl2;
    }

    public Role() {
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "Role{" +
                "rid=" + rid +
                ", rname='" + rname + '\'' +
                ", description='" + description + '\'' +
                ", create_time=" + create_time +
                ", create_uid=" + create_uid +
                ", update_time=" + update_time +
                ", update_uid=" + update_uid +
                ", delete_flag=" + delete_flag +
                ", yl1='" + yl1 + '\'' +
                ", yl2='" + yl2 + '\'' +
                ", create_uname='" + create_uname + '\'' +
                ", update_uname='" + update_uname + '\'' +
                '}';
    }
}
