package com.sxkl.fastrigger.user.department;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.user.role.Role;
import lombok.Data;

import java.util.List;

/**
 * 用户权限模块 部门实体类
 * @author wy
 * @date 2019-06-10 13:41:52
 */
@Data
public class Department extends BaseEntity {

    private String name;
    private String parentId;
    private List<Department> children;
    private List<Role> roles;
}
