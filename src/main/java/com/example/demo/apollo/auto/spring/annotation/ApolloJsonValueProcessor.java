package com.example.demo.apollo.auto.spring.annotation;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
import com.ctrip.framework.apollo.build.ApolloInjector;
import com.example.demo.apollo.auto.spring.property.PlaceholderHelper;
import com.example.demo.apollo.auto.spring.property.SpringValue;
import com.example.demo.apollo.auto.spring.property.SpringValueRegistry;
import com.example.demo.apollo.auto.spring.util.SpringInjector;
import com.example.demo.apollo.auto.util.ConfigUtils;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;

public class ApolloJsonValueProcessor extends ApolloProcessor implements BeanFactoryAware {
    private static final Logger logger = LoggerFactory.getLogger(ApolloJsonValueProcessor.class);
    private static final Gson gson = new Gson();
    private final ConfigUtils configUtil = (ConfigUtils)ApolloInjector.getInstance(ConfigUtils.class);
    private final PlaceholderHelper placeholderHelper = (PlaceholderHelper) SpringInjector.getInstance(PlaceholderHelper.class);
    private final SpringValueRegistry springValueRegistry = (SpringValueRegistry)SpringInjector.getInstance(SpringValueRegistry.class);
    private ConfigurableBeanFactory beanFactory;

    public ApolloJsonValueProcessor() {
    }

    protected void processField(Object bean, String beanName, Field field) {
        ApolloJsonValue apolloJsonValue = (ApolloJsonValue)AnnotationUtils.getAnnotation(field, ApolloJsonValue.class);
        if (apolloJsonValue != null) {
            String placeholder = apolloJsonValue.value();
            Object propertyValue = this.placeholderHelper.resolvePropertyValue(this.beanFactory, beanName, placeholder);
            if (propertyValue instanceof String) {
                boolean accessible = field.isAccessible();
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, this.parseJsonValue((String)propertyValue, field.getGenericType()));
                field.setAccessible(accessible);
                if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled()) {
                    Set<String> keys = this.placeholderHelper.extractPlaceholderKeys(placeholder);
                    Iterator var9 = keys.iterator();

                    while(var9.hasNext()) {
                        String key = (String)var9.next();
                        SpringValue springValue = new SpringValue(key, placeholder, bean, beanName, field, true);
                        this.springValueRegistry.register(this.beanFactory, key, springValue);
                        logger.debug("Monitoring {}", springValue);
                    }
                }

            }
        }
    }

    protected void processMethod(Object bean, String beanName, Method method) {
        ApolloJsonValue apolloJsonValue = (ApolloJsonValue)AnnotationUtils.getAnnotation(method, ApolloJsonValue.class);
        if (apolloJsonValue != null) {
            String placeHolder = apolloJsonValue.value();
            Object propertyValue = this.placeholderHelper.resolvePropertyValue(this.beanFactory, beanName, placeHolder);
            if (propertyValue instanceof String) {
                Type[] types = method.getGenericParameterTypes();
                Preconditions.checkArgument(types.length == 1, "Ignore @Value setter {}.{}, expecting 1 parameter, actual {} parameters", new Object[]{bean.getClass().getName(), method.getName(), method.getParameterTypes().length});
                boolean accessible = method.isAccessible();
                method.setAccessible(true);
                ReflectionUtils.invokeMethod(method, bean, new Object[]{this.parseJsonValue((String)propertyValue, types[0])});
                method.setAccessible(accessible);
                if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled()) {
                    Set<String> keys = this.placeholderHelper.extractPlaceholderKeys(placeHolder);
                    Iterator var10 = keys.iterator();

                    while(var10.hasNext()) {
                        String key = (String)var10.next();
                        SpringValue springValue = new SpringValue(key, apolloJsonValue.value(), bean, beanName, method, true);
                        this.springValueRegistry.register(this.beanFactory, key, springValue);
                        logger.debug("Monitoring {}", springValue);
                    }
                }

            }
        }
    }

    private Object parseJsonValue(String json, Type targetType) {
        try {
            return gson.fromJson(json, targetType);
        } catch (Throwable var4) {
            logger.error("Parsing json '{}' to type {} failed!", new Object[]{json, targetType, var4});
            throw var4;
        }
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ConfigurableBeanFactory)beanFactory;
    }
}
