package com.wumeng.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wumeng.model.system.SysUser;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author wumeng
 * @since 2023-04-19
 */
public interface SysUserService extends IService<SysUser> {

    void updateStatus(Long id,Integer status);

    Map<String, Object> getUserInfo(String username);

    SysUser getUserByUserName(String username);

}
