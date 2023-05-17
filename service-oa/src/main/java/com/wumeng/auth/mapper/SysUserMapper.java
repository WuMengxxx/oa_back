package com.wumeng.auth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wumeng.model.system.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author wumeng
 * @since 2023-04-19
 */
@Repository
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
