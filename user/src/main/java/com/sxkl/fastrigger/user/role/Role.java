package com.sxkl.fastrigger.user.role;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.user.department.Department;
import com.sxkl.fastrigger.user.menu.Menu;
import com.sxkl.fastrigger.user.privilege.Privilege;
import com.sxkl.fastrigger.user.user.entity.User;
import lombok.Data;

import java.util.List;

/**
 * 用户权限模块 角色实体类
 * @author wy
 * @date 2019-06-10 13:41:52
 */
@Data
public class Role extends BaseEntity {

    private String name;
    private List<Department> departments;
    private List<User> users;
    private List<Menu> menus;
    private List<Privilege> privileges;
}
