package com.jr.mybatis.framework.sqlnode;

/**
 * @author: yuboliang
 * @date:
 **/
public interface SqlNode {

    void apply(DynamicContext context);
}
