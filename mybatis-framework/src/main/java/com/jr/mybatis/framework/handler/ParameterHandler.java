package com.jr.mybatis.framework.handler;

import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * @author Liang
 * @date 2019-11-16
 */
@AllArgsConstructor
public class ParameterHandler {
    private Statement statement;
    private String statementType;
    private Object param;
    private Class<?> parameterTypeClass;
    private List<String> parameterNameList;


    public void processParam() throws SQLException, NoSuchFieldException, IllegalAccessException {
        PreparedStatement preparedStatement = (PreparedStatement) statement;

        if (parameterTypeClass.equals(Integer.class)) {
            preparedStatement.setObject(1, Integer.parseInt(param.toString()));
        } else if (parameterTypeClass.equals(String.class)) {
            preparedStatement.setObject(1, param.toString());
        } else if (parameterTypeClass.equals(Map.class)) {

        } else {
            for (int i = 0; i < parameterNameList.size(); i++) {
                Field field = parameterTypeClass.getDeclaredField(parameterNameList.get(i));
                field.setAccessible(true);
                Object value = field.get(param);

                preparedStatement.setObject(i + 1, value);
            }
        }
    }
}
