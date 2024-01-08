package com.test;

import com.dao.CarDao;
import com.domain.Car;

public class TestSave {
    public static void main(String[] args) {
        new CarDao().save( new Car(null,"falali","blue",400000.0) );
    }
}
