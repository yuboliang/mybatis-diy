package com.jr.mybatis.framework.config;


import com.jr.mybatis.framework.sqlsource.SqlSource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MappedStatement {

	private String statementId;
	private Class<?> parameterTypeClass;
	private Class<?> resultTypeClass;
	private String statementType;
	private SqlSource sqlSource;

}
