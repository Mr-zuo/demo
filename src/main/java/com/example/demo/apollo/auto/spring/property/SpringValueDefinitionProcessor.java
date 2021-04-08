package com.example.demo.apollo.auto.spring.property;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
import com.ctrip.framework.apollo.build.ApolloInjector;
import com.example.demo.apollo.auto.spring.util.SpringInjector;
import com.example.demo.apollo.auto.util.ConfigUtils;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

public class SpringValueDefinitionProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Map<BeanDefinitionRegistry, Multimap<String, SpringValueDefinition>> beanName2SpringValueDefinitions = Maps.newConcurrentMap();
    private static final Set<BeanDefinitionRegistry> PROPERTY_VALUES_PROCESSED_BEAN_FACTORIES = Sets.newConcurrentHashSet();
    private final ConfigUtils configUtil = (ConfigUtils)ApolloInjector.getInstance(ConfigUtils.class);
    private final PlaceholderHelper placeholderHelper = (PlaceholderHelper) SpringInjector.getInstance(PlaceholderHelper.class);

    public SpringValueDefinitionProcessor() {
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled()) {
            this.processPropertyValues(registry);
        }

    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    public static Multimap<String, SpringValueDefinition> getBeanName2SpringValueDefinitions(BeanDefinitionRegistry registry) {
        Multimap<String, SpringValueDefinition> springValueDefinitions = (Multimap)beanName2SpringValueDefinitions.get(registry);
        if (springValueDefinitions == null) {
            springValueDefinitions = LinkedListMultimap.create();
        }

        return (Multimap)springValueDefinitions;
    }

    private void processPropertyValues(BeanDefinitionRegistry beanRegistry) {
        if (PROPERTY_VALUES_PROCESSED_BEAN_FACTORIES.add(beanRegistry)) {
            if (!beanName2SpringValueDefinitions.containsKey(beanRegistry)) {
                beanName2SpringValueDefinitions.put(beanRegistry, LinkedListMultimap.create());
            }

            Multimap<String, SpringValueDefinition> springValueDefinitions = (Multimap)beanName2SpringValueDefinitions.get(beanRegistry);
            String[] beanNames = beanRegistry.getBeanDefinitionNames();
            String[] var4 = beanNames;
            int var5 = beanNames.length;

            label47:
            for(int var6 = 0; var6 < var5; ++var6) {
                String beanName = var4[var6];
                BeanDefinition beanDefinition = beanRegistry.getBeanDefinition(beanName);
                MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
                List<PropertyValue> propertyValues = mutablePropertyValues.getPropertyValueList();
                Iterator var11 = propertyValues.iterator();

                while(true) {
                    PropertyValue propertyValue;
                    String placeholder;
                    Set keys;
                    do {
                        Object value;
                        do {
                            if (!var11.hasNext()) {
                                continue label47;
                            }

                            propertyValue = (PropertyValue)var11.next();
                            value = propertyValue.getValue();
                        } while(!(value instanceof TypedStringValue));

                        placeholder = ((TypedStringValue)value).getValue();
                        keys = this.placeholderHelper.extractPlaceholderKeys(placeholder);
                    } while(keys.isEmpty());

                    Iterator var16 = keys.iterator();

                    while(var16.hasNext()) {
                        String key = (String)var16.next();
                        springValueDefinitions.put(beanName, new SpringValueDefinition(key, placeholder, propertyValue.getName()));
                    }
                }
            }

        }
    }
}
