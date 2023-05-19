package com.wumeng.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wumeng.model.process.Process;
import com.wumeng.vo.process.ProcessQueryVo;
import com.wumeng.vo.process.ProcessVo;

/**
 * <p>
 * 审批类型 服务类
 * </p>
 *
 * @author wumeng
 * @since 2023-05-18
 */
public interface OaProcessService extends IService<Process>{

    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);
}
