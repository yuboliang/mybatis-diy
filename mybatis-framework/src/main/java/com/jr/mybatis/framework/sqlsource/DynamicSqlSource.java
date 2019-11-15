package com.jr.mybatis.framework.sqlsource;

import com.jr.mybatis.framework.sqlnode.DynamicContext;
import com.jr.mybatis.framework.sqlnode.SqlNode;
import com.jr.mybatis.framework.utils.GenericTokenParser;
import com.jr.mybatis.framework.utils.ParameterMappingTokenHandler;
import lombok.AllArgsConstructor;

/**
 * @author: yuboliang
 * @date:
 **/
@AllArgsConstructor
public class DynamicSqlSource implements SqlSource {

    private SqlNode sqlNode;

    @Override
    public BoundSql getBoundSql(Object param) {
        DynamicContext dynamicContext = new DynamicContext();
        dynamicContext.setParam(param);
        sqlNode.apply(dynamicContext);

        String sqlText = dynamicContext.getSql();

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sql = genericTokenParser.parse(sqlText);

        return new BoundSql(sql, parameterMappingTokenHandler.getParameterNameList());
    }
}
