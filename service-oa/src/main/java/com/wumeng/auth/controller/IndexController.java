package com.wumeng.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wumeng.auth.service.SysMenuService;
import com.wumeng.auth.service.SysUserService;
import com.wumeng.common.config.Exception.OaException;
import com.wumeng.common.jwt.JwtHelper;
import com.wumeng.common.result.Result;
import com.wumeng.common.utils.MD5;
import com.wumeng.model.system.SysUser;
import com.wumeng.vo.system.LoginVo;
import com.wumeng.vo.system.RouterVo;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wmstart
 * @create 2023-04-17 15:11
 */

@Api("后台登录管理")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysMenuService sysMenuService;

    //(1)根据用户名查数据库，看数据是否存在（信息是否正确）
    //(2)如果用户存在（信息正确），判断用户是否被禁用
    //(3)登录成功之后保持登录状态
    //基于token实现
    //使用登录信息(用户id，用户名称等)生成唯一的字符串，对生成字符串进行编码加密处理
    //把唯一的字符串放到cookie里面，从cookie获取用户信息
    //但是cookie有问题:不能跨域传递

    //解决cookie跨域，每次发送请求的时候，把cookie值获取处理放到请求头，每次从请求头获取用户信息
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
//        Map<String,Object> map=new HashMap<>();
//        map.put("token","admin-token");
//        return Result.ok(map);

        //获取输入的用户名和密码
        String username = loginVo.getUsername();
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser user = sysUserService.getOne(wrapper);

        //用户信息是否存在
        if(user==null){
            throw new OaException(201,"用户不存在");
        }
        //判断密码
        //数据库存密码(MD5)
        String password_db = user.getPassword();
        //获取输入的密码
        String password_input = MD5.encrypt(loginVo.getPassword());
        if(!password_db.equals(password_input)){
            throw new OaException(201,"密码错误");
        }

        //判断用户是否被禁用
        if(user.getStatus().intValue()==0){
            throw new OaException(201,"已经被禁用");
        }
        //使用jwt
        String token = JwtHelper.createToken(user.getId(), user.getUsername());

        //返回
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return Result.ok(map);

    }


    @GetMapping("info")
    public Result info(HttpServletRequest request){
        String username = JwtHelper.getUsername(request.getHeader("token"));
        Map<String, Object> map = sysUserService.getUserInfo(username);
        return Result.ok(map);
    }


    @PostMapping("logout")
    public Result logout(){
        return Result.ok();
    }
}
