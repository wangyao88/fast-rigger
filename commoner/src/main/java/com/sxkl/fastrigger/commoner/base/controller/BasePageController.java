package com.sxkl.fastrigger.commoner.base.controller;

import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 系统公用模块基础视图控制层
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public abstract class BasePageController<T extends BaseEntity>  extends BaseController<T>{

//    @GetMapping("/addPage")
//    public String addPage() {
//        return getViewName("add");
//    }
//
//    @GetMapping("/updatePage")
//    public ModelAndView updatePage(@RequestParam("id") String id) {
//        String viewName =  getViewName("update");
//        ModelAndView mv = new ModelAndView(viewName);
//        mv.addObject("id", id);
//        return mv;
//    }
//
//    @GetMapping("/tablePage")
//    public ModelAndView tablePage() {
//        return new ModelAndView(getViewName("table"));
//    }
}
