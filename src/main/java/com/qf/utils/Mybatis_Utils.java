package com.qf.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;

public class Mybatis_Utils {
    private static SqlSession sqlSession;
    private static SqlSessionFactory sessionFactory;
    private static Logger logger;

    static {
        try {
           logger = Logger.getLogger(Mybatis_Utils.class);
            InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
            sessionFactory= new SqlSessionFactoryBuilder().build(is);

        } catch (IOException e) {
            e.printStackTrace();
            logger.error("加载失败！");
        }
    }

    public static SqlSession openSqlSession() {
        sqlSession = sessionFactory.openSession();
        return sqlSession;
    }
}
