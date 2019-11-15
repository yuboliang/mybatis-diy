package com.jr.mybatis.framework.sqlnode;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author: yuboliang
 * @date: 2019/11/15
 **/
@Data
@AllArgsConstructor
public class StaticTextSqlNode implements SqlNode {

    private String sqlText;

    @Override
    public void apply(DynamicContext context) {
        context.appendSql(sqlText);
    }
}
