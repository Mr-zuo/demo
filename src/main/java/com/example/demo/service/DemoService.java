package com.example.demo.service;

import org.springframework.beans.factory.InitializingBean;

/**
 * 策略模式测试接口
 * @author ron
 * @date 2021年05月13日 14:53
 */
public interface DemoService extends InitializingBean {

    /**
     * 获取服务名称
     * @return 服务名称
     */
    String getServiceName();

}
