package com.jr.mybatis.framework.config;

import com.jr.mybatis.framework.sqlnode.*;
import com.jr.mybatis.framework.sqlsource.DynamicSqlSource;
import com.jr.mybatis.framework.sqlsource.RawSqlSource;
import com.jr.mybatis.framework.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.List;

public class XmlScriptParser {

    private boolean dynamicSql;

    public SqlSource parseSqlSource(Element selectElement) {
        MixedSqlNode mixedSqlNode = parseSqlNode(selectElement);

        if (dynamicSql) {
            return new DynamicSqlSource(mixedSqlNode);
        } else {
            return new RawSqlSource(mixedSqlNode);
        }
    }

    private MixedSqlNode parseSqlNode(Element sqlElement) {
        List<SqlNode> allSqlNode = new ArrayList<>();

        int nodeCount = sqlElement.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = sqlElement.node(i);

            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                SqlNode sqlNode = null;
                boolean isDynamicSql = checkDynamicSql(sqlText);
                if (isDynamicSql) {
                    sqlNode = new TextSqlNode(sqlText);
                } else {
                    sqlNode = new StaticTextSqlNode(sqlText);
                }

                allSqlNode.add(sqlNode);
            } else {
                Element ifElement = (Element) node;
                String testOgnl = ifElement.attributeValue("test");
                MixedSqlNode mixedSqlNode = parseSqlNode(ifElement);

                IfSqlNode ifSqlNode = new IfSqlNode();
                ifSqlNode.setTestOgnl(testOgnl);
                ifSqlNode.setSqlNode(mixedSqlNode);

                allSqlNode.add(ifSqlNode);

                dynamicSql = true;
            }
        }

        return new MixedSqlNode(allSqlNode);
    }

    private boolean checkDynamicSql(String sqlText) {
        if (sqlText.contains("${")) {
            dynamicSql = true;
            return true;
        }

        return false;
    }
}
