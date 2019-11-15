package com.jr.mybatis.framework.sqlnode;

import com.jr.mybatis.framework.utils.OgnlUtils;
import lombok.Data;
import ognl.Ognl;

import java.util.List;

/**
 * @author: yuboliang
 * @date: 2019/11/15
 **/
@Data
public class IfSqlNode implements SqlNode {

    private String testOgnl;

    private SqlNode sqlNode;

    @Override
    public void apply(DynamicContext context) {
        // 判断ognl是否满足条件

        boolean test = OgnlUtils.evaluateBoolean(testOgnl, context.getParam());

        if (test) {
            sqlNode.apply(context);
        }
    }
}
