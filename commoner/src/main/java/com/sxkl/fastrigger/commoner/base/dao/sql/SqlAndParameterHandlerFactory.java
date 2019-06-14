package com.sxkl.fastrigger.commoner.base.dao.sql;

import com.google.common.collect.Maps;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.Set;

/**
 * sql和参数处理器工厂类
 * @author wy
 * @date 2019-06-12 13:03:33
 */
@Component
public class SqlAndParameterHandlerFactory {

    private static Map<SqlOperateType, SqlAndParameterHandler> handlers = Maps.newHashMap();

    @PostConstruct
    private void init() {
        Reflections reflections = new Reflections(SqlAndParameterHandlerFactory.class.getPackage().getName());
        Set<Class<? extends SqlAndParameterHandler>> sqlAndParameterHandlers = reflections.getSubTypesOf(SqlAndParameterHandler.class);
        sqlAndParameterHandlers.stream().forEach(handler->{
            SqlAndParameterHandler sqlAndParameterHandler = null;
            try {
                sqlAndParameterHandler = handler.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            handlers.put(sqlAndParameterHandler.getSqlOperateType(), sqlAndParameterHandler);
        });
    }

    public SqlAndParameterHandler getSqlAndParameterHandler(SqlOperateType sqlOperateType) {
        return handlers.get(sqlOperateType);
    }
}
