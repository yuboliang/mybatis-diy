package com.jr.mybatis.framework.config;

import com.jr.mybatis.framework.sqlsource.SqlSource;
import com.jr.mybatis.framework.utils.DocumentUtils;
import com.jr.mybatis.framework.utils.ResourceUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author: yuboliang
 * @date: 2019-11-13
 **/
public class XmlConfigBuilder {

    private Configuration configuration;

    public XmlConfigBuilder() {
        init();
    }

    private void init() {
        this.configuration = new Configuration();
    }

    public Configuration parse(InputStream inputStream) {
        Document document = DocumentUtils.readInputStream(inputStream);
        // 解析xml 获取根节点
        Element rootElement = document.getRootElement();
        // 解析Environments节点
        parseEnvironments(rootElement);

        // 解析mappers节点
        parseMappers(rootElement);

        return configuration;
    }

    private void parseEnvironments(Element rootElement) {
        // 获取environments节点
        Element environmentsElement = rootElement.element("environments");

        final String defaultEnv = environmentsElement.attributeValue("default");
        List<Element> environmentElementList = environmentsElement.elements("environment");
        Element environmentElement = environmentElementList.stream().filter(element -> element.attributeValue("id").equals(defaultEnv)).findFirst().get();
        Element dataSourceElement = environmentElement.element("dataSource");
        String type = dataSourceElement.attributeValue("type");

        Properties properties = new Properties();

        if (type.equals("DBCP")) {
            List<Element> propertyElementList = dataSourceElement.elements("property");
            propertyElementList.forEach(propertyElement -> {
                String name = propertyElement.attributeValue("name");
                String value = propertyElement.attributeValue("value");
                properties.setProperty(name, value);
            });
        }

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(dataSource);
    }

    private void parseMappers(Element rootElement) {
        Element mappersElement = rootElement.element("mappers");
        List<Element> mapperElementList = mappersElement.elements("mapper");

        mapperElementList.forEach(mapperElement -> {
            String mapperResource = mapperElement.attributeValue("resource");
            InputStream inputStream = ResourceUtils.getResourceAsStream(mapperResource);
            // 解析mapper的xml获取根节点
            parseMapper(inputStream);

        });
    }

    private void parseMapper(InputStream inputStream) {
        Document document = DocumentUtils.readInputStream(inputStream);
        Element mapperRootElement = document.getRootElement();
        String namespace = mapperRootElement.attributeValue("namespace");

        List<Element> selectElement = mapperRootElement.elements("select");
        selectElement.forEach(element -> {
            parseSelect(namespace, element);
        });

    }

    private void parseSelect(String namespace, Element selectElement) {
        String id = selectElement.attributeValue("id");
        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);
        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);
        String statementType = StringUtils.isBlank(selectElement.attributeValue("statementType")) ? "prepared" : selectElement.attributeValue("statementType");

        XmlScriptParser xmlScriptParser = new XmlScriptParser();
        SqlSource sqlSource = xmlScriptParser.parseSqlSource(selectElement);

        MappedStatement mappedStatement = new MappedStatement.MappedStatementBuilder().statementId(id)
                .parameterTypeClass(parameterTypeClass).resultTypeClass(resultTypeClass)
                .statementType(statementType).sqlSource(sqlSource).build();
        configuration.addMappedStatement(namespace + id, mappedStatement);
    }

    public Class<?> resolveClass(String className) {
        try {
            return ClassUtils.getClass(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
