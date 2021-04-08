package com.example.demo.apollo.auto;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
import com.ctrip.framework.apollo.spring.util.BeanRegistrationUtil;
import com.example.demo.apollo.auto.spring.annotation.ApolloJsonValueProcessor;
import com.example.demo.apollo.auto.spring.annotation.SpringValueProcessor;
import com.example.demo.apollo.auto.spring.config.ApolloPropertySourcesProcessor;
import com.example.demo.apollo.auto.spring.property.SpringValueDefinitionProcessor;
import com.google.common.collect.Lists;
//import com.zto.titans.config.annotation.EnableConfig;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class ApolloRegistrar implements ImportBeanDefinitionRegistrar {
    public ApolloRegistrar() {
    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
//        AnnotationAttributes attributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(EnableConfig.class.getName()));
//        String[] namespaces = attributes.getStringArray("value");
//        int order = attributes.getNumber("order").intValue();
//        ApolloPropertySourcesProcessor.addNamespaces(Lists.newArrayList(namespaces), order);
//        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloPropertySourcesProcessor.class.getName(), ApolloPropertySourcesProcessor.class);
//        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringValueProcessor.class.getName(), SpringValueProcessor.class);
//        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, SpringValueDefinitionProcessor.class.getName(), SpringValueDefinitionProcessor.class);
//        BeanRegistrationUtil.registerBeanDefinitionIfNotExists(registry, ApolloJsonValueProcessor.class.getName(), ApolloJsonValueProcessor.class);
    }
}
