package com.minhhai.thuctap.dao_impl;

import com.minhhai.thuctap.common.MyConnection;
import com.minhhai.thuctap.dao.OfficeDao;
import com.minhhai.thuctap.model.Office;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfficeDaoImpl implements OfficeDao {

    private MyConnection myConnection = new MyConnection();

    //hàm dùng để đọc ra các trường trong resultSet để trả về đối tượng có các thuộc tính tương ứng
    @Override
    public Office getObject(ResultSet resultSet) throws SQLException {
        Office office =null;
        // Sử dụng contructor full tham số (int id , String name,boolean deleleted)
        //resultSet.getInt("tên cột") để lấy ra giá trị của tên cột tương ứng ví dụ resultSet.getInt("id") để lấy ra
        //giá trị cột id
        office = new Office(resultSet.getString("OfficeCode"),
                            resultSet.getString("city"),
                            resultSet.getString("phone"),
                            resultSet.getString("addressLine1"),
                            resultSet.getString("addressLine2"),
                            resultSet.getString("state"),
                            resultSet.getString("country"),
                            resultSet.getString("postalCode"),
                            resultSet.getString("territory"),
                            resultSet.getBoolean("delete_office")
                );
        return office;
    }

    @Override
    public List<Office> getList(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public List<Office> findAll() throws SQLException {
        List<Office> officeList = new ArrayList<>();
        String sql = "CALL get_all_office()"; //câu lệnh call procedure
        PreparedStatement preparedStatement = myConnection.prepare(sql); // lấy ra prepare dùng cho câu lệnh query
        ResultSet resultSet = preparedStatement.executeQuery(); // thực thi câu lệnh query và lấy resultSet trả về

        //resetSet.first() để đưa con trỏ resetSet về bản ghi đầu tiên lấy được nếu tồn tại trả về true, còn không thì false
        if(resultSet.first()) {
            do {
                Office office = getObject(resultSet);
                if(office != null) officeList.add(office);
            } while(resultSet.next()); //.next() đưa con trỏ resultSet đến dòng kết tiếu nếu tồn tại trả về true, còn không thì false
        }
        return officeList;
    }

    @Override
    public Office findById(String id) throws SQLException {
        Office office = null;
        String sql = "CALL get_office_by_id(?)";
        PreparedStatement preparedStatement = myConnection.prepare(sql);
        preparedStatement.setString(1, id); // dùng để set giá trị vào index chấm hỏi tương ứng từ 1
        ResultSet resultSet =  preparedStatement.executeQuery();
        if(resultSet.first()) {
            office = getObject(resultSet);
        }
        return office;
    }

    @Override
    public List<Office> search(String text) throws SQLException {
        List<Office> officeList = new ArrayList<>();
        String sql = "CALL search_office(?)"; //câu lệnh call procedure
        PreparedStatement preparedStatement = myConnection.prepare(sql); // lấy ra prepare dùng cho câu lệnh query
        preparedStatement.setString(1, text);
        ResultSet resultSet = preparedStatement.executeQuery(); // thực thi câu lệnh query và lấy resultSet trả về

        //resetSet.first() để đưa con trỏ resetSet về bản ghi đầu tiên lấy được nếu tồn tại trả về true, còn không thì false
        if(resultSet.first()) {
            do {
                Office office = getObject(resultSet);
                if(office != null) officeList.add(office);
            } while(resultSet.next()); //.next() đưa con trỏ resultSet đến dòng kết tiếu nếu tồn tại trả về true, còn không thì false
        }
        return officeList;
    }

    @Override
    public Office insert(Office office) throws SQLException {
        Office newOffice = null;
        String sql = "CALL insert_office(?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql); // lấy ra prepare dùng để thực thi câu lệnh update
        preparedStatement.setString(1, office.getOfficeCode());
        preparedStatement.setString(2, office.getCity());
        preparedStatement.setString(3, office.getPhone());
        preparedStatement.setString(4, office.getAddressLine1());
        preparedStatement.setString(5, office.getAddressLine2());
        preparedStatement.setString(6, office.getState());
        preparedStatement.setString(7, office.getCountry());
        preparedStatement.setString(8, office.getPostalCode());
        preparedStatement.setString(9, office.getTerritory());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) { //nếu insert thành công thì thực hiện lấy id vừa insert
            ResultSet resultSet = preparedStatement.getGeneratedKeys(); // hàm dùng để lấy id cho bản ghi vừa insert
            if(resultSet.first()) { // nếu có
                newOffice = findById((String) resultSet.getString(1)); // lấy id bằng cách resultSet.getLong(1) kiểu trả về cột 1 của reusltSet là long,
                // sau đó dùng hàm findById((int) key) vì key kiểu long nên ép kiểu về int để tìm lại bản ghi vừa insert;
            }
        }
        return newOffice;
    }

    @Override
    public boolean update(Office office) throws SQLException {
        boolean result = false;
        String sql = "CALL update_office(?,?,?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, office.getOfficeCode());
        preparedStatement.setString(2, office.getCity());
        preparedStatement.setString(3, office.getPhone());
        preparedStatement.setString(4, office.getAddressLine1());
        preparedStatement.setString(5, office.getAddressLine2());
        preparedStatement.setString(6, office.getState());
        preparedStatement.setString(7, office.getCountry());
        preparedStatement.setString(8, office.getPostalCode());
        preparedStatement.setString(9, office.getTerritory());
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }

    @Override
    public boolean delete(String id) throws SQLException {
        boolean result = false;
        String sql = "CALL delete_office(?)";
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql);
        preparedStatement.setString(1, id);
        int rs = preparedStatement.executeUpdate();
        if(rs > 0) result = true;
        return result;
    }

    @Override
    public int getOfficeTotalRows() throws SQLException {
        String sql = "CALL get_total_row_of_office()"; //câu lệnh call procedure
        PreparedStatement preparedStatement = myConnection.prepareUpdate(sql); // lấy ra prepare dùng cho câu lệnh query
        int rs = preparedStatement.executeUpdate(); // thực thi câu lệnh query và lấy resultSet trả về
        System.out.println(rs);
        //resetSet.first() để đưa con trỏ resetSet về bản ghi đầu tiên lấy được nếu tồn tại trả về true, còn không thì false

        return rs;
    }

    @Override
    public List<Office> getOfficeFromTo(int startPage, int itemPerPage) throws SQLException {
        List<Office> officeList = new ArrayList<>();
        String sql = "CALL get_office_from_to(?,?)"; //câu lệnh call procedure
        PreparedStatement preparedStatement = myConnection.prepare(sql); // lấy ra prepare dùng cho câu lệnh query
        preparedStatement.setInt(1, startPage);
        preparedStatement.setInt(2, itemPerPage);
        ResultSet resultSet = preparedStatement.executeQuery(); // thực thi câu lệnh query và lấy resultSet trả về

        //resetSet.first() để đưa con trỏ resetSet về bản ghi đầu tiên lấy được nếu tồn tại trả về true, còn không thì false
        if(resultSet.first()) {
            do {
                Office office = getObject(resultSet);
                if(office != null) officeList.add(office);
            } while(resultSet.next()); //.next() đưa con trỏ resultSet đến dòng kết tiếu nếu tồn tại trả về true, còn không thì false
        }
        return officeList;
    }


}
