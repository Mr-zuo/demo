package com.example.demo.apollo.auto.spring.annotation;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.ReflectionUtils;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
public abstract class ApolloProcessor implements BeanPostProcessor, PriorityOrdered {
    public ApolloProcessor() {
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class clazz = bean.getClass();
        Iterator var4 = this.findAllField(clazz).iterator();

        while(var4.hasNext()) {
            Field field = (Field)var4.next();
            this.processField(bean, beanName, field);
        }

        var4 = this.findAllMethod(clazz).iterator();

        while(var4.hasNext()) {
            Method method = (Method)var4.next();
            this.processMethod(bean, beanName, method);
        }

        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    protected abstract void processField(Object var1, String var2, Field var3);

    protected abstract void processMethod(Object var1, String var2, Method var3);

    public int getOrder() {
        return 2147483647;
    }

    private List<Field> findAllField(Class clazz) {
        List<Field> res = new LinkedList();
        ReflectionUtils.doWithFields(clazz, (field) -> {
            res.add(field);
        });
        return res;
    }

    private List<Method> findAllMethod(Class clazz) {
        List<Method> res = new LinkedList();
        ReflectionUtils.doWithMethods(clazz, (method) -> {
            res.add(method);
        });
        return res;
    }
}

