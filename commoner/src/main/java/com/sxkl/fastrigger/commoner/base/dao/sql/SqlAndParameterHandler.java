package com.sxkl.fastrigger.commoner.base.dao.sql;

import com.sxkl.fastrigger.commoner.base.annotation.Table;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.ObjectUtils;

import java.sql.SQLException;

/**
 * sql和参数组装接口
 * @author wy
 * @date 2019-06-11 16:34:39
 */
public interface SqlAndParameterHandler {

    /**
     * 获取sql和参数实体
     * @param entity 操作实体
     * @param <Entity> 操作实体类型
     * @return sql和参数实体
     */
    <Entity extends BaseEntity> SqlAndParameter getSqlAndParameter(Entity entity) throws SQLException;

    SqlOperateType getSqlOperateType();

    default  <Entity extends BaseEntity> String getTableName(Entity entity) {
        Table[] tables = entity.getClass().getAnnotationsByType(Table.class);
        if(ObjectUtils.isNotNull(tables) && tables.length > 0) {
            return tables[0].name();
        }
        return entity.getClass().getSimpleName();
    }
}
