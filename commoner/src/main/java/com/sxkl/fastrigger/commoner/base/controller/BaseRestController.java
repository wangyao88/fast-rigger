package com.sxkl.fastrigger.commoner.base.controller;

import com.github.pagehelper.PageInfo;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.base.entity.BasePageInfo;
import com.sxkl.fastrigger.commoner.base.entity.OperateResult;
import com.sxkl.fastrigger.commoner.base.service.BaseService;
import com.sxkl.fastrigger.commoner.utils.PaginationHelper;
import com.sxkl.fastrigger.commoner.utils.RequestUtils;
import com.sxkl.fastrigger.commoner.utils.StringUtils;
import com.sxkl.fastrigger.commoner.utils.UUIDUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

/**
 * 系统公用模块基础Restful api控制层
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public abstract class BaseRestController<T extends BaseEntity> extends BaseController<T>{

//    @PostMapping("/add")
//    @ResponseBody
//    protected OperateResult add(@RequestBody T entity,
//                                HttpServletRequest request,
//                                HttpServletResponse response) throws Exception{
//        String userId = RequestUtils.getUserId(request);
//        if(StringUtils.isBlank(userId)) {
//            String url = "login";
//            response.sendRedirect(url);
//            return OperateResult.builder().status(Boolean.FALSE).msg("未登录").build();
//        }
//        entity.setId(UUIDUtil.getUUID());
//        entity.setUserId(userId);
//        ZoneId zone = ZoneId.systemDefault();
//        entity.setCreateDateTime(LocalDateTime.now(zone));
//        return getService().add(entity);
//    }
//
//    @PostMapping("/remove")
//    @ResponseBody
//    protected OperateResult removeAll(@RequestBody T entity) throws Exception{
//        return getService().remove(entity);
//    }
//
//    @PostMapping("/update")
//    @ResponseBody
//    protected OperateResult update(@RequestBody T entity) throws Exception{
//        if(StringUtils.isBlank(entity.getId())) {
//            return OperateResult.builder().status(Boolean.FALSE).msg("更新实体主键为空，无法更新").build();
//        }
//        return getService().update(entity);
//    }
//
//    @GetMapping("/findOne")
//    @ResponseBody
//    protected BaseEntity findOne(@RequestBody T entity) {
//        return getService().findOne(entity);
//    }
//
//    @GetMapping("/findPage")
//    @ResponseBody
//    protected BasePageInfo<T> findPage(T entity, HttpServletRequest request) {
//        String userId = RequestUtils.getUserId(request);
//        entity = RequestUtils.requestToBean(request, entity);
//        entity.setUserId(userId);
//        Pageable pageable = PaginationHelper.buildPageInfo(request);
//        PageInfo<T> pageInfo = getService().findPage(pageable.getPageNumber(), pageable.getPageSize(), entity);
//        List<T> entities = pageInfo.getList();
//        List<T> indexedEntities = getService().addIndex(entities);
//        pageInfo.setList(indexedEntities);
//        return new BasePageInfo<>(pageInfo);
//    }
}
