package com.controller;


import com.domain.Car;
import org.duyi.mvc.ModelAndView;
import org.duyi.mvc.annotations.RequestMapping;
import org.duyi.mvc.annotations.RequestParam;
import org.duyi.mvc.annotations.ResponseBody;

public class TestController {

    @RequestMapping("/test1")
    @ResponseBody
    public String test1(@RequestParam("cname") String cname , @RequestParam("color") String color){
        System.out.println(cname + " , " + color );

        return "test success !!!" ;
    }

    @RequestMapping("/test2")
    @ResponseBody
    public Car test2(Car car){
        System.out.println(car);

        return car ;
    }

    @RequestMapping("/test3")
    public ModelAndView test3(){
        //转发访问03.jsp网页，并携带一组数据
        //需要使用ModelAndView
        ModelAndView mv = new ModelAndView();
        mv.setViewName("03.jsp");
        mv.setAttribute("cname","benz");

        return mv ;
    }

}
