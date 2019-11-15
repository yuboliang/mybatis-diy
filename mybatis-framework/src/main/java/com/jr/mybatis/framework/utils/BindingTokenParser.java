package com.jr.mybatis.framework.utils;

import com.jr.mybatis.framework.sqlnode.DynamicContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BindingTokenParser implements TokenHandler {

		private DynamicContext context;

		@Override
		public String handleToken(String expression) {
			Object param = context.getParam();
			if (param == null) {
				return "";
			} else if (SimpleTypeRegistry.isSimpleType(param.getClass())) {
				return String.valueOf(param);
			}

			// 使用Ognl api去获取相应的值
			Object value = OgnlUtils.getValue(expression, context.getParam());
			return value == null ? "" : String.valueOf(value);
		}

	}