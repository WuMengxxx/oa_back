package com.wumeng.process.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.model.process.Process;
import com.wumeng.vo.process.ProcessQueryVo;
import com.wumeng.vo.process.ProcessVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author wumeng
 * @since 2023-05-18
 */
@Repository
@Mapper
public interface OaProcessMapper extends BaseMapper<Process> {
    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, @Param("vo")ProcessQueryVo processQueryVo);

}
