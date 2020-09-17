package com.testtask.tables;

import com.testtask.Database;
import com.testtask.domain.Doctor;
import com.testtask.domain.Patient;
import com.testtask.domain.Recipe;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesService implements ServiceInterface<Recipe> {
    private Connection connect;
    public static final String selectCriteriaPatient="PATIENT_ID=?";
    public static final String selectCriteriaDescription="DESCRIPTION=?";
    public static final String selectCriteriaPriority="PRIORITY=?";
    public RecipesService() throws SQLException {
        connect= Database.getConnection();
    }
    @Override
    public int insert(Recipe newInstance) throws SQLException {
        String sql="INSERT INTO \"PUBLIC\".\"RECIPES\"\n" +
                "( \"RECIPE_ID\", \"DOCTOR_ID\", \"PATIENT_ID\", \"CREATION_DATE\", \"DURATION\", \"DESCRIPTION\", \"PRIORITY\" )\n" +
                "VALUES ( ?,? ,? ,? ,? , ?, ?)";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1, newInstance.getId());
        if(newInstance.getDoctor()!=null)
        preparedStatement.setLong(2, newInstance.getDoctor().getId());
        else
            preparedStatement.setNull(2, Types.BIGINT);
            if(newInstance.getPatient()!=null)
        preparedStatement.setLong(3, newInstance.getPatient().getId());
            else
                preparedStatement.setNull(3, Types.BIGINT);
        preparedStatement.setDate(4, new java.sql.Date(newInstance.getCreationDate().getTime()));
        preparedStatement.setLong(5, newInstance.getDuration());
        preparedStatement.setString(6,newInstance.getDescription());
        preparedStatement.setString(7,newInstance.getPriority());
        return preparedStatement.executeUpdate();
    }

    @Override
    public int delete(Long id) throws SQLException {
        String sql="DELETE FROM RECIPES\n" +
                "\tWHERE recipe_id=?;";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        preparedStatement.setLong(1,id);
        return preparedStatement.executeUpdate();
    }
public List<Recipe> criteriaSelect(HashMap<String,String> criteriaMap) throws SQLException {
    PreparedStatement preparedStatement ;
    String selectSql="SELECT * FROM \"PUBLIC\".\"RECIPES\"\n";
    if(criteriaMap==null||criteriaMap.isEmpty())
        preparedStatement = connect.prepareStatement(selectSql);
    else{
        selectSql+="WHERE ";
        for(Map.Entry entry:criteriaMap.entrySet())
        {
            selectSql+=entry.getKey()+" AND ";
        }
        int position=selectSql.length();
        selectSql=selectSql.substring(0, position-4);
        selectSql+=";";
        preparedStatement = connect.prepareStatement(selectSql);
        int columnIndex=1;
        for(Map.Entry entry:criteriaMap.entrySet())
        {

            if(entry.getKey()==selectCriteriaPatient)
                preparedStatement.setLong(columnIndex,Long.parseLong((String)entry.getValue()));
            else
                preparedStatement.setString(columnIndex,(String)entry.getValue());
            columnIndex++;

        }

    }
    return resultListRecipes(preparedStatement.executeQuery());

}
private List<Recipe> resultListRecipes(ResultSet resultSet) throws SQLException {
    List<Recipe> recipeList=new ArrayList<>();
    List<Doctor> doctorList=new DoctorsService().findAll();
    List<Patient> patientList=new PatientsService().findAll();
    while(resultSet.next()) {
        Recipe recipe=new Recipe();
        recipe.setPriority(resultSet.getString("priority"));
        Long doctorId=resultSet.getLong("doctor_id");
        Long patientId=resultSet.getLong("patient_id");
        Doctor doctor = null;
        Patient patient=null;

        for(Doctor d:doctorList)
            if(d.getId()==doctorId)
                doctor=d;

        for(Patient p:patientList)
            if(p.getId()==patientId)
                patient=p;


        recipe.setId(resultSet.getLong("recipe_id"));
        recipe.setCreationDate(resultSet.getDate("creation_date"));
        recipe.setDescription(resultSet.getString("description"));
        if(doctor!=null)
            recipe.setDoctor(doctor);
        if(patient!=null)
            recipe.setPatient(patient);
        recipe.setDuration(resultSet.getLong("duration"));
        recipeList.add(recipe);
    }
    return recipeList;
}
    @Override
    public List<Recipe> findAll() throws SQLException {
        String sql="SELECT * FROM \"PUBLIC\".\"RECIPES\"";
        PreparedStatement preparedStatement = connect.prepareStatement(sql);
        ResultSet resultSet=preparedStatement.executeQuery();
        return resultListRecipes(resultSet);
    }

    @Override
    public int update(Recipe newInstance) throws SQLException {
        String sql="UPDATE \"PUBLIC\".\"RECIPES\" SET DOCTOR_ID=?, PATIENT_ID=?, " +
                "CREATION_DATE=?, DURATION=?, DESCRIPTION=?, PRIORITY=? WHERE RECIPE_ID=?";
        PreparedStatement preparedStatement=connect.prepareStatement(sql);
        preparedStatement.setLong(1,newInstance.getDoctor().getId());
        preparedStatement.setLong(2,newInstance.getPatient().getId());
        preparedStatement.setDate(3,new java.sql.Date(newInstance.getCreationDate().getTime()));
        preparedStatement.setLong(4,newInstance.getDuration());
        preparedStatement.setString(5,newInstance.getDescription());
        preparedStatement.setString(6,newInstance.getPriority());
        preparedStatement.setLong(7,newInstance.getId());
        return preparedStatement.executeUpdate();

    }
}
