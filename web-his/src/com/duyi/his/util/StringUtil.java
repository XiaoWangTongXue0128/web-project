package com.duyi.his.util;

public class StringUtil {

    /**
     *
     * @param s
     * @return  true表示空，false 表示非空
     */
    public static boolean isEmpty(String s){
        return s == null || "".equals(s) ;
    }

    public static boolean isEmpty(Integer s){
        return s == null ;
    }

    public static boolean isNotEmpty(String s){
        return !isEmpty(s);
    }

}
