package com.sxkl.fastrigger.commoner.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sxkl.fastrigger.commoner.base.entity.BaseConstants;
import com.sxkl.fastrigger.commoner.base.entity.BaseEntity;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.*;

/**
 * 系统公用模块 Request工具类
 * @author wy
 * @date 2019-06-10 11:14:52
 */
public class RequestUtils {

    private static final String DATATABLE_SEARCH_ENCODE= "_base64";

    public static <T> T requestToBean(HttpServletRequest request, Class<T> clazz){
        JSONObject json = new JSONObject();
        Base64.Decoder decoder = Base64.getDecoder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String element = parameterNames.nextElement();
            String value = request.getParameter(element);
            if(element.contains(DATATABLE_SEARCH_ENCODE)){
                value = new String(decoder.decode(value),Charsets.UTF_8);
                element = element.replaceAll(DATATABLE_SEARCH_ENCODE, StringUtils.EMPTY);
                json.put(element,value);
            }
            json.put(element,value);
        }
        return json.toJavaObject(clazz);
    }

    public static <E> E requestToBean(HttpServletRequest request, E entity){
        Map<String, String> fieldMap = Maps.newHashMap();
        Base64.Decoder decoder = Base64.getDecoder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()){
            String element = parameterNames.nextElement();
            String value = request.getParameter(element);
            if(element.contains(DATATABLE_SEARCH_ENCODE)){
                value = new String(decoder.decode(value),Charsets.UTF_8);
                element = element.replaceAll(DATATABLE_SEARCH_ENCODE, StringUtils.EMPTY);
                fieldMap.put(element,value);
            }
            if(element.equals("search") && StringUtils.isNotBlank(value)) {
                Map map = JSONObject.parseObject(value, Map.class);
                fieldMap.putAll(map);
            }
        }
        if(fieldMap.size() == 0) {
            return entity;
        }
        Field[] superFields = entity.getClass().getSuperclass().getDeclaredFields();
        Field[] selfFields = entity.getClass().getDeclaredFields();
        List<Field> fields = Lists.newArrayList(superFields);
        fields.addAll(Arrays.asList(selfFields));
        int size = fields.size();
        for (int j = 0; j < size; j++) {
            Field field = fields.get(j);
            String fieldName = field.getName();
            Object value = fieldMap.get(fieldName);
            if(value != null) {
                try {
                    field.setAccessible(true);
                    field.set(entity, value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
       return entity;
    }

    public static String getUserId(HttpServletRequest request) {
        Object attribute = request.getSession().getAttribute(BaseConstants.USER_KEY);
        return ObjectUtils.isNull(attribute) ? StringUtils.EMPTY : ((BaseEntity)attribute).getUserId();
    }
}
