package com.jr.mybatis.framework.executor;


import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.MappedStatement;

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
	 * @param sql 解析后的SQL
	 * @return
	 */
	<T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param, String sql);
}
