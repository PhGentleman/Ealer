package org.ph.ealer.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MyBatisUtil {
    public static SqlSession getSqlSession() {
        try (InputStream is = Resources.getResourceAsStream("mybatis-config.xml")) {
            SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
            SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(is);
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            return sqlSession;
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }
}