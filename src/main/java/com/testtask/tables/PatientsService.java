package com.testtask.tables;

import com.testtask.Database;
import com.testtask.domain.Doctor;
import com.testtask.domain.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientsService implements ServiceInterface<Patient> {
    private Connection connect;
    public PatientsService() throws SQLException {
        connect= Database.getConnection();
    }
    @Override
    public int insert(Patient newInstance) throws SQLException {
        String sql="INSERT INTO \"PUBLIC\".\"PATIENTS\"\n" +
                "( \"PATIENT_ID\", \"FIRSTNAME\", \"LASTNAME\", \"MIDDLENAME\", \"PHONENUMBER\" )\n" +
                "VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1, newInstance.getId());
        preparedStatement.setString(2, newInstance.getFirstName());
        preparedStatement.setString(3, newInstance.getLastName());
        preparedStatement.setString(4, newInstance.getMiddleName());
        preparedStatement.setString(5, newInstance.getPhoneNumber());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Long id) throws SQLException {
        String recipeSql="UPDATE \"PUBLIC\".\"RECIPES\" SET PATIENT_ID=? " +
                "WHERE PATIENT_ID=?";
        PreparedStatement updateStatement=connect.prepareStatement(recipeSql);
        updateStatement.setNull(1, Types.BIGINT);
        updateStatement.setLong(2,id);
        updateStatement.executeUpdate();
        String sql="DELETE FROM PATIENTS\n" +
                "\tWHERE patient_id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        return preparedStatement.executeUpdate();
    }

    @Override
    public List<Patient> findAll() throws SQLException {
        String sql="SELECT * FROM \"PUBLIC\".\"PATIENTS\"";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        List<Patient> patientsList=new ArrayList<>();

        while(resultSet.next())
            patientsList.add(new Patient(resultSet.getLong("patient_id"),resultSet.getString("firstname"),resultSet.getString("lastname"),
                    resultSet.getString("middlename"),resultSet.getString("phonenumber")));
        return patientsList;
    }

    @Override
    public int update(Patient newInstance) throws SQLException {
        String sql="UPDATE \"PUBLIC\".\"PATIENTS\" SET FIRSTNAME=?,LASTNAME=?,MIDDLENAME=?,PHONENUMBER=? WHERE PATIENT_ID=?";
        PreparedStatement preparedStatement=connect.prepareStatement(sql);
        preparedStatement.setString(1,newInstance.getFirstName());
        preparedStatement.setString(2,newInstance.getLastName());
        preparedStatement.setString(3,newInstance.getMiddleName());
        preparedStatement.setString(4,newInstance.getPhoneNumber());
        preparedStatement.setLong(5,newInstance.getId());
        return preparedStatement.executeUpdate();
    }
}
