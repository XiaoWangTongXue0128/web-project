package com.duyi.his.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import cn.hutool.extra.pinyin.PinyinUtil;
import com.duyi.his.dao.UserDao;
import com.duyi.his.dao.impl.UserDaoImpl;
import com.duyi.his.domain.User;
import com.duyi.his.service.UserService;
import com.duyi.his.vo.PageVO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {

    UserDao dao = new UserDaoImpl() ;
    private static final String DEFAULT_PASS = "123" ;
    private static final Digester md5 = new Digester(DigestAlgorithm.MD5);

    @Override
    public User findByUname(String uname) {
        return dao.findByUname(uname);
    }

    @Override
    public void updatePwd(long uid, String upass) {
        dao.updatePwd(uid,upass);
    }

    @Override
    public PageVO list(Map<String, Object> param) {
        //要做的是分页过滤查询
        //过滤条件在param中直接使用，有就用，没有就拉倒
        //分页数据中我们先确保page页码是正确的
        //  页码有可能超标

        //处理下限
        Integer page = (Integer) param.get("page");
        Integer rows = (Integer) param.get("rows");
        page = Math.max(1,page);

        //处理上限
        //一共有多少页，上限是多少，不确定，需要算一下 total/rows  100/9=11+1=12页
        //如果要计算，就需要total，所以需要先从数据库中查询总数，再计算。
        //注意： 我们这次列表查询不仅有分页，还有过滤
        //      所以我们要查的是在这个过滤条件的基础上，总页数
        //      查询时需要携带过滤参数，参数都在param中
        //注意：获取总数后，有一种可能，没有记录0，此时计算的最大页=0，需要将最大页变成1
        long total = dao.listTotal(param);
        int max = (int) (total%rows==0? total/rows :  (total/rows + 1));
        max = Math.max(1,max);
        page = Math.min(page,max);

        //更新一下
        param.put("page",page);

        //数据库查询数据
        //根据文档分析，需要将功能分页的参数 转换成 数据库分页的参数  page+rows --> start+length
        int start = (page-1)*rows ;
        int length = rows ;
        param.put("start",start);
        param.put("length",length);

        List<User> users = dao.list(param);

        //接下来就是将数据返回给网页展示
        //根据前端展示的需求需要返回list，page，total，rows，param(过滤条件)
        //如何通过一个返回值携带多个信息呢
        //  1. 多个信息组装成map （适合临时结构）
        //  2. 单独创建一个类    （适合经常使用结构）
        return new PageVO(users,total,rows,page,param,max);
    }

    @Override
    public String save(User user,long create_uid) {
        //user对象是缺少数据，有些数据需要补全，有些不需要
        //1. 缺少uid， 保存时由数据库自动生成   不用管
        //2. 缺少助记码，用户名（中文）对应的拼音表示，Hutool中提供了拼音处理 PinyinUtil
        //3. 缺少密码，新建时提供默认的密码（123），123需要将加密，使用Hutool工具实现加密
        //4. 缺少创建者的id，应该就是当前登录的用户，信息在session中。
        //5. 缺少创建时，就是当前的系统时间（java生成 new Date()，sql生成  now()）
        //6. 缺少update信息（修改人，修改时间） 因为是新建，没有这部分信息，不需要处理
        //7. 预留字段咱们没有确定含义，不需要处理
        //8. 缺少删除字段，默认为1 (java处理，sql处理）

        String zjm = PinyinUtil.getPinyin(user.getUname(),"");
        user.setZjm(zjm);

        String upass = md5.digestHex(DEFAULT_PASS) ;
        user.setUpass(upass);

        user.setCreate_uid(create_uid);

        user.setCreate_time( new Date() );

        user.setDelete_flag(1);

        if(dao.findTotalByUname(user.getUname())>0){
            return "uname" ;
        }
        if(dao.findTotalByUname(user.getZjm())>0){
            return "zjm" ;
        }
        if(dao.findTotalByUname(user.getPhone())>0){
            return "phone" ;
        }
        if(dao.findTotalByUname(user.getMail())>0){
            return ",mail" ;
        }

        dao.save(user);

        return "" ;

    }

    @Override
    public void delete(Map<String, Long> param) {
        dao.delete(param);
    }

    @Override
    public void deletes(Map<String, Object> param) {
        String uids = (String) param.get("uids");
        String[] uidArray = uids.split(",");

        //设计一个dao.delete所需要的map<String,Long>
        //map定义在循环外面，每删一条，就更换一次uid，只需要一个map容器，节约内存
        Map<String,Long> deleteParam = new HashMap<>();
        deleteParam.put("update_uid", (Long) param.get("update_uid"));

        for(String uid : uidArray){
            deleteParam.put("uid",Long.parseLong(uid));
            dao.delete(deleteParam);
        }
    }

    @Override
    public User findById(long uid) {
        return dao.findById(uid);
    }

    @Override
    public String update(User user) {
        //修改前需要判断一下电话和邮箱是否出现了重复
        //注意：这个重复不应该包括当前用户信息
        User old = dao.findById(user.getUid());
        if(!user.getPhone().equals(old.getPhone())){
            //修改电话号码，考虑是不是和其他人的电话重复了
            if(dao.findTotalByUname(user.getPhone()) > 0){
                return "phone";
            }
        }
        if(!user.getMail().equals(old.getMail())){
            //更换了邮箱
            if(dao.findTotalByUname(user.getMail()) > 0){
                return "mail";
            }
        }

        //数据没有问题，可以修改
        dao.update(user);

        return null;
    }

    @Override
    public String saves(List<User> users,long create_uid) {
        String tip = "" ;
        int i = 1 ;
        int count1 = 0 ;//记录成功保存的数量
        int count2 = 0 ;//记录保存失败的数量
        for(User user : users){
            //缺少数据，zjm，upass，create时间，create人，delete_flag
            String result = this.save(user,create_uid);
            switch (result){
                case "uname" : count2++; tip+="第【"+i+"】条，用户名【"+user.getUname()+"】重复\\n";break ;
                case "zjm" : count2++;tip+="第【"+i+"】条，助记码【"+user.getZjm()+"】重复\\n";break ;
                case "phone" : count2++; tip+="第【"+i+"】条，电话【"+user.getPhone()+"】重复\\n";break ;
                case "mail" : count2++ ;tip+="第【"+i+"】条，邮箱【"+user.getMail()+"】重复\\n";break ;
                default:count1++;break ;
            }
            i++;
        }
        tip = "此次成功保存【"+count1+"】条记录，失败【"+count2+"】条记录\\n"+tip;
        return tip ;
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public void resetPass(long uid, long update_uid) {
        String upass = md5.digestHex(DEFAULT_PASS);

        Map<String,Object> param = new HashMap<>();
        param.put("uid",uid);
        param.put("upass",upass);
        param.put("update_uid",update_uid);

        dao.resetPass(param);
    }


}
