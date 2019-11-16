package com.jr.mybatis.framework.utils;

import com.jr.mybatis.framework.config.MappedStatement;
import com.jr.mybatis.framework.sqlsource.SqlSource;

/**
 * @author Liang
 * @date 2019-11-16
 */
public class GetSqlUtils {

    public static String getSql(MappedStatement mappedStatement, Object param) {
        SqlSource sqlSource = mappedStatement.getSqlSource();
        return sqlSource.getBoundSql(param).getSql();
    }
}
