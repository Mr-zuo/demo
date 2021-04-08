package com.example.demo.apollo.auto.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
//@Import({EnableConfigChangeListenerSelector.class, ConfigRegistrar.class})
public @interface EnableConfig {
    String[] value() default {"application"};

    int order() default 2147483647;
}
