package com.wumeng.common.result;

import lombok.Getter;

/**
 * @author wmstart
 * @create 2023-04-15 0:03
 */

@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201,"失败"),
    SERVICE_ERROR(2012,"服务异常"),
    DATA_ERROR(204,"数据异常"),

    LOGIN_AUTH(208,"未登录"),
    PERMISSION(209,"没有权限"),

    LOGIN_ERROR(204,"认证失败");

    private Integer code;
    private String message;

    private ResultCodeEnum(Integer code,String message){
        this.code=code;
        this.message=message;
    }
}
