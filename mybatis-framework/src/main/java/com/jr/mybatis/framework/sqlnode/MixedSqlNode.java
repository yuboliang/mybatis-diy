package com.jr.mybatis.framework.sqlnode;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 * @author: yuboliang
 * @date: 2019/11/15
 **/
@AllArgsConstructor
public class MixedSqlNode implements SqlNode {
    List<SqlNode> sqlNodeList;

    @Override
    public void apply(DynamicContext context) {
        sqlNodeList.forEach(node -> node.apply(context));
    }
}
