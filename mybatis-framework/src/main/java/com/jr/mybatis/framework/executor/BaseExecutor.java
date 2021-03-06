package com.jr.mybatis.framework.executor;

import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.MappedStatement;
import com.jr.mybatis.framework.handler.ParameterHandler;
import com.jr.mybatis.framework.handler.ResultSetHandler;
import com.jr.mybatis.framework.handler.StatementHandler;
import com.jr.mybatis.framework.sqlsource.BoundSql;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Slf4j
public abstract class BaseExecutor implements Executor {
    
    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param) {
        return query(mappedStatement, configuration, param, mappedStatement.getSqlSource().getBoundSql(param));
    }

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object param, BoundSql boundSql) {
        // 获取connection
        Connection connection = getConnection(configuration);
        // 创建Statement
        String statementType = mappedStatement.getStatementType();
        String sql = boundSql.getSql();
        log.info(sql);
        StatementHandler statementHandler = new StatementHandler(connection, statementType, sql);
        Statement statement = statementHandler.getStatement();
        // 设置参数
        ParameterHandler parameterHandler = new ParameterHandler(statement, statementType, param, mappedStatement.getParameterTypeClass(), boundSql.getParameterNameList());
        try {
            parameterHandler.processParam();
        } catch (SQLException | NoSuchFieldException | IllegalAccessException e) {
            log.error("设置参数异常", e);
            throw new RuntimeException(e);
        }
        // 执行SQL
        ResultSet resultSet = executeQuery(statement);
        // 封装参数并返回
        ResultSetHandler resultSetHandler = new ResultSetHandler(resultSet, mappedStatement.getResultTypeClass());

        try {
            return resultSetHandler.processResultSet();
        } catch (SQLException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
            log.error("封装返回值异常", e);
            throw new RuntimeException(e);
        }


    }

    public abstract ResultSet executeQuery(Statement statement);

    private Connection getConnection(Configuration configuration) {
        DataSource dataSource = configuration.getDataSource();
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            log.error("获取连接异常", e);
            throw new RuntimeException(e);
        }
    }


}