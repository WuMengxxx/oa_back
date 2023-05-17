package com.wumeng.auth.service;

import com.wumeng.model.system.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wumeng.vo.system.AssginRoleVo;

import java.util.Map;

/**
 * @author wmstart
 * @create 2023-04-14 23:38
 */
public interface SysRoleService extends IService<SysRole>{


    //查询所有角色和当前用户所属角色

    Map<String,Object> findRoleDataByUserId(Long userId);

    void doAssign(AssginRoleVo assginRoleVo);

}
