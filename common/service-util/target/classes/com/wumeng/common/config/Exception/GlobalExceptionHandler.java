package com.wumeng.common.config.Exception;

import com.wumeng.common.result.Result;
import com.wumeng.common.result.ResultCodeEnum;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.AccessDeniedException;

/**
 * @author wmstart
 * @create 2023-04-15 20:37
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    //全局异常处理，执行的方法
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail().message("执行全局异常处理...");
    }

    //特定异常处理
    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e) {
        e.printStackTrace();
        return Result.fail().message("请输入内容");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e) {
        e.printStackTrace();
        return Result.fail().message("执行特定异常处理...");
    }

    //自定义异常处理
    @ExceptionHandler(OaException.class)
    @ResponseBody
    public Result error(OaException e) {
        e.printStackTrace();
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }

    //spring-security异常
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e)throws AccessDeniedException{
        return Result.build(null, ResultCodeEnum.PERMISSION);
    }

//    /**
//     * spring security异常
//     * @param e
//     * @return
//     */
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseBody
//    public Result error(AccessDeniedException e) throws AccessDeniedException {
//        return Result.fail().code(205).message("没有操作权限");
//    }
}
