package com.jr.mybatis.framework.executor;

import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class SimpleExecutor extends BaseExecutor {

    @Override
    public ResultSet executeQuery(Statement statement) {
        try {
            return ((PreparedStatement)statement).executeQuery();
        } catch (SQLException e) {
            log.error("SQL执行异常", e);
            throw new RuntimeException(e);
        }
    }
}
