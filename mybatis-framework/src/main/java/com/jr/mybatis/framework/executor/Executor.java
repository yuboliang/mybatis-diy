package com.jr.mybatis.framework.executor;


import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.MappedStatement;
import com.jr.mybatis.framework.sqlsource.BoundSql;

import java.util.List;

public interface Executor {
	
	/**
	 * 
	 * @param mappedStatement 获取SQL语句和入参出参类型信息
	 * @param configuration	获取数据源连接处信息
	 * @param param	获取入参类型
	 * @return
	 */
	<T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param);

	/**
	 *
	 * @param mappedStatement 获取SQL语句和入参出参类型信息
	 * @param configuration	获取数据源连接处信息
	 * @param param	获取入参类型
	 * @param boundSql 解析后的boundSql
	 * @return
	 */
	<T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql);
}
