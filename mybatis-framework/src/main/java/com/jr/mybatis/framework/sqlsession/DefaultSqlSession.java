package com.jr.mybatis.framework.sqlsession;

import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.executor.CachingExecutor;
import com.jr.mybatis.framework.executor.SimpleExecutor;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    @Override
    public <T> T selectOne(String statementId, Object param) {
        List<T> result = selectList(statementId, param);
        return result.stream().findFirst().orElse(null);
    }

    @Override
    public <T> List<T> selectList(String statementId, Object param) {

        CachingExecutor cachingExecutor = new CachingExecutor(new SimpleExecutor());
        return null;
    }
}
