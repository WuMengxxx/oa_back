package com.wumeng.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wumeng.model.process.ProcessType;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */

@Repository
@Mapper
public interface OaProcessTypeMapper extends BaseMapper<ProcessType> {

}
