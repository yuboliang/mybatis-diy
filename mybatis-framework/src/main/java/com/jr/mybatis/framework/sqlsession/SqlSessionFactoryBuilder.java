package com.jr.mybatis.framework.sqlsession;

import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.XmlConfigBuilder;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    public SqlSessionFactory build(InputStream inputStream) {
        XmlConfigBuilder configBuilder = new XmlConfigBuilder();
        Configuration configuration = configBuilder.parse(inputStream);

        return new DefaultSqlSessionFactory(configuration);
    }
}
