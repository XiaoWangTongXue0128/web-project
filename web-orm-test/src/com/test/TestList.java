package com.test;

import com.dao.CarDao;

public class TestList {
    public static void main(String[] args) {
        new CarDao().findAll();
    }
}
