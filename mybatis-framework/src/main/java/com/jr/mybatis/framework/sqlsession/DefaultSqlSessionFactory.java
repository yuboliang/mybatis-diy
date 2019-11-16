package com.jr.mybatis.framework.sqlsession;

import com.jr.mybatis.framework.config.Configuration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
