package com.minhhai.thuctap.service;

import com.minhhai.thuctap.model.Office;

import java.sql.SQLException;
import java.util.List;

public interface OfficeService {
    List<Office> findAll() throws SQLException;

    Office findById(String id) throws SQLException;

    List<Office> search(String text) throws SQLException;

    Office insert(Office office) throws SQLException;

    boolean update(Office office) throws SQLException;

    boolean delete(String id) throws SQLException;

    int getOfficeTotalRows() throws SQLException;

    List<Office> getOfficeFromTo(int startPage, int itemPerPage) throws SQLException;
}
