package com.duyi.his.test;

import cn.hutool.extra.pinyin.PinyinUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class Test1 {
    public static void main(String[] args) {
        System.out.println(args[1]);
        System.out.println(PinyinUtil.getPinyin("中国666",""));
        System.out.println(PinyinUtil.getFirstLetter("中国666",""));
    }
}
