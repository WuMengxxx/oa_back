package com.wumeng.auth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wumeng.auth.mapper.SysRoleMapper;
import com.wumeng.auth.service.SysRoleService;
import com.wumeng.auth.service.SysUserRoleService;
import com.wumeng.auth.service.SysUserService;
import com.wumeng.model.system.SysRole;
import com.wumeng.model.system.SysUser;
import com.wumeng.model.system.SysUserRole;
import com.wumeng.vo.system.AssginRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wmstart
 * @create 2023-04-14 23:41
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {


    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public Map<String, Object> findRoleDataByUserId(Long userId) {

        //查询所有角色，返回list集合
        List<SysRole> allRoleList = baseMapper.selectList(null);
        //根据userid查询角色用户关系表，查询userid对应所有角色
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,userId);
        List<SysUserRole> existUserRoleList = sysUserRoleService.list(wrapper);

        //根据查询所有角色id，找到对应角色信息
//        List<Long> list=new ArrayList<>();
//        for(SysUserRole sysUserRole:existUserRoleList){
//            Long roleId=sysUserRole.getRoleId();
//            list.add(roleId);
//        }
//        这段代码将一个名为existUserRoleList的对象列表中的每个对象的roleId属性提取出来，然后将它们组成一个Long类型的列表collect。
//        其中，existUserRoleList是一个泛型为某个类型的List对象，而getRoleId()是这个类型中的一个返回roleId属性值的方法。
//        使用Java  8中的stream和map方法可以对列表中的每个对象进行操作并返回一个新的列表。
//        最后使用collect(Collectors.toList())方法将操作后的结果转换为List类型。
        List<Long> existRoleIdList = existUserRoleList.stream().map(c->c.getRoleId()).collect(Collectors.toList());
        //根据角色id到所有的角色的list集合进行比较
        List<SysRole> assignRoleList = new ArrayList<>();
        for(SysRole sysRole : allRoleList){
            if(existRoleIdList.contains(sysRole.getId())){
                assignRoleList.add(sysRole);
            }
        }
        //把得到的两部分数据封装map集合，返回

        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("assginRoleList",assignRoleList);
        roleMap.put("allRolesList",allRoleList);
        return roleMap;
    }

    @Override
    public void doAssign(AssginRoleVo assginRoleVo) {
        //把用户之前分配角色数据进行删除，用户角色关系表根据userid删除
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId,assginRoleVo.getUserId());
        sysUserRoleService.remove(wrapper);


        //重新进行分配
        List<Long> roleIdList = assginRoleVo.getRoleIdList();
        for(Long roleId:roleIdList){
            if(StringUtils.isEmpty(roleId+"")){
                continue;
            }
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setUserId(assginRoleVo.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.save(sysUserRole);


        }

    }
}
