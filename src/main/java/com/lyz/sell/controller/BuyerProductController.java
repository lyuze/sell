package com.lyz.sell.controller;

import com.lyz.sell.entity.ProductCategory;
import com.lyz.sell.entity.ProductInfo;
import com.lyz.sell.service.CategoryService;
import com.lyz.sell.service.ProductInfoService;
import com.lyz.sell.util.ResultVOUtil;
import com.lyz.sell.vo.ProductInfoVo;
import com.lyz.sell.vo.ProductVo;
import com.lyz.sell.vo.ResultVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    // @Cacheable(cacheNames = "product", key = "123") redis缓存 打开即用
    public ResultVo list(){
        // 查询所有的上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        // 查询类目(一次性)  java8 lambda(过滤很有用)
        List<Integer> categoryTypeList = productInfoList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        // 数据拼装 (外面那层)
        List<ProductVo> productVoList = new ArrayList<>();
        for (ProductCategory productCategory : productCategoryList){
            ProductVo productVo = new ProductVo();
            productVo.setCategoryType(productCategory.getCategoryType());
            productVo.setCategoryName(productCategory.getCategoryName());

            // 数据拼装 (里面那层)
            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    // copy
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }

        return ResultVOUtil.success(productVoList);
    }
}
