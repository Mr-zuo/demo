package com.example.demo.aop.invalid;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/1
 */
@Service
public class AsyncService implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    public void async1() {
        System.out.println("1:" + Thread.currentThread().getName());
//        this.async2();
        // 使用ApplicationContext来获得动态代理的bean
        this.applicationContext.getBean(AsyncService.class).async2();
    }

    @Async
    public void async2() {
        System.out.println("2:" + Thread.currentThread().getName());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
