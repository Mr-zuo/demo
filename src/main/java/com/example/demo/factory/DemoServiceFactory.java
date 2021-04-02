package com.example.demo.factory;

import com.example.demo.service.DemoService;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DemoServiceFactory {

    private static Map<Integer, DemoService> services=new ConcurrentHashMap<>();

    public static DemoService getByNum(int type){
        return services.get(type);
    };

    public static void register(int type,DemoService demoService){
        Assert.notNull(type,"type can't be null");
        services.put(type,demoService);
    }
}
