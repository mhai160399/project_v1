package com.minhhai.thuctap.service_impl;

import com.minhhai.thuctap.dao.OfficeDao;
import com.minhhai.thuctap.dao_impl.OfficeDaoImpl;
import com.minhhai.thuctap.model.Office;
import com.minhhai.thuctap.service.OfficeService;

import java.sql.SQLException;
import java.util.List;

public class OfficeServiceImpl implements OfficeService {

    private OfficeDao officeDao = new OfficeDaoImpl();

    @Override
    public List<Office> findAll() throws SQLException {
        return officeDao.findAll();
    }

    @Override
    public Office findById(String id) throws SQLException {
        return id != "" ? officeDao.findById(id) : null;
    }

    @Override
    public List<Office> search(String text) throws SQLException {
        return text != "" ? officeDao.search(text) : null;
    }

    @Override
    public Office insert(Office office) throws SQLException {
        Office newOffice = new Office();
        newOffice.setOfficeCode(office.getOfficeCode());
        newOffice.setCity(office.getCity());
        newOffice.setPhone(office.getPhone());
        newOffice.setAddressLine1(office.getAddressLine1());
        newOffice.setAddressLine2(office.getAddressLine2());
        newOffice.setState(office.getState());
        newOffice.setCountry(office.getCountry());
        newOffice.setPostalCode(office.getPostalCode());
        newOffice.setTerritory(office.getTerritory());
        newOffice.setDelete_office(false);
        return officeDao.insert(newOffice);
    }

    @Override
    public boolean update(Office office) throws SQLException {
        return office.getOfficeCode() != "" ? officeDao.update(office) : false;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        return id != "" ? officeDao.delete(id) : false;
    }

    @Override
    public int getOfficeTotalRows() throws SQLException {
        return officeDao.getOfficeTotalRows();
    }

    @Override
    public List<Office> getOfficeFromTo(int startPage, int itemPerPage) throws SQLException {
        return officeDao.getOfficeFromTo(startPage,itemPerPage);
    }
}
