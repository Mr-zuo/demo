package com.example.demo.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/3/18
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface CustomizedConstruct {
}
