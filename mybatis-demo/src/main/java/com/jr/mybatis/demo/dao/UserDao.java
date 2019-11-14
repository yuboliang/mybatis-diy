package com.jr.mybatis.demo.dao;

import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.XmlConfigBuilder;
import com.jr.mybatis.framework.utils.ResourceUtils;

import java.io.InputStream;

/**
 * @author: yuboliang
 * @date: 2019-11-13
 **/
public class UserDao {

    public void getConfiguration() {
        String resource = "mybatis-config.xml";
		 InputStream inputStream = ResourceUtils.getResourceAsStream(resource);

		XmlConfigBuilder builder = new XmlConfigBuilder();
		Configuration configuration = builder.parse(inputStream);
		System.out.println(configuration);
    }
}
