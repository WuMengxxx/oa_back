package com.wumeng.process.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.common.result.Result;
import com.wumeng.model.process.Process;
import com.wumeng.process.service.OaProcessService;
import com.wumeng.vo.process.ProcessQueryVo;
import com.wumeng.vo.process.ProcessVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 审批类型 前端控制器
 * </p>
 *
 * @author wumeng
 * @since 2023-05-18
 */
@RestController
@RequestMapping("/admin/process")
public class OaProcessController {

    @Autowired
    private OaProcessService processService;

//    @PreAuthorize("hasAuthority('bnt.process.list')")
    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit
            , ProcessQueryVo processQueryVo){
        Page<ProcessVo> pageParam = new Page<>(page,limit);
        IPage<ProcessVo> pageModel=processService.selectPage(pageParam,processQueryVo);


        return Result.ok(pageModel);
    }

}

