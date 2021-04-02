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
public class Demo2ServiceImpl implements DemoService {
    @Override
    public String getServiceName() {
        return "Demo2ServiceImpl";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        DemoServiceFactory.register(2,this);
    }
}
