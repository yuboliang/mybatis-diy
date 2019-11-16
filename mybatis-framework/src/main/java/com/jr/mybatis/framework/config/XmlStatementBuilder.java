package com.jr.mybatis.framework.config;

import com.jr.mybatis.framework.sqlsource.SqlSource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

/**
 * @author Liang
 * @date 2019-11-16
 */
@AllArgsConstructor
public class XmlStatementBuilder {
    private String namespace;
    private Element element;


    public MappedStatement parseSelect() {
        String id = element.attributeValue("id");
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);
        String resultType = element.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);
        String statementType = StringUtils.isBlank(element.attributeValue("statementType")) ? "prepared" : element.attributeValue("statementType");

        XmlScriptParser xmlScriptParser = new XmlScriptParser();
        SqlSource sqlSource = xmlScriptParser.parseSqlSource(element);

        return new MappedStatement.MappedStatementBuilder().statementId(id)
                .parameterTypeClass(parameterTypeClass).resultTypeClass(resultTypeClass)
                .statementType(statementType).sqlSource(sqlSource).build();
    }

    private Class<?> resolveClass(String className) {
        try {
            return ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
