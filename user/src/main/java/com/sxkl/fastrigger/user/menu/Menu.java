package com.sxkl.fastrigger.user.menu;

import com.sxkl.fastrigger.user.role.Role;
import lombok.Data;

import java.util.List;

/**
 * 用户权限模块 菜单实体类
 * @author wy
 * @date 2019-06-10 13:41:52
 */
@Data
public class Menu {

    private String name;
    private String url;
    private boolean leaf;
    private String icon;
    private Menu parentId;
    private List<Menu> children;
    private List<Role> roles;
}
