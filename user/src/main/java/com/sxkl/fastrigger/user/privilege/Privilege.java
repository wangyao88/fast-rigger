package com.sxkl.fastrigger.user.privilege;

import com.sxkl.fastrigger.user.role.Role;
import lombok.Data;

import java.util.List;

/**
 * 用户权限模块 权限实体类
 * @author wy
 * @date 2019-06-10 13:41:52
 */
@Data
public class Privilege {

    private String name;
    private String url;
    private List<Role> roles;
}
