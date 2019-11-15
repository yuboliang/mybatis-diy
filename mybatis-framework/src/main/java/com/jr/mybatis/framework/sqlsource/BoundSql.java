package com.jr.mybatis.framework.sqlsource;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author: yuboliang
 * @date:
 **/
@AllArgsConstructor
@Data
public class BoundSql {

    private String sql;

    private List<String> parameterNameList;
}
