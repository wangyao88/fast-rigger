package com.sxkl.fastrigger.commoner.base.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.dao.BaseDao;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.base.entity.OperateResult;
import com.sxkl.fastrigger.commoner.utils.UUIDUtil;

import java.util.List;
import java.util.stream.IntStream;

/**
 * 系统公用模块基础服务
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public abstract class BaseService<T extends BaseEntity> {

//    public OperateResult add(T entity) {
//        try {
//            entity.setId(UUIDUtil.getUUID());
//            getDao().add(entity);
//            return OperateResult.buildSuccess();
//        } catch (Exception e) {
//            return OperateResult.buildFail(e);
//        }
//    }
//
//    public OperateResult remove(T entity) {
//        try {
//            getDao().remove(entity);
//            return OperateResult.buildSuccess();
//        } catch (Exception e) {
//            return OperateResult.buildFail(e);
//        }
//    }
//
//    public OperateResult update(T entity) {
//        try {
//            getDao().update(entity);
//            return OperateResult.buildSuccess();
//        } catch (Exception e) {
//            return OperateResult.buildFail(e);
//        }
//    }
//
//    public T findOne(T entity) {
//        return getDao().findOne(entity);
//    }
//
//    public PageInfo<T> findPage(int pageNum, int pageSize, T entity) {
//        PageHelper.startPage(pageNum, pageSize);
//        List<T> entities =  getDao().findByCondition(entity);
//        return new PageInfo<>(entities);
//    }
//
//    public List<T> addIndex(List<T> entities) {
//        int size = entities.size();
//        List<T> datas = Lists.newArrayListWithCapacity(size);
//        IntStream.range(0, size).forEach(num->{
//            T data = entities.get(num);
//            data.setIndex(num+1);
//            datas.add(data);
//        });
//        return datas;
//    }
//
//    protected abstract <E extends BaseDao<T>> E getDao();
}
