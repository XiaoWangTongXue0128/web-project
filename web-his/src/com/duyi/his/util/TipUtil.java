package com.duyi.his.util;

public class TipUtil {

    public static String tip(String msg,String nextUrl){
        return "/commonTip.jsp?msg="+msg+"&url="+nextUrl ;
    }

}
