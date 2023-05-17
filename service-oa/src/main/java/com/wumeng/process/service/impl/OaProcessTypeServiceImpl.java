package com.wumeng.process.service.impl;

import com.wumeng.model.process.ProcessType;
import com.wumeng.process.mapper.OaProcessTypeMapper;
import com.wumeng.process.service.OaProcessTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 审批类型 服务实现类
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */
@Service
public class OaProcessTypeServiceImpl extends ServiceImpl<OaProcessTypeMapper, ProcessType> implements OaProcessTypeService {

}
