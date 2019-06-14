package com.sxkl.fastrigger.commoner.base.dao.sql;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAndValue;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAnnotation;
import com.sxkl.fastrigger.commoner.base.dao.annotation.FieldAnnotationParser;
import com.sxkl.fastrigger.commoner.base.dao.annotation.ManyToManyFieldAndValue;
import com.sxkl.fastrigger.commoner.base.entity.BaseConstants;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;
import com.sxkl.fastrigger.commoner.utils.MyBatisSQL;
import com.sxkl.fastrigger.commoner.utils.StringUtils;

import java.sql.SQLException;
import java.util.List;

/**
 * sql和参数组装接口 插入类型
 * @author wy
 * @date 2019-06-11 16:50:43
 */
public class InsertSqlAndParameterHandler implements SqlAndParameterHandler {

    @Override
    public <Entity extends BaseEntity> SqlAndParameter getSqlAndParameter(Entity entity) throws SQLException {
        SqlAndParameter sqlAndParameter = new SqlAndParameter();
        doHandler(entity, sqlAndParameter);
        return sqlAndParameter;
    }

    private <Entity extends BaseEntity> SqlAndParameter doHandler(Entity entity, SqlAndParameter<Entity> sqlAndParameter) throws SQLException {
        sqlAndParameter.setEntity(entity);

        List<String> names = Lists.newArrayList();
        List<Object> args = Lists.newArrayList();

        FieldAnnotation fieldAnnotation = FieldAnnotationParser.parse(entity);
        List<FieldAndValue<Entity>> ids = fieldAnnotation.getId();
        sqlAndParameter.setIdName(ids.get(0).getName());
        sqlAndParameter.setIdValue(ids.get(0).getValue());
        parseId(ids, names, args);
        parseColumns(fieldAnnotation.getColumns(), names, args);
        parseManyToOnes(sqlAndParameter, names, args, fieldAnnotation);
        parseOneToOnes(sqlAndParameter, names, args, fieldAnnotation);
        parseOneToManys(sqlAndParameter, fieldAnnotation);
        parseManyToManys(sqlAndParameter, fieldAnnotation);

        configureSqlAndParameter(entity, sqlAndParameter, names, args);
        return sqlAndParameter;
    }

    private <Entity extends BaseEntity> void parseId(List<FieldAndValue<Entity>> ids, List<String> names, List<Object> args) {
        for(FieldAndValue<Entity> id : ids) {
            parse(id, names, args);
        }
    }

    private <Entity extends BaseEntity> void parseColumns(List<FieldAndValue<Entity>> columns, List<String> names, List<Object> args) {
        for(FieldAndValue<Entity> column : columns) {
            parse(column, names, args);
        }
    }

    private <Entity extends BaseEntity> void parseManyToOnes(SqlAndParameter<Entity> sqlAndParameter, List<String> names, List<Object> args, FieldAnnotation fieldAnnotation) {
        List<FieldAndValue<Entity>> manyToOnes = fieldAnnotation.getManyToOnes();
        parseToOnes(sqlAndParameter, names, args, manyToOnes);
    }

    private <Entity extends BaseEntity> void parseOneToOnes(SqlAndParameter<Entity> sqlAndParameter, List<String> names, List<Object> args, FieldAnnotation fieldAnnotation) {
        List<FieldAndValue<Entity>> oneToOnes = fieldAnnotation.getOneToOnes();
        parseToOnes(sqlAndParameter, names, args, oneToOnes);
    }

    private <Entity extends BaseEntity> void parseOneToManys(SqlAndParameter<Entity> sqlAndParameter, FieldAnnotation fieldAnnotation) {
        List<FieldAndValue<Entity>> toManys = fieldAnnotation.getOneToManys();
        parseToManys(sqlAndParameter, toManys);
    }

    private <Entity extends BaseEntity> void parseManyToManys(SqlAndParameter<Entity> sqlAndParameter, FieldAnnotation fieldAnnotation) {
        List<FieldAndValue<Entity>> toManys = fieldAnnotation.getManyToManys();
        parseToManys(sqlAndParameter, toManys);

        toManys.stream().filter(FieldAndValue::isCascade).map(toMany -> (ManyToManyFieldAndValue)toMany).forEach(manyToMany -> {
            SqlAndParameter<Entity> childSqlAndParameter = new SqlAndParameter<>();

            String columns = StringUtils.append(BaseConstants.SQL_SEPARATOR, manyToMany.getMyIdName(), manyToMany.getOtherIdName());
            String values = initValues(2);
            String sql = MyBatisSQL.builder()
                                   .INSERT_INTO(manyToMany.getMiddleTableName())
                                   .INTO_COLUMNS(columns)
                                   .INTO_VALUES(values)
                                   .build();
            childSqlAndParameter.setSql(sql);
            childSqlAndParameter.setArgs(new Object[]{manyToMany.getMyIdValue(), manyToMany.getOtherIdValue()});
            sqlAndParameter.getChildren().add(childSqlAndParameter);
        });
    }

    private <Entity extends BaseEntity> void parseToManys(SqlAndParameter<Entity> sqlAndParameter, List<FieldAndValue<Entity>> toManys) {
        toManys.stream().filter(FieldAndValue::isCascade).forEach(toMany -> parseCascade(sqlAndParameter, toMany));
    }

    private <Entity extends BaseEntity> void parseToOnes(SqlAndParameter<Entity> sqlAndParameter, List<String> names, List<Object> args, List<FieldAndValue<Entity>> toOnes) {
        toOnes.stream().filter(FieldAndValue::isCascade).forEach(toOne -> {
            parse(toOne, names, args);
            parseCascade(sqlAndParameter, toOne);
        });
    }

    private <Entity extends BaseEntity> void parseCascade(SqlAndParameter<Entity> sqlAndParameter, FieldAndValue<Entity> cascade) {
        try {
            SqlAndParameter<Entity> childSqlAndParameter = new SqlAndParameter<>();
            childSqlAndParameter.setCheckExists(true);
            Entity entity = cascade.getSourceEntity();
            entity.setSource(sqlAndParameter.getEntity());
            sqlAndParameter.getChildren().add(doHandler(entity, childSqlAndParameter));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private <Entity extends BaseEntity> void parse(FieldAndValue<Entity> fieldAndValue, List<String> names, List<Object> args) {
        names.add(fieldAndValue.getName());
        args.add(fieldAndValue.getValue());
    }

    private <Entity extends BaseEntity> void configureSqlAndParameter(Entity entity, SqlAndParameter sqlAndParameter, List<String> names, List<Object> args) {
        String columns = Joiner.on(BaseConstants.SQL_SEPARATOR).join(names);
        int size = names.size();
        String values = initValues(size);
        String sql = MyBatisSQL.builder()
                               .INSERT_INTO(getTableName(entity))
                               .INTO_COLUMNS(columns)
                               .INTO_VALUES(values)
                               .build();
        sqlAndParameter.setSql(sql);
        sqlAndParameter.setArgs(args.toArray());
        sqlAndParameter.setTableName(getTableName(entity));
    }

    private String initValues(int size) {
        List<String> valuesList = Lists.newArrayList();
        for (int i = 0; i < size; i++) {
            valuesList.add(BaseConstants.QUESTION_MARK);
        }
        return Joiner.on(BaseConstants.SQL_SEPARATOR).join(valuesList);
    }

    @Override
    public SqlOperateType getSqlOperateType() {
        return SqlOperateType.INSERT;
    }

}
