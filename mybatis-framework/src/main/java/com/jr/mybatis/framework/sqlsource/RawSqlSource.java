package com.jr.mybatis.framework.sqlsource;

import com.jr.mybatis.framework.sqlnode.DynamicContext;
import com.jr.mybatis.framework.sqlnode.SqlNode;
import com.jr.mybatis.framework.utils.GenericTokenParser;
import com.jr.mybatis.framework.utils.ParameterMappingTokenHandler;

/**
 * @author: yuboliang
 * @date:
 **/
public class RawSqlSource implements SqlSource {

    private BoundSql boundSql;

    public RawSqlSource(SqlNode sqlNode) {
        DynamicContext dynamicContext = new DynamicContext();
        sqlNode.apply(dynamicContext);
        String sqlText = dynamicContext.getSql();

        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);
        String sql = genericTokenParser.parse(sqlText);

        boundSql = new BoundSql(sql, parameterMappingTokenHandler.getParameterNameList());
    }

    @Override
    public BoundSql getBoundSql(Object param) {
        return boundSql;
    }
}
