package com.wumeng.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wumeng.auth.mapper.SysUserMapper;
import com.wumeng.auth.mapper.SysUserRoleMapper;
import com.wumeng.auth.service.SysUserRoleService;
import com.wumeng.auth.service.SysUserService;
import com.wumeng.model.system.SysUser;
import com.wumeng.model.system.SysUserRole;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author wumeng
 * @since 2023-04-19
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
