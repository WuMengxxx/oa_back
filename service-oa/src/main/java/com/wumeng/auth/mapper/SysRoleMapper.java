package com.wumeng.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wumeng.model.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author wmstart
 * @create 2023-04-14 19:33
 */

@Repository
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {


}
