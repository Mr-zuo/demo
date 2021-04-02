package com.example.demo.configrable;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/1
 */
@Configurable(preConstruction = true)
@Component
public class Car {

    public void startCar() {
        System.out.println("Car started");
    }
}
