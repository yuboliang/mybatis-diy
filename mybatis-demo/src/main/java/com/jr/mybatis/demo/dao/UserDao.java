package com.jr.mybatis.demo.dao;

import com.jr.mybatis.demo.po.User;
import com.jr.mybatis.framework.config.Configuration;
import com.jr.mybatis.framework.config.XmlConfigBuilder;
import com.jr.mybatis.framework.sqlsession.SqlSession;
import com.jr.mybatis.framework.sqlsession.SqlSessionFactory;
import com.jr.mybatis.framework.sqlsession.SqlSessionFactoryBuilder;
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

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

		SqlSession sqlSession = sqlSessionFactory.openSession();

		User user = sqlSession.selectOne("test.findUserById", null);

	}

	public static void main(String[] args) {
		UserDao userDao = new UserDao();
		userDao.getConfiguration();
	}
}
