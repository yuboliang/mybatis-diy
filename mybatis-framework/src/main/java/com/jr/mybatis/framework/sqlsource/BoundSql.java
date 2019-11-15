package com.jr.mybatis.framework.sqlsource;

import lombok.Data;

import java.util.List;

/**
 * @author: yuboliang
 * @date:
 **/
@Data
public class BoundSql {

    private String sql;

    private List<String> parameterNameList;
}
