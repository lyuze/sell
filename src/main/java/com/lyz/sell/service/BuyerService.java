package com.lyz.sell.service;

import com.lyz.sell.dto.OrderDTO;

/**
 * 买家
 */
public interface BuyerService {

    // 查询订单
    OrderDTO findOrderOne(String openid, String orderId);

    // 取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
