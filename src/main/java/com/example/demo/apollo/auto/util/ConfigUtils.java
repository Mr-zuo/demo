package com.example.demo.apollo.auto.util;


import com.ctrip.framework.foundation.Foundation;
import com.google.common.base.Strings;

/**
 * @author zxl
 * @Description: TODO
 * @date 2021/4/8
 */
public class ConfigUtils {

    private boolean autoUpdateInjectedSpringProperties = true;

    public ConfigUtils() {
    }

    public boolean isAutoUpdateInjectedSpringPropertiesEnabled() {
        return this.autoUpdateInjectedSpringProperties;
    }

    private void initAutoUpdateInjectedSpringProperties() {
        String enableAutoUpdate = System.getProperty("apollo.autoUpdateInjectedSpringProperties");
        if (Strings.isNullOrEmpty(enableAutoUpdate)) {
            enableAutoUpdate = Foundation.app().getProperty("apollo.autoUpdateInjectedSpringProperties", (String)null);
        }

        if (!Strings.isNullOrEmpty(enableAutoUpdate)) {
            this.autoUpdateInjectedSpringProperties = Boolean.parseBoolean(enableAutoUpdate.trim());
        }

    }
}
