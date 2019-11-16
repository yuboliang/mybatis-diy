package com.jr.mybatis.framework.executor;

import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.MappedStatement;
import com.jr.mybatis.framework.sqlsource.BoundSql;
import com.jr.mybatis.framework.sqlsource.SqlSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存执行器
 * @author Liang
 * @date 2019-11-16
 */
public class CachingExecutor implements Executor {

    private Map<String, Object> oneLevelCache = new ConcurrentHashMap<>();

    private Executor delegate;

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param) {
        // 获取SQL
        SqlSource sqlSource = mappedStatement.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(param);
        String sql = boundSql.getSql();

        // 获取缓存中的数据
        Object cacheResult = oneLevelCache.get(sql);
        if (cacheResult != null) {
            return (List<T>) cacheResult;
        }

        // 静态代理，交给其他的executor 执行SQL
        List<T> result = query(mappedStatement, configuration, param, boundSql);

        // 设置缓存
        oneLevelCache.put(mappedStatement.getStatementId(), result);
        return result;
    }

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql) {
        return delegate.query(mappedStatement, configuration, param, boundSql);
    }
}
