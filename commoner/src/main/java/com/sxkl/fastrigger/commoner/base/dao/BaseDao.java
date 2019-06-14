package com.sxkl.fastrigger.commoner.base.dao;

import com.google.common.base.Joiner;
import com.sxkl.fastrigger.commoner.base.annotation.Condition;
import com.sxkl.fastrigger.commoner.base.dao.sql.SqlAndParameter;
import com.sxkl.fastrigger.commoner.base.dao.sql.SqlAndParameterHandlerFactory;
import com.sxkl.fastrigger.commoner.base.dao.sql.SqlOperateType;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.MyBatisSQL;
import com.sxkl.fastrigger.commoner.utils.ObjectUtils;
import com.sxkl.fastrigger.commoner.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统公用模块基础持久层
 * @author wy
 * @date 2019-06-10 11:14:52
 */
@Repository
public class BaseDao {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private SqlAndParameterHandlerFactory sqlAndParameterHandlerFactory;

    public <Entity extends BaseEntity> int insert(Entity entity) throws SQLException {
        Connection connection = null;
        SqlRunner sqlRunner;
        int num;
        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            sqlRunner = new SqlRunner(connection);
            SqlAndParameter sqlAndParameter = sqlAndParameterHandlerFactory.getSqlAndParameterHandler(SqlOperateType.INSERT).getSqlAndParameter(entity);
            num =  doInsert(sqlRunner, sqlAndParameter);
            connection.commit();
        }catch (SQLException e) {
            if(ObjectUtils.isNotNull(connection)) {
                connection.rollback();
            }
            throw e;
        }finally {
            if(ObjectUtils.isNotNull(connection)) {
                connection.setAutoCommit(true);
            }
            sqlRunner = null;
        }
        return num;
    }

    private int doInsert(SqlRunner sqlRunner, SqlAndParameter sqlAndParameter) throws SQLException {
        int num = 0;
        if(sqlAndParameter.isCheckExists()) {
            List<Map<String, Object>> list = sqlRunner.selectAll(sqlAndParameter.getCheckExistsSql(), sqlAndParameter.getIdValue());
            if(list.isEmpty()) {
                num += sqlRunner.insert(sqlAndParameter.getSql(), sqlAndParameter.getArgs());
            }
        }else {
            num += sqlRunner.insert(sqlAndParameter.getSql(), sqlAndParameter.getArgs());
        }
        List<SqlAndParameter> sqlAndParameterChildren = sqlAndParameter.getChildren();
        for(SqlAndParameter sqlAndParameterChild : sqlAndParameterChildren) {
            num += doInsert(sqlRunner, sqlAndParameterChild);
        }
        return num;
    }

    class BaseDaoProvider<T extends BaseEntity> {

        public String add(T entity) {
            return MyBatisSQL.builder()
                             .INSERT_INTO(getEntityName(entity))
                             .INTO_COLUMNS(getColumns(entity))
                             .INTO_VALUES(getValues(entity))
                             .build();
        }

        public String remove(T entity) throws IllegalAccessException {
            MyBatisSQL myBatisSQL = MyBatisSQL.builder().DELETE_FROM(getEntityName(entity));
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            for(Field field : declaredFields) {
                myBatisSQL = myBatisSQL.whereIfNotNull(field.get(entity), getCondition(field, true));
            }
            return myBatisSQL.build();
        }

        public String update(T entity) throws IllegalAccessException {
            MyBatisSQL myBatisSQL = MyBatisSQL.builder().UPDATE(getEntityName(entity));
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            for(Field field : declaredFields) {
                if(field.getName().equalsIgnoreCase("id")) {
                    continue;
                }
                myBatisSQL = myBatisSQL.setIfNotNull(field.get(entity), getCondition(field, false));
            }
            myBatisSQL = myBatisSQL.WHERE("id=#{id}");
            return myBatisSQL.build();
        }

        public String findOne(T entity) {
            return MyBatisSQL.builder()
                             .SELECT(getColumns(entity))
                             .FROM(getEntityName(entity))
                             .WHERE("id=#{id}")
                             .build();
        }

        public String findByCondition(T entity) throws IllegalAccessException {
            MyBatisSQL myBatisSQL = MyBatisSQL.builder().SELECT(getColumns(entity))
                    .FROM(getEntityName(entity));
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            for(Field field : declaredFields) {
                myBatisSQL = myBatisSQL.whereIfNotNull(field.get(entity), getCondition(field, true));
            }
            return myBatisSQL.build();
        }

        private String getCondition(Field field, boolean useAnnotation) {
            Condition[] conditions = field.getAnnotationsByType(Condition.class);
            String fieldName = field.getName();
            if(conditions.length == 0 || !useAnnotation) {
                return StringUtils.appendJoinEmpty(fieldName, "=#{", fieldName, "}");
            }
            String value = conditions[0].value().trim();
            value = value.equalsIgnoreCase("like") ? StringUtils.appendJoinEmpty(" ", value, " ") : value;
            return StringUtils.appendJoinEmpty(fieldName, value, "#{", fieldName, "}");
        }

        private String getEntityName(T entity) {
            return entity.getClass().getSimpleName();
        }

        private String getColumns(T entity) {
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            List<String> names = Arrays.stream(declaredFields).map(Field::getName).collect(Collectors.toList());
            String separator = ",";
            return Joiner.on(separator).join(names);
        }

        private String getValues(T entity) {
            Field[] declaredFields = entity.getClass().getDeclaredFields();
            List<String> names = Arrays.stream(declaredFields).map(field -> StringUtils.appendJoinEmpty("#{", field.getName(), "#")).collect(Collectors.toList());
            String separator = ",";
            return Joiner.on(separator).join(names);
        }
    }
}
