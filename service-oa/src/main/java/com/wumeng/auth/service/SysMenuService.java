package com.wumeng.auth.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wumeng.model.system.SysMenu;
import com.wumeng.vo.system.AssginMenuVo;
import com.wumeng.vo.system.RouterVo;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author wumeng
 * @since 2023-04-22
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    void removeMenuById(Long id);

    List<SysMenu> findMenuByRoleId(Long id);

    void doAssign(AssginMenuVo assginMenuVo);

    List<RouterVo> findUserMenuListByUserId(Long userId);

    List<String> findUserPermsByUserId(Long userId);
}
