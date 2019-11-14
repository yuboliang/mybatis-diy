package com.jr.mybatis.framework.config;

import lombok.Data;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: yuboliang
 * @date:
 **/
@Data
public class Configuration {

    private DataSource dataSource;

    private Map<String, MappedStatement> mappedStatementMap = new HashMap<>();

    public void addMappedStatement(String id, MappedStatement mappedStatement) {
        mappedStatementMap.put(id, mappedStatement);
    }
}
