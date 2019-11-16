package com.jr.mybatis.framework.sqlsession;

import com.jr.mybatis.framework.config.Configuration;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    @Override
    public <T> T selectOne(Object param) {
        return null;
    }

    @Override
    public <T> List<T> selectList(Object param) {
        return null;
    }
}
