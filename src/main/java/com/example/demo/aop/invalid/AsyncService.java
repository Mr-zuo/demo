package com.example.demo.aop.invalid;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/1
 */
@Service
public class AsyncService {

    public void async1() {
        System.out.println("1:" + Thread.currentThread().getName());
        this.async2();
    }

    @Async
    public void async2() {
        System.out.println("2:" + Thread.currentThread().getName());
    }
}
