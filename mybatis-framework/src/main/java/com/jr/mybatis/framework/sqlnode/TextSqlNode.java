package com.jr.mybatis.framework.sqlnode;

import com.jr.mybatis.framework.utils.BindingTokenParser;
import com.jr.mybatis.framework.utils.GenericTokenParser;
import lombok.AllArgsConstructor;

/**
 * @author: yuboliang
 * @date: 2019/11/15
 **/
@AllArgsConstructor
public class TextSqlNode implements SqlNode {

    private String sqlText;

    @Override
    public void apply(DynamicContext context) {
        BindingTokenParser bindingTokenParser = new BindingTokenParser(context);
        GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", bindingTokenParser);
        String sql = genericTokenParser.parse(sqlText);
        context.appendSql(sql);
    }
}
