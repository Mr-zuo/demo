package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("condition")
public class ConditionController {


    @Value("${envirDetect}")
    private String envirDetect;

    @GetMapping("envir")
    public String envir(){
        return envirDetect;
    }

    @GetMapping("status")
    public boolean status(){
        return true;
    }




}
