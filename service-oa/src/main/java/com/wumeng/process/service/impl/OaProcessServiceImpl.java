package com.wumeng.process.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.model.process.Process;
import com.wumeng.process.mapper.OaProcessMapper;
import com.wumeng.process.service.OaProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wumeng.vo.process.ProcessQueryVo;
import com.wumeng.vo.process.ProcessVo;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author wumeng
 * @since 2023-05-18
 */
@Service
public class OaProcessServiceImpl extends ServiceImpl<OaProcessMapper, Process> implements OaProcessService {

    @Override
    public IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo) {

        IPage<ProcessVo> pageModel = baseMapper.selectPage(pageParam, processQueryVo);

        return null;
    }
}
