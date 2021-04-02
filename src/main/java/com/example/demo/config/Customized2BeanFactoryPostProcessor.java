package com.example.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/3/18
 */
@Slf4j
//@Configuration
public class Customized2BeanFactoryPostProcessor implements BeanFactoryPostProcessor, Ordered {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("InitStarter CustomizedBeanFactoryPostProcessor 2002");
        BeanDefinition initStarter = beanFactory.getBeanDefinition("initStarter");
        initStarter.getPropertyValues().add("name","2002");
    }

    @Override
    public int getOrder() {
        return 2002;
    }
}
