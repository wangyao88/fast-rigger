package com.sxkl.fastrigger.user.user.restcontroller;

import com.sxkl.fastrigger.commoner.base.controller.BaseRestController;
import com.sxkl.fastrigger.user.user.entity.User;
import com.sxkl.fastrigger.user.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户权限模块 用户Restful api控制层
 * @author wy
 * @date 2019-06-10 16:08:18
 */
@RestController
@RequestMapping("/user")
public class UserRestController extends BaseRestController<User> {

    @Autowired
    private UserService userService;

    @Override
    protected UserService getService() {
        return userService;
    }
}
