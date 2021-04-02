package com.example.demo.service.impl;

import com.example.demo.factory.DemoServiceFactory;
import com.example.demo.service.DemoService;
import org.springframework.stereotype.Service;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/3/18
 */
@Service
public class Demo1ServiceImpl implements DemoService {
    @Override
    public String getServiceName() {
        return "Demo1ServiceImpl";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DemoServiceFactory.register(1,this);
    }
}
