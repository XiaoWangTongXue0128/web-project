package com.dao;

import com.domain.Car;
import duyi.jdbc.annotation.Delete;
import duyi.jdbc.annotation.Select;

import java.util.List;
import java.util.Map;

public interface ICarDao {

    /**如果参数只有一个，可以直接传递，不需要组成对象*/
    @Delete("delete from t_car where cno = #{cno}")
    public void delete(int cno);

    /**如果需要传递多个参数，必须组成Map对象,包含start和length*/
    @Select("select * from t_car limit #{start},#{length}")
    public List<Car> findByPage(Map<String,Integer> params);
}
