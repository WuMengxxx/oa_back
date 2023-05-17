package com.wumeng.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wumeng.model.system.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author wumeng
 * @since 2023-04-22
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    List<SysMenu> findMenuListByUserId(@Param("userId") Long userId);

}
