package com.jr.mybatis.framework.handler;

import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liang
 * @date 2019-11-16
 */
@AllArgsConstructor
public class ResultSetHandler {
    private ResultSet resultSet;
    private Class<?> resultTypeClass;


    public <T> List<T> processResultSet() throws SQLException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            Object result = resultTypeClass.newInstance();

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                Field field = resultTypeClass.getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(result, resultSet.getObject(i + 1));
            }

            list.add((T) result);
        }
        return list;
    }
}
