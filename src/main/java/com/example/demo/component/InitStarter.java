package com.example.demo.component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/3/18
 */
@Slf4j
@Component
public class InitStarter implements InitializingBean{

    @Setter
    @Getter
    private String name;

    static {
        log.info("InitStarter static");
    }

    static void test(){
        log.info("InitStarter static test");
    }

    public InitStarter() {
        log.info("InitStarter Constructor");
    }

    @PostConstruct
    private void init(){
        log.info("InitStarter PostConstruct Annotation");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitStarter afterPropertiesSet");
    }

}
