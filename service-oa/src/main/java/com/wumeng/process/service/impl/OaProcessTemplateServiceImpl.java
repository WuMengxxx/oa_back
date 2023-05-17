package com.wumeng.process.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.model.process.ProcessTemplate;
import com.wumeng.model.process.ProcessType;
import com.wumeng.process.mapper.OaProcessTemplateMapper;
import com.wumeng.process.mapper.OaProcessTypeMapper;
import com.wumeng.process.service.OaProcessTemplateService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wumeng.process.service.OaProcessTypeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批模板 服务实现类
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */
@Service
public class OaProcessTemplateServiceImpl extends ServiceImpl<OaProcessTemplateMapper, ProcessTemplate> implements OaProcessTemplateService {
    @Override
    public IPage<ProcessTemplate> selectPageProcessTemplate(Page<ProcessTemplate> pageParam) {
        //1.调用mapper分页查询

        //2.分页查询之后返回分页数据，从分页数据获取列表list集合

        //3.遍历list集合

        //4.根据审批类型id，查询获取对应名称

        //5.完成最终封装processTypename
        return null;
    }
}
