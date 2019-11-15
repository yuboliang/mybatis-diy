package com.jr.mybatis.framework.sqlsource;

public interface SqlSource {

    BoundSql getBoundSql(Object param);
}
