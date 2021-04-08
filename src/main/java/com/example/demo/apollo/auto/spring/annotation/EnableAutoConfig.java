package com.example.demo.apollo.auto.spring.annotation;

import com.example.demo.apollo.auto.ApolloRegistrar;
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
@Import({ApolloRegistrar.class})
public @interface EnableAutoConfig {
}
