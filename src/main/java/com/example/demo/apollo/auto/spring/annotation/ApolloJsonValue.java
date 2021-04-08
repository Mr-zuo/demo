package com.example.demo.apollo.auto.spring.annotation;

import java.lang.annotation.*;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface ApolloJsonValue {
    String value();
}
