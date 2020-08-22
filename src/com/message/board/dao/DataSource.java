package com.message.board.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/18 20:22
 */
public class DataSource {

    static volatile Boolean mock = false;
    static volatile Connection connection = null;

    public static Connection getConnection() throws Exception{
        InputStream is = null;
        try {
            if(mock){
                return null;
            }
            if(connection == null) {
                synchronized (BaseDao.class) {
                    if(connection == null) {
                        is = BaseDao.class.getClassLoader().getResourceAsStream("/db.properties");
                        Properties db = new Properties();
                        db.load(is);

                        String mock = db.getProperty("jdbc.mock");
                        if(mock != null && new Boolean(mock)){
                            DataSource.mock = true;
                            System.out.println("=====> the mock DataSource info has inited successful.");
                            return null;
                        }
                        Class.forName(db.getProperty("jdbc.driver"));
                        connection = DriverManager.getConnection(db.getProperty("jdbc.url"),
                                db.getProperty("jdbc.username"), db.getProperty("jdbc.password"));
                        System.out.println("=====> the jdbc info has inited successful.");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("init jdbc info fail...");
            throw new RuntimeException(e);
        }finally {
            if(is != null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return connection;
    }

}
