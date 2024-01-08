package com.duyi.his.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

   /** 基本属性 */
   private Long uid ;
   private String uname ;
   private String upass;
   private String zjm ;
   private String phone ;
   private String mail ;
   private String truename ;
   private String sex ;
   private Integer age ;
   private Date create_time;
   private Long create_uid ;
   private Date update_time ;
   private Long update_uid ;
   private Integer delete_flag = 1 ;
   private String yl1 ;
   private String yl2 ;
   private String yl3 ;
   private String yl4 ;

   //未来我将表中的数据取出后，存入当前类对象
   //最终可能会展示在网页上
   //展示的时候，展示的那个create_uid，是一个创建人的编号，但希望展示的是一个人名

   /** 关联属性 */
   private String create_uname ;
   private String update_uname ;

    public User(Long uid, String uname, String upass,String zjm, String phone, String mail, String truename, String sex, Integer age, Date create_time, Long create_uid, String yl1, String yl2, String yl3, String yl4) {
        this.uid = uid;
        this.uname = uname;
        this.upass = upass ;
        this.zjm = zjm;
        this.phone = phone;
        this.mail = mail;
        this.truename = truename;
        this.sex = sex;
        this.age = age;
        this.create_time = create_time;
        this.create_uid = create_uid;
        this.yl1 = yl1;
        this.yl2 = yl2;
        this.yl3 = yl3;
        this.yl4 = yl4;
        delete_flag = 1;
    }

    public User() {

    }

    public String getUpass() {
        return upass;
    }

    public void setUpass(String upass) {
        this.upass = upass;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getZjm() {
        return zjm;
    }

    public void setZjm(String zjm) {
        this.zjm = zjm;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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
}
