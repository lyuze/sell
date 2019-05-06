package com.lyz.sell.service.impl;

import com.lyz.sell.exception.SellException;
import com.lyz.sell.service.RedisLock;
import com.lyz.sell.service.SeckillService;
import com.lyz.sell.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SeckillServiceImpl implements SeckillService {

    private static final int TIMEOUT = 10 * 1000;

    @Autowired
    private RedisLock redisLock;

    static Map<String, Integer> products;
    static Map<String, Integer> stock;
    static Map<String, String> orders;
    static
    {
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId){
        return "国庆活动,皮蛋粥特价,限量份"
                + products.get(productId)
                + "还剩" + stock.get(productId) + "份" +
                "该商品成功下单用户数量:"
                + orders.size() + "人";
    }

    @Override
    public String querySeckillProductInfo(String productId) {
        return this.queryMap(productId);
    }

    @Override
    public void orderProductMockDiffUser(String productId) {
        // 加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        if (!redisLock.lock(productId, String.valueOf(time))){
            throw new SellException(101, "哎呦喂,人也太多了, 换个姿势再试试");
        }
        // 查询该商品库存
        int stockNum = stock.get(productId);
        if (stockNum == 0){
            throw new SellException(100, "活动结束");
        }else {
            // 下单
            orders.put(KeyUtil.genUniqueKey(), productId);
            // 减库存
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        // 解锁
        redisLock.unLock(productId, String.valueOf(time));
    }
}
