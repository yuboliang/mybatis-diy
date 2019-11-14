package com.jr.mybatis.framework.utils;

import java.io.InputStream;

/**
 * @author: yuboliang
 * @date: 2019-11-13
 **/
public class ResourceUtils {

    public static InputStream getResourceAsStream(String location) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(location);
    }
}
