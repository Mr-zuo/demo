package com.example.demo.service;

import com.example.demo.domain.Shop;
import com.tools.ronapi.annotation.DocApi;
import com.tools.ronapi.annotation.DocMethod;


/**
 * 策略模式测试接口
 * @author ron
 * @date 2021年05月13日 14:53
 */
@DocApi
public interface DemoService2{

    /**
     * 获取服务名称
     * @param id 服务id
     * @return 服务名称
     */
    @DocMethod
    public String getServiceName(int id);

    /**
     * 获取商店
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体
     */
    @DocMethod
    public Shop getShopsV2(String shopName, int shopNum);
    

}
