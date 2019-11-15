package com.jr.mybatis.framework.utils;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ParameterMappingTokenHandler implements TokenHandler {
    private List<String> parameterNameList = new ArrayList<>();

	@Override
	public String handleToken(String content) {
		parameterNameList.add(content);
		return "?";
	}


}
