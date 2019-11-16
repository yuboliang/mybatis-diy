package com.jr.mybatis.framework.handler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Liang
 * @date 2019-11-16
 */
@AllArgsConstructor
@Slf4j
public class StatementHandler {

    private Connection connection;

    private String statementType;

    private String sql;

    public Statement getStatement() {
        if ("prepare".equals(statementType)) {
            try {
                return connection.prepareStatement(sql);
            } catch (SQLException e) {
                log.error("获取statement异常", e);
                throw new RuntimeException(e);
            }
        }

        return null;
    }
}
