package com.wumeng.auth.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wumeng.auth.mapper.SysMenuMapper;
import com.wumeng.auth.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wumeng.auth.service.SysRoleMenuService;
import com.wumeng.auth.service.SysRoleService;
import com.wumeng.auth.util.MenuHelper;
import com.wumeng.common.config.Exception.OaException;
import com.wumeng.model.system.SysMenu;
import com.wumeng.model.system.SysRole;
import com.wumeng.model.system.SysRoleMenu;
import com.wumeng.vo.system.AssginMenuVo;
import com.wumeng.vo.system.MetaVo;
import com.wumeng.vo.system.RouterVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author wumeng
 * @since 2023-04-22
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public List<SysMenu> findNodes() {

        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);

        return resultList;
    }


    //删除菜单
    @Override
    public void removeMenuById(Long id) {

        //判断当前菜单是否有子菜单
        LambdaQueryWrapper<SysMenu> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getParentId,id);
        Integer count = baseMapper.selectCount(wrapper);
        System.out.println(count);
        if(count>0){
            throw new OaException(201,"菜单不能删除");
        }
        baseMapper.deleteById(id);

    }

    @Override
    public List<SysMenu> findMenuByRoleId(Long id) {
        //查询所有菜单-添加条件 status=1
        LambdaQueryWrapper<SysMenu> wrapperSysMenu=new LambdaQueryWrapper<>();
        wrapperSysMenu.eq(SysMenu::getStatus,1);
        List<SysMenu> allSystemMenuList = baseMapper.selectList(wrapperSysMenu);

        //根据角色roleid查询，角色菜单关系表里面，角色id对应所有的菜单id
        LambdaQueryWrapper<SysRoleMenu> wrapperSysRoleMenu = new LambdaQueryWrapper<>();
        wrapperSysRoleMenu.eq(SysRoleMenu::getRoleId,id);
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuService.list(wrapperSysRoleMenu);



        //根据获取的菜单id，获取对应菜单对象
        List<Long> menuIdList = sysRoleMenuList.stream().map(c -> c.getMenuId()).collect(Collectors.toList());
        //拿着菜单id，和所有菜单集合里面id比较，如果相同的话就封装
        allSystemMenuList.stream().forEach(item->{
            if(menuIdList.contains(item.getId())){
                item.setSelect(true);
            }else{
                item.setSelect(false);
            }
        });
        //返回规定格式菜单列表
        List<SysMenu> sysMenuList = MenuHelper.buildTree(allSystemMenuList);


        return sysMenuList;
    }


    //角色分配菜单
    @Override
    public void doAssign(AssginMenuVo assginMenuVo) {
        //根据角色id 删除菜单角色表 分配数据
        LambdaQueryWrapper<SysRoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRoleMenu::getRoleId,assginMenuVo.getRoleId());
        sysRoleMenuService.remove(wrapper);

        //从参数里面获取角色新分配菜单id列表，进行遍历，把每个id数据添加到菜单角色关系表里去
        //遍历，把每个id数据添加菜单角色表
        List<Long> menuIdList = assginMenuVo.getMenuIdList();
        for(Long menuId:menuIdList){
            if(StringUtils.isEmpty(menuId+"")){
                continue;
            }
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(assginMenuVo.getRoleId());
            sysRoleMenuService.save(sysRoleMenu);
        }
    }


    //根据id获取用户可以操作的菜单列表
    @Override
    public List<RouterVo> findUserMenuListByUserId(Long userId) {
        List<SysMenu> sysMenuList;
        //判断当前用户是否是管理员 userId=1是管理员
        //如果当前用户是管理员，可以查询所有菜单列表
        if(userId.longValue()==1){
            //查询所有菜单列表
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getStatus,1);
            wrapper.orderByAsc(SysMenu::getSortValue);
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            //如果不是管理员，根据userId查询可以操作的菜单列表
            //多表关联查询：用户角色关系表、角色菜单关系表、菜单表
            sysMenuList = baseMapper.findMenuListByUserId(userId);


        }
        //把查询出来的数据列表，构建成框架要求的路由数据结构
        //使用菜单操作工具类，构建成树型结构
        List<SysMenu> sysMenuTreeList = MenuHelper.buildTree(sysMenuList);
        //构建成框架要求的路由结构
        List<RouterVo> routerList = this.buildRouter(sysMenuTreeList);

        return routerList;
    }

    private List<RouterVo> buildRouter(List<SysMenu> menus){
        //创建list集合，存储最终数据
        List<RouterVo> routers = new ArrayList<>();
        //menus遍历
        for(SysMenu menu:menus){
            RouterVo router = new RouterVo();
            router.setHidden(false);
            router.setAlwaysShow(false);
            router.setPath(getRouterPath(menu));
            router.setComponent(menu.getComponent());
            router.setMeta(new MetaVo(menu.getName(),menu.getIcon()));

            //封装下一层数据
            List<SysMenu> children = menu.getChildren();
            if(menu.getType().intValue()==1){
                //加载下面隐藏路由
                List<SysMenu> hiddenMenuList = children.stream()
                        .filter(item -> !StringUtils.isEmpty(item.getComponent()))
                        .collect(Collectors.toList());
                for(SysMenu hiddenMenu : hiddenMenuList){
                    RouterVo hiddenRouter = new RouterVo();

                    hiddenRouter.setHidden(true);
                    hiddenRouter.setAlwaysShow(false);
                    hiddenRouter.setPath(getRouterPath(hiddenMenu));
                    hiddenRouter.setComponent(hiddenMenu.getComponent());
                    hiddenRouter.setMeta(new MetaVo(hiddenMenu.getName(),hiddenMenu.getIcon()));

                    routers.add(hiddenRouter);

                }
            }else {
                if(!CollectionUtils.isEmpty(children)){
                    if(children.size()>0){
                        router.setAlwaysShow(true);
                    }
                    router.setChildren(buildRouter(children));

                }
            }
            routers.add(router);

        }
        return routers;

    }

    @Override
    public List<String> findUserPermsByUserId(Long userId) {
        //判断是否是管理员，如果是管理员，查询所有按钮列表
        List<SysMenu> sysMenuList = null;
        if(userId.longValue()==1){
            //查询所有菜单列表
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysMenu::getStatus,1);
            sysMenuList = baseMapper.selectList(wrapper);
//            sysMenuList = this.list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getStatus, 1));
        }else{
            //如果不是管理员，根据userId查询可以操作的菜单列表
            //多表关联查询：用户角色关系表、角色菜单关系表、菜单表
            sysMenuList = baseMapper.findMenuListByUserId(userId);
        }

        List<String> permsList = sysMenuList.stream()
                .filter(item -> item.getType() == 2)
                .map(item -> item.getPerms())
                .collect(Collectors.toList());
        return permsList;
    }

    public String getRouterPath(SysMenu menu){
        String routerPath = "/" + menu.getPath();
        if(menu.getParentId().intValue()!=0){
            routerPath = menu.getPath();
        }
        return routerPath;
    }

}
