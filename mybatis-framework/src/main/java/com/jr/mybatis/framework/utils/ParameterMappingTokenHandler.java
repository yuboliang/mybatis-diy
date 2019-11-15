package com.jr.mybatis.framework.utils;

import java.util.ArrayList;
import java.util.List;

public class ParameterMappingTokenHandler implements TokenHandler {
    private List<String> parameterNameList = new ArrayList<>();

	@Override
	public String handleToken(String content) {
		parameterNameList.add(content);
		return "?";
	}


}
