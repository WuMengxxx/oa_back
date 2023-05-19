package com.wumeng.process.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.common.result.Result;
import com.wumeng.model.process.ProcessType;
import com.wumeng.process.service.OaProcessTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 审批类型 前端控制器
 * </p>
 *
 * @author wumeng
 * @since 2023-05-17
 */
@Api
@RestController
@RequestMapping("/admin/process/processType")
public class OaProcessTypeController {

    @Autowired
    private OaProcessTypeService processTypeService;




    //查询所有审批类型
    @ApiOperation("查询所有审批类型")
    @GetMapping("findAll")
    public Result findAll(){
        List<ProcessType> list = processTypeService.list();
        return Result.ok(list);
    }

    @ApiOperation("获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit){

        //创建page对象
        Page<ProcessType> pageParam = new Page<>(page,limit);
        IPage<ProcessType> pageModel = processTypeService.page(pageParam);
        return Result.ok(pageModel);


    }


    @ApiOperation("获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id){
        ProcessType processType = processTypeService.getById(id);
        return Result.ok(processType);
    }

    @ApiOperation("增加")
    @PostMapping("save")
    public Result get(@RequestBody ProcessType processType){
        processTypeService.save(processType);
        return Result.ok();
    }


    @ApiOperation("修改")
    @PutMapping("update")
    public Result update(@RequestBody ProcessType processType){
        processTypeService.updateById(processType);
        return Result.ok();
    }


    @ApiOperation("删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        processTypeService.removeById(id);
        return Result.ok();
    }




}

