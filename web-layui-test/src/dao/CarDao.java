package dao;


import domain.Car;

import java.util.Arrays;
import java.util.List;

public class CarDao {
    public List<Car> findAll(){
        return Arrays.asList(
                new Car(1,"bmw1","blue",300000.0),
                new Car(2,"bmw2","blue",310000.0),
                new Car(3,"bmw3","blue",320000.0),
                new Car(4,"bmw4","blue",330000.0),
                new Car(5,"bmw5","blue",340000.0),
                new Car(6,"bmw6","blue",350000.0),
                new Car(7,"bmw7","blue",360000.0),
                new Car(8,"bmw8","blue",370000.0),
                new Car(9,"bmw9","blue",380000.0)
        );
    }
}
