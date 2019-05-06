package com.lyz.sell.service.impl;

import com.lyz.sell.dao.SellerInfoRepository;
import com.lyz.sell.entity.SellerInfo;
import com.lyz.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
