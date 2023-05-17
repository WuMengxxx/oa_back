package com.wumeng.auth.util;


import com.wumeng.model.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wmstart
 * @create 2023-04-22 14:54
 */
public class MenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        List<SysMenu> trees = new ArrayList<>();

        for(SysMenu sysMenu:sysMenuList){
            if(sysMenu.getParentId().longValue()==0){
                trees.add(getChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }


    public static SysMenu getChildren(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<SysMenu>());
        for(SysMenu it:sysMenuList){
            if(sysMenu.getId().longValue()==it.getParentId().longValue()){
                if(sysMenu.getChildren()==null){
                    sysMenu.setChildren(new ArrayList<>());
                }

                sysMenu.getChildren().add(getChildren(it,sysMenuList));
            }
        }
        return sysMenu;

    }


}
