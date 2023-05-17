package com.wumeng.process.mapper;

import com.wumeng.model.process.ProcessTemplate;
import com.wumeng.model.process.ProcessType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 审批模板 Mapper 接口
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */

@Repository
@Mapper
public interface OaProcessTemplateMapper extends BaseMapper<ProcessTemplate> {

}
