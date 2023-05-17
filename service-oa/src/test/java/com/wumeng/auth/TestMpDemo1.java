package com.wumeng.auth;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wumeng.auth.mapper.SysRoleMapper;
import com.wumeng.model.system.SysRole;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author wmstart
 * @create 2023-04-14 19:35
 */
@SpringBootTest
public class TestMpDemo1 {


    @Autowired
    private SysRoleMapper mapper;

    @Test
    public void getAll(){
        List<SysRole> list = mapper.selectList(null);
        System.out.println(list);


    }

    @Test
    public void add(){
        SysRole sysRole =new SysRole();
        sysRole.setRoleName("吴");
        sysRole.setRoleCode("123");
        sysRole.setDescription("143");

        int insert = mapper.insert(sysRole);
        System.out.println(insert);
    }

    @Test
    public void update(){
        SysRole role = mapper.selectById(8);
        role.setRoleName("蒙蒙");
        int rows=mapper.updateById(role);
        System.out.println(rows);
    }


    //条件查询
    @Test
    public void testQuery1(){
        //创建QueryWrapper对象，调用方法封装条件
        QueryWrapper<SysRole> wrapper=new QueryWrapper<>();
        wrapper.eq("role_name","吴");

        List<SysRole> list=mapper.selectList(wrapper);
        System.out.println(list);

    }

    @Test
    public void testQuery2(){
        //Lambda
        LambdaQueryWrapper<SysRole> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName,"吴");

        List<SysRole> list=mapper.selectList(wrapper);
        System.out.println(list);

    }

}
