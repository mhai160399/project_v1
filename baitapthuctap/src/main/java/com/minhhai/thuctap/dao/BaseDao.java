package com.minhhai.thuctap.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

//Lập trình tổng quát với <T>
public interface BaseDao<T> {

    T getObject(ResultSet resultSet) throws SQLException;

    List<T> getList(ResultSet resultSet) throws SQLException;

    // lấy tất cả các bán ghi trong datbase của bảng category

}
