package com.sxkl.fastrigger.commoner.base.controller;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.base.service.BaseService;
import com.sxkl.fastrigger.commoner.utils.RequestUtils;
import com.sxkl.fastrigger.commoner.utils.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统公用模块基础控制层
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public abstract class BaseController<T extends BaseEntity> {

    protected abstract <Service extends BaseService<T>> Service getService();

    private String getEntityName() {
        int index = getService().getClass().getSimpleName().indexOf("Service");
        return getService().getClass().getSimpleName().substring(0, index).toLowerCase();
    }

    protected String getViewName(String name) {
        return StringUtils.appendJoinEmpty(getEntityName(), "/", name);
    }

    protected String getUserId(HttpServletRequest request) {
        return RequestUtils.getUserId(request);
    }
}
