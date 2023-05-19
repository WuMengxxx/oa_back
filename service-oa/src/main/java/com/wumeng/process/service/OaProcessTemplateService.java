package com.wumeng.process.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wumeng.model.process.ProcessTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批模板 服务类
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */

public interface OaProcessTemplateService extends IService<ProcessTemplate> {

    IPage<ProcessTemplate> selectPageProcessTemplate(Page<ProcessTemplate> pageParam);

    void publish(Long id);
}
