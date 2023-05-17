package com.wumeng.auth.controller;


import com.wumeng.auth.service.SysMenuService;
import com.wumeng.common.result.Result;
import com.wumeng.model.system.SysMenu;
import com.wumeng.vo.system.AssginMenuVo;
import com.wumeng.vo.system.AssginRoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author wumeng
 * @since 2023-04-22
 */
@Api("菜单管理接口")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    //查询所有菜单和角色分配的菜单
    @ApiOperation("查询所有的菜单和角色分配的菜单")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId){
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);


    }


    @ApiOperation("角色分配菜单")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo){
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();

    }

    //菜单列表接口
    @ApiOperation("获取菜单")
    @GetMapping("findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }



    @ApiOperation("新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu permission){
        sysMenuService.save(permission);
        return Result.ok();
    }



    @ApiOperation("修改菜单")
    @PostMapping("udpate")
    public Result updateById(@RequestBody SysMenu permission){
        sysMenuService.updateById(permission);
        return Result.ok();
    }


    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    @ApiOperation("删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id){
        sysMenuService.removeMenuById(id);
        return Result.ok();
    }

}

