package com.lyz.sell.controller;

import com.lyz.sell.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/skill")
@Slf4j
public class SeckillController {

    @Autowired
    private SeckillService seckillService;

    @GetMapping("/query/{productId}")
    public String query(@PathVariable String productId) throws Exception{
        return seckillService.querySeckillProductInfo(productId);
    }

    public String skill(@PathVariable String productId) throws Exception{
        log.info("@skill request, productId:" + productId);
        seckillService.orderProductMockDiffUser(productId);
        return seckillService.querySeckillProductInfo(productId);
    }
}
