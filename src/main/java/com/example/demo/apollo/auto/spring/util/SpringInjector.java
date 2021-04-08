package com.example.demo.apollo.auto.spring.util;


import com.ctrip.framework.apollo.exceptions.ApolloConfigException;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.example.demo.apollo.auto.spring.property.PlaceholderHelper;
import com.example.demo.apollo.auto.spring.property.SpringValueRegistry;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Singleton;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
public class SpringInjector {
    private static volatile Injector s_injector;
    private static final Object lock = new Object();

    public SpringInjector() {
    }

    private static Injector getInjector() {
        if (s_injector == null) {
            synchronized(lock) {
                if (s_injector == null) {
                    try {
                        s_injector = Guice.createInjector(new Module[]{new SpringInjector.SpringModule()});
                    } catch (Throwable var4) {
                        ApolloConfigException exception = new ApolloConfigException("Unable to initialize Apollo Spring Injector!", var4);
                        Tracer.logError(exception);
                        throw exception;
                    }
                }
            }
        }

        return s_injector;
    }

    public static <T> T getInstance(Class<T> clazz) {
        try {
            return getInjector().getInstance(clazz);
        } catch (Throwable var2) {
            Tracer.logError(var2);
            throw new ApolloConfigException(String.format("Unable to load instance for %s!", clazz.getName()), var2);
        }
    }

    private static class SpringModule extends AbstractModule {
        private SpringModule() {
        }

        protected void configure() {
            this.bind(PlaceholderHelper.class).in(Singleton.class);
            this.bind(SpringValueRegistry.class).in(Singleton.class);
        }
    }
}
