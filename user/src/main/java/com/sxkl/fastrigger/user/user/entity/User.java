package com.sxkl.fastrigger.user.user.entity;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.user.role.Role;
import lombok.Data;

import java.util.List;

/**
 * 用户权限模块 用户实体类
 * @author wy
 * @date 2019-06-10 13:41:52
 */
@Data
public class User extends BaseEntity {

    private String name;
    private String password;
    private String email;
    private List<Role> roles;
}