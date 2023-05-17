package com.wumeng.auth.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wumeng.auth.mapper.SysRoleMapper;
import com.wumeng.auth.service.SysRoleService;
import com.wumeng.common.result.Result;
import com.wumeng.model.system.SysRole;
import com.wumeng.vo.system.SysRoleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.wumeng.vo.system.AssginRoleVo;


import java.util.List;
import java.util.Map;

/**
 * @author wmstart
 * @create 2023-04-14 23:48
 */
@Api("角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMapper sysRoleMapper;



    @ApiOperation("查询所有角色以及所属角色")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable Long userId){
        Map<String,Object> map=sysRoleService.findRoleDataByUserId(userId);
        return Result.ok(map);
    }

    @ApiOperation("为用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleVo assignRoleVo){
        sysRoleService.doAssign(assignRoleVo);
        return Result.ok();


    }










    //查询所有角色
    @GetMapping("findAll")
    @ApiOperation("查询所有角色")
    public Result<List<SysRole>> findAll(){
        //调用service方法
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }



    //条件分页查询
    //page 当前页 limit每页显示记录数
    //SysRoleQueryVo条件对象


//    有问题！！！！！！！！！！！！！！！！！！！！
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQueryRole(@PathVariable Long page,
                                @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo){

        //调用service
        //1.创建page对象，传递分页相关参数
        Page<SysRole> pageParam = new Page<>(page,limit);


        //封装条件，判断条件是否为空，不为空进行封装
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if(!StringUtils.isEmpty(roleName)){
            wrapper.like(SysRole::getRoleName,roleName);
        }


        IPage<SysRole> pageModel = sysRoleMapper.selectPage(pageParam,wrapper);

        List<SysRole> records=pageModel.getRecords();
        for (SysRole record : records) {
            System.out.println(record);
        }


        return Result.ok(pageModel);
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.add')")
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result save(@RequestBody SysRole role){
        //调用service的方法
        boolean is_success = sysRoleService.save(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.update')")
    @ApiOperation("修改角色")
    @PutMapping("update")
    public Result update(@RequestBody SysRole role){
        //调用service的方法
        boolean is_success = sysRoleService.updateById(role);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("根据id删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        boolean is_success=sysRoleService.removeById(id);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }

    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    @ApiOperation("根据id获取角色")
    @GetMapping("get/{id}")
    public Result getById(@PathVariable Long id){
        SysRole sysRole=sysRoleService.getById(id);
        return Result.ok(sysRole);
    }


    @PreAuthorize("hasAuthority('bnt.sysRole.remove')")
    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList){
        boolean is_success = sysRoleService.removeByIds(idList);
        if(is_success){
            return Result.ok();
        }else {
            return Result.fail();
        }
    }






}
