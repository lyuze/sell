package com.lyz.sell.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = 6479889273643194250L;

    /** 错误码 */
    private Integer code;

    /** 错误信息 */
    private String msg;

    /** 内容 */
    private T data;
}
