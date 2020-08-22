package com.message.board.dao;

import java.io.*;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * @author cnxqin
 * @desc
 * @date 2020/06/14 15:45
 */
public abstract class BaseDao<T>{


    public Connection getConnection() throws Exception{
        return DataSource.getConnection();
    }

    public abstract int save(T entity) throws Exception;
    public abstract int delete(Long id) throws Exception;
    public abstract int update(T entity) throws Exception;

    public T select(Long id) throws Exception{
        List<T> list = selectAll(id);
        return list.size() > 0 ? list.get(0) : null;
    }

    public abstract List<T> selectAll(Long id) throws Exception;
}

