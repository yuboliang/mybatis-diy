package com.jr.mybatis.framework.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author: yuboliang
 * @date: 2019-11-14
 **/
@Slf4j
public class DocumentUtils {

    public static Document readInputStream(InputStream inputStream) {
        SAXReader reader = new SAXReader();
        try {
            return reader.read(inputStream);
        } catch (DocumentException e) {
            // todo 记录日志
            throw new RuntimeException(e);
        }
    }
}
