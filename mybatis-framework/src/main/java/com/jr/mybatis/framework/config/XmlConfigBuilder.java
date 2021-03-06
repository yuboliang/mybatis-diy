package com.jr.mybatis.framework.config;

import com.jr.mybatis.framework.utils.DocumentUtils;
import com.jr.mybatis.framework.utils.ResourceUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;

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
            XmlStatementBuilder xmlStatementBuilder = new XmlStatementBuilder(namespace, element);
            MappedStatement mappedStatement = xmlStatementBuilder.parseSelect();

            configuration.addMappedStatement(namespace + "." + mappedStatement.getStatementId(), mappedStatement);
        });

    }



}
