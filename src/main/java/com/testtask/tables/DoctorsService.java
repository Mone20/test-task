package com.testtask.tables;

import com.testtask.Database;
import com.testtask.domain.Doctor;
import com.testtask.domain.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorsService implements ServiceInterface<Doctor> {
    private Connection connect;
    public DoctorsService() throws SQLException {
        connect=Database.getConnection();
    }
    @Override
    public int insert(Doctor newInstance) throws SQLException {


        String sql="INSERT INTO \"PUBLIC\".\"DOCTORS\"\n" +
                "( \"DOCTOR_ID\", \"FIRSTNAME\", \"LASTNAME\", \"MIDDLENAME\", \"SPECIALIZATION\" )\n" +
                "VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1, newInstance.getId());
        preparedStatement.setString(2, newInstance.getFirstName());
        preparedStatement.setString(3, newInstance.getLastName());
        preparedStatement.setString(4, newInstance.getMiddleName());
        preparedStatement.setString(5, newInstance.getSpecialization());
        return preparedStatement.executeUpdate();
    }
    @Override
    public int delete(Long id) throws SQLException {
        String recipeSql="UPDATE \"PUBLIC\".\"RECIPES\" SET DOCTOR_ID=? " +
                "WHERE DOCTOR_ID=?";
        PreparedStatement updateStatement=connect.prepareStatement(recipeSql);
        updateStatement.setNull(1, Types.BIGINT);
        updateStatement.setLong(2,id);
        updateStatement.executeUpdate();
        String sql="DELETE FROM doctors\n" +
                "\tWHERE doctor_id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        return preparedStatement.executeUpdate();
    }
    @Override
    public List<Doctor> findAll() throws SQLException {
        String sql="SELECT * FROM \"PUBLIC\".\"DOCTORS\"";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Doctor> doctorList=new ArrayList<>();
        String countSql=null;
        PreparedStatement countStatement=null;
        ResultSet countResult=null;
        int countRecipes=0;
        while(resultSet.next()) {
            countRecipes=0;
            countSql="SELECT * FROM \"PUBLIC\".\"RECIPES\" WHERE DOCTOR_ID=?";
            countStatement = connect.prepareStatement(countSql);
            countStatement.setLong(1,resultSet.getLong("doctor_id"));
            countResult=countStatement.executeQuery();
           while(countResult.next()) {
                countRecipes++;
            }
            doctorList.add(new Doctor(resultSet.getLong("doctor_id"), resultSet.getString("firstname"), resultSet.getString("lastname"),
                    resultSet.getString("middlename"), resultSet.getString("specialization"),countRecipes));
        }
        return doctorList;

    }

    @Override
    public int update(Doctor newInstance) throws SQLException
    {
        String sql="UPDATE \"PUBLIC\".\"DOCTORS\" SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,SPECIALIZATION=? WHERE DOCTOR_ID=?";
        PreparedStatement preparedStatement=connect.prepareStatement(sql);
        preparedStatement.setString(1,newInstance.getFirstName());
        preparedStatement.setString(2,newInstance.getLastName());
        preparedStatement.setString(3,newInstance.getMiddleName());
        preparedStatement.setString(4,newInstance.getSpecialization());
        preparedStatement.setLong(5,newInstance.getId());
        return preparedStatement.executeUpdate();

    }
    public List<Doctor> findWithCriteria()
    {
return null;
    }

}
