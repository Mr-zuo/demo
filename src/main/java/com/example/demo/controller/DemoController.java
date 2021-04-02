package com.example.demo.controller;

import com.example.demo.factory.DemoServiceFactory;
import com.example.demo.service.DemoService;
import com.example.demo.service.impl.Demo1ServiceImpl;
import com.example.demo.service.impl.Demo2ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("demo")
public class DemoController {


//    @Autowired
//    @Qualifier("demo1ServiceImpl")
//    @Resource(name = "demo2ServiceImpl")
//    private DemoService demoService;

//    @Autowired
//    @Qualifier("demo2ServiceImpl")
//    private DemoService demoService2;

    @PostMapping("getServiceName")
    public String getServiceName(@RequestParam("num") int num){
        DemoService demoService = DemoServiceFactory.getByNum(num);
//        DemoService demoService =null;
//        if (num==1){
//            demoService = new Demo1ServiceImpl();
//        }else {
//            demoService = new Demo2ServiceImpl();
//        }
        return demoService.getServiceName();
    }






}
