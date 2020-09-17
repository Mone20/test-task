package com.testtask.tables;

import com.testtask.domain.Entity;

import java.sql.SQLException;
import java.util.List;

public interface ServiceInterface<T>{
    public int insert(T newInstance) throws SQLException;
    public int delete(Long id) throws SQLException;
    public List<T> findAll() throws SQLException;
    public int update(T newInstance) throws SQLException;
}
