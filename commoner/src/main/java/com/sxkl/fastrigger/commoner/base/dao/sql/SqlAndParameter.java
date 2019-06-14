package com.sxkl.fastrigger.commoner.base.dao.sql;

import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * sql和参数实体
 * @author wy
 * @date 2019-06-11 16:34:39
 */
@Data
public class SqlAndParameter<Entity extends BaseEntity> {

    private String sql;
    private Object[] args;
    private String idName;
    private Object idValue;
    private String tableName;
    private boolean checkExists = false;
    private List<SqlAndParameter> children = Lists.newArrayList();
    private Entity entity;

    public String getCheckExistsSql() {
        return "select " + getIdName() + " from " + getTableName() + " where " + getIdName() + " = ?";
    }
}