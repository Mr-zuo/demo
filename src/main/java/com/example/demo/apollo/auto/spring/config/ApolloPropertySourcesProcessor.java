package com.example.demo.apollo.auto.spring.config;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.build.ApolloInjector;
import com.example.demo.apollo.auto.spring.property.AutoUpdateConfigChangeListener;
import com.example.demo.apollo.auto.util.ConfigUtils;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

public class ApolloPropertySourcesProcessor implements BeanFactoryPostProcessor, EnvironmentAware, PriorityOrdered {
    private static final Multimap<Integer, String> NAMESPACE_NAMES = LinkedHashMultimap.create();
    private static final Set<BeanFactory> AUTO_UPDATE_INITIALIZED_BEAN_FACTORIES = Sets.newConcurrentHashSet();
    private final ConfigUtils configUtil = (ConfigUtils)ApolloInjector.getInstance(ConfigUtils.class);
    private ConfigurableEnvironment environment;

    public ApolloPropertySourcesProcessor() {
    }

    public static boolean addNamespaces(Collection<String> namespaces, int order) {
        return NAMESPACE_NAMES.putAll(order, namespaces);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.initializePropertySources();
        this.initializeAutoUpdatePropertiesFeature(beanFactory);
    }

    private void initializePropertySources() {
    }

    private void initializeAutoUpdatePropertiesFeature(ConfigurableListableBeanFactory beanFactory) {
        if (this.configUtil.isAutoUpdateInjectedSpringPropertiesEnabled() && AUTO_UPDATE_INITIALIZED_BEAN_FACTORIES.add(beanFactory)) {
            AutoUpdateConfigChangeListener autoUpdateConfigChangeListener = new AutoUpdateConfigChangeListener(this.environment, beanFactory);
            ImmutableSortedSet<Integer> orders = ImmutableSortedSet.copyOf(NAMESPACE_NAMES.keySet());
            UnmodifiableIterator iterator = orders.iterator();

            while(iterator.hasNext()) {
                int order = (Integer)iterator.next();
                Iterator var6 = NAMESPACE_NAMES.get(order).iterator();

                while(var6.hasNext()) {
                    String namespace = (String)var6.next();
                    Config config = ConfigService.getConfig(namespace);
                    config.addChangeListener(autoUpdateConfigChangeListener);
                }
            }

            NAMESPACE_NAMES.clear();
        }
    }

    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment)environment;
    }

    public int getOrder() {
        return -2147483648;
    }
}
