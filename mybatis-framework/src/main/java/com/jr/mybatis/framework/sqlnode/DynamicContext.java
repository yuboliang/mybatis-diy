package com.jr.mybatis.framework.sqlnode;

import lombok.Data;

/**
 * @author: yuboliang
 * @date: 2019/11/15
 **/
@Data
public class DynamicContext {

    private StringBuffer sqlBuffer = new StringBuffer();

    private Object param;

    public void appendSql(String sql) {
        sqlBuffer.append(sql).append(" ");
    }

    public String getSql() {
        return sqlBuffer.toString();
    }

}
