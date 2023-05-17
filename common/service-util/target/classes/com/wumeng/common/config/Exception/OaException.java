package com.wumeng.common.config.Exception;

import com.wumeng.common.result.ResultCodeEnum;
import lombok.Data;

/**
 * @author wmstart
 * @create 2023-04-16 14:41
 */
@Data
public class OaException extends RuntimeException {

    private Integer code;//状态码
    private String msg;//描述信息

    public OaException(Integer code,String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public OaException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "GuliException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
