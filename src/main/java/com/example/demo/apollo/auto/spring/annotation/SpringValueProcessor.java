package com.example.demo.apollo.auto.spring.annotation;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
import com.ctrip.framework.apollo.build.ApolloInjector;
import com.example.demo.apollo.auto.spring.property.*;
import com.example.demo.apollo.auto.spring.util.SpringInjector;
import com.example.demo.apollo.auto.util.ConfigUtils;
import com.google.common.collect.LinkedListMultimap;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Bean;

public class SpringValueProcessor extends ApolloProcessor implements BeanFactoryPostProcessor, BeanFactoryAware {
    private static final Logger logger = LoggerFactory.getLogger(SpringValueProcessor.class);
    private final ConfigUtils configUtil = (ConfigUtils)ApolloInjector.getInstance(ConfigUtils.class);
    private final PlaceholderHelper placeholderHelper = (PlaceholderHelper) SpringInjector.getInstance(PlaceholderHelper.class);
    private final SpringValueRegistry springValueRegistry = (SpringValueRegistry)SpringInjector.getInstance(SpringValueRegistry.class);
    private BeanFactory beanFactory;
    private Multimap<String, SpringValueDefinition> beanName2SpringValueDefinitions = LinkedListMultimap.create();

    public SpringValueProcessor() {
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled() && beanFactory instanceof BeanDefinitionRegistry) {
            this.beanName2SpringValueDefinitions = SpringValueDefinitionProcessor.getBeanName2SpringValueDefinitions((BeanDefinitionRegistry)beanFactory);
        }

    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled()) {
            super.postProcessBeforeInitialization(bean, beanName);
            this.processBeanPropertyValues(bean, beanName);
        }

        return bean;
    }

    protected void processField(Object bean, String beanName, Field field) {
        Value value = (Value)field.getAnnotation(Value.class);
        if (value != null) {
            Set<String> keys = this.placeholderHelper.extractPlaceholderKeys(value.value());
            if (!keys.isEmpty()) {
                Iterator var6 = keys.iterator();

                while(var6.hasNext()) {
                    String key = (String)var6.next();
                    SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, field, false);
                    this.springValueRegistry.register(this.beanFactory, key, springValue);
                    logger.debug("Monitoring {}", springValue);
                }

            }
        }
    }

    protected void processMethod(Object bean, String beanName, Method method) {
        Value value = (Value)method.getAnnotation(Value.class);
        if (value != null) {
            if (method.getAnnotation(Bean.class) == null) {
                if (method.getParameterTypes().length != 1) {
                    logger.error("Ignore @Value setter {}.{}, expecting 1 parameter, actual {} parameters", new Object[]{bean.getClass().getName(), method.getName(), method.getParameterTypes().length});
                } else {
                    Set<String> keys = this.placeholderHelper.extractPlaceholderKeys(value.value());
                    if (!keys.isEmpty()) {
                        Iterator var6 = keys.iterator();

                        while(var6.hasNext()) {
                            String key = (String)var6.next();
                            SpringValue springValue = new SpringValue(key, value.value(), bean, beanName, method, false);
                            this.springValueRegistry.register(this.beanFactory, key, springValue);
                            logger.info("Monitoring {}", springValue);
                        }

                    }
                }
            }
        }
    }

    private void processBeanPropertyValues(Object bean, String beanName) {
        Collection<SpringValueDefinition> propertySpringValues = this.beanName2SpringValueDefinitions.get(beanName);
        if (propertySpringValues != null && !propertySpringValues.isEmpty()) {
            Iterator var4 = propertySpringValues.iterator();

            while(var4.hasNext()) {
                SpringValueDefinition definition = (SpringValueDefinition)var4.next();

                try {
                    PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(bean.getClass(), definition.getPropertyName());
                    Method method = pd.getWriteMethod();
                    if (method != null) {
                        SpringValue springValue = new SpringValue(definition.getKey(), definition.getPlaceholder(), bean, beanName, method, false);
                        this.springValueRegistry.register(this.beanFactory, definition.getKey(), springValue);
                        logger.debug("Monitoring {}", springValue);
                    }
                } catch (Throwable var9) {
                    logger.error("Failed to enable auto update feature for {}.{}", bean.getClass(), definition.getPropertyName());
                }
            }

            this.beanName2SpringValueDefinitions.removeAll(beanName);
        }
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
