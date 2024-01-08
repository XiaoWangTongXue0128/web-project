package controller;


import com.alibaba.fastjson.JSON;
import dao.CarDao;
import domain.Car;
import domain.LayTableVO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars")
public class CarFindController extends HttpServlet {
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarDao dao =new CarDao();
        List<Car> cars = dao.findAll() ;

        //接下来就需要将汽车信息返回给浏览器（layui对象）
        //不需要转发，也不需要重定向
        //但集合数据不能直接返回的（响应，IO）
        //需要转换成json格式的字符串（fastjson）

        //集合转换成json响应给layui，就可以展示么
        //不能，需要确保返回的数据结构符合layui可以处理的结构
        LayTableVO vo = new LayTableVO("0", "", 1000, cars);
        String json = JSON.toJSONString(vo);

        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(json);
    }
}
