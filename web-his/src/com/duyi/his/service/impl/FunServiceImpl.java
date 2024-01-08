package com.duyi.his.service.impl;

import com.duyi.his.dao.FunDao;
import com.duyi.his.dao.impl.FunDaoImpl;
import com.duyi.his.domain.Fun;
import com.duyi.his.service.FunService;
import com.duyi.his.vo.MenuVO;

import java.util.*;

public class FunServiceImpl implements FunService {

    private FunDao dao = new FunDaoImpl();

    @Override
    public List<Fun> findAll() {
        return dao.findAll() ;
    }

    @Override
    public String save(Fun fun) {
        //检测功能名称是否重复
        if(dao.totalForName(fun.getFname()) > 0){
            return "fname" ;
        }

        //可以保存
        dao.save(fun);
        return null ;
    }

    @Override
    public Fun findById(Long fid) {
        return dao.findById(fid);
    }

    @Override
    public String update(Fun fun) {
        if(dao.totalForNameAndId(fun) == 0 && dao.totalForName(fun.getFname()) > 0){
            // 第一个判断==0 表示此次修改名字发生了变化
            // 第二个判断>0 表示此次修改的新名字和其他功能名称重复
            return "fname" ;
        }

        dao.update(fun);

        return null ;

    }

    @Override
    public List<MenuVO> findParentMenuForChange(Long fid) {
        //所有的菜单
        List<Fun> funs = dao.findMenus();

        //额外增加一个主菜单选项
        Fun fun = new Fun();
        fun.setFid(-1L);
        fun.setFname("主菜单");
        fun.setPid(-2L);
        funs.add(fun);

        //去掉当前fid指定这个功能及其子菜单
        //因为我们在修改fid功能时，不能调整其父级菜单为其本身，也不能为其子菜单。
        //存储需要排除的fid
        Set<Long> excludeFidSet = new HashSet<>();
        excludeFidSet.add(fid);

        //轮询，一遍一遍的找，把所需要排除的内容都排除掉
        while (true){
            int length = funs.size();
            for (int i = 0; i < funs.size(); i++) {
                Fun f = funs.get(i);
                Long id = f.getFid();
                if (excludeFidSet.contains(id)) {
                    //当前f是需要被排除的
                    funs.remove(f);
                    i--;
                    continue;
                }
                //当前这个fun他的fid != 传入fid。 但还有可能是他的子级
                Long pid = f.getPid();
                if (excludeFidSet.contains(pid)) {
                    //当前这个fun的父级是被排除，当前这个fun也需要被排除
                    funs.remove(f);
                    //一旦当前这个fun被排除了，其子级也需要被排除
                    //所以当前这个功能的fid也需要放在被排除的集合中
                    excludeFidSet.add(id);
                    i--;
                    continue;
                }
            }
            if(funs.size() == length){
                //此次轮询没有排除任何功能，就证明功能排除结束了
                break ;
            }
        }

        //将funs集合中的数据，转换成，前端tree组件所需要的结构
        //单独的功能 需要将fid->id , fname->title
        //子父关系   需要将pid->children
        //默认设置spread=true
        //将funs->menuVOs
        //需要按照层级关系，逐层处理
        //先找到主菜单
        //在处理主菜单时，需要顺道将其子菜单处理（系统管理，系统监控，数据中心）
        //处理子菜单(系统管理)时,顺道将其子子菜单处理（用户管理，角色管理，功能管理）
        //对子菜单的处理和对主菜单处理是一样的处理机制——递归
        List<MenuVO> menuVOS = handleMenuPreLevel(-2L, funs);
        return menuVOS ;
    }

    /**
     * 找到指定父级pid的那一层子级菜单
     * 要找一组菜单，这组菜单中所有菜单的父级id就是传递的参数中指定的那个pid
     * 起初第一次找父级id为-2的那一层子级菜单——就一个，主菜单
     * @param pid
     * @param source
     * @return  返回要找到那一层的所有菜单
     */
    private List<MenuVO> handleMenuPreLevel(Long pid,List<Fun> source){
        List<MenuVO> menuVOS = new ArrayList<>();
        for(Fun fun : source){
            if( pid.equals(fun.getPid()) ){
                //找到了这一层的一个菜单
                //fun->menuVO
                MenuVO vo = new MenuVO() ;
                vo.setId(fun.getFid());
                vo.setTitle(fun.getFname());
                //vo中的children就是当前fun这个菜单的子菜单
                //也就是下一层菜单，怎么找下一层菜单，当前方法就可以
                //找到父级菜单id和当前菜单的id相等那些菜单，就是当前这个菜单的子菜单
                List<MenuVO> children = handleMenuPreLevel(fun.getFid(), source);
                vo.setChildren(children);
                menuVOS.add(vo);
            }
        }
        return menuVOS;
    }

    @Override
    public long childrenCount(long fid) {
        return dao.childrenCount(fid);
    }

    @Override
    public void delete(long fid, long update_uid) {
        Map<String ,Object> param = new HashMap<>() ;
        param.put("fid",fid);
        param.put("update_uid",update_uid);
        dao.delete(param);
    }
}
