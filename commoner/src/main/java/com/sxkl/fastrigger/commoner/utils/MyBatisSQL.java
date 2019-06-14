package com.sxkl.fastrigger.commoner.utils;


import org.apache.ibatis.jdbc.AbstractSQL;

/**
 * 系统公用模块 sql组装工具类
 * @author wy
 * @date 2019-06-10 11:14:52zu
 */
public class MyBatisSQL extends AbstractSQL<MyBatisSQL> {

	public MyBatisSQL whereIfNotNull(Object param, String conditions) {
		if(param instanceof String ) {
			if(StringUtils.isNotBlank(param+"")) {
				super.WHERE(conditions);
			}
		}else if(ObjectUtils.isNotNull(param)){
			super.WHERE(conditions);
		}
		return this;
	}

	public MyBatisSQL setIfNotNull(Object param, String conditions) {
		if(ObjectUtils.isNotNull(param)){
			super.SET(conditions);
		}
		return this;
	}

	@Override
	public MyBatisSQL getSelf() {
		return this;
	}

	public static AbstractSQL<MyBatisSQL> builder() {
		return new MyBatisSQL();
	}

	public String build() {
		return this.toString();
	}
}
