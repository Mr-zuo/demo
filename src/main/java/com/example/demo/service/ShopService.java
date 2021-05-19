package com.example.demo.service;

import com.example.demo.domain.Shop;
import com.tools.ronapi.annotation.DocApi;
import com.tools.ronapi.annotation.DocMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 商店服务接口
 * @author ron
 * @date 2021年05月13日 14:53
 */
@DocApi
public interface ShopService {

    /**
     * 获取商店
     * @param shopName 商店名称(模糊查询)
     * @param shopNum  商店数量
     * @return 商品实体
     */
    @DocMethod
    public Shop getShopsV2(String shopName, int shopNum);


    //参数返回值无法通过智能查询找到时，可以通过注释指定 #tyep:...#
    /**
     * 创建商店
     * @param shop 商店类
     * @return #type:com.example.demo.domain.Shop#
     */
    @PostMapping("createShop")
    public Shop createShop(Shop shop);


    //PathVariable 的方式
    /**
     * 删除商店
     * @param id 商店id
     * @return 是否成功
     */
    public Shop deleteShop(@PathVariable(name="id") long id);
}
