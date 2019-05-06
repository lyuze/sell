package com.lyz.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductVo implements Serializable {

    private static final long serialVersionUID = -3671199148273301307L;

    /** 类目名字 */
    @JsonProperty("name")
    private String categoryName;

    /** 类目类型 */
    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
