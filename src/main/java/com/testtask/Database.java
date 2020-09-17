package com.testtask;


import javafx.util.Pair;
import java.sql.*;
public class Database {
    private static Connection connection;
    public static String normalPriority="normal";
    public static String citoPriority="cito";
    public static String statimPriority="statim";

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null) {
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                connection = DriverManager.getConnection("jdbc:hsqldb:file:C:\\hsqldb\\testdb;hsqldb.lock_file=false", "sa", "");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        createTables();
        return connection;
    }
    private static void createTables() throws SQLException {
        String doctorsSQL="CREATE TABLE IF NOT EXISTS doctors\n" +
                "                ( doctor_id bigint NOT NULL, \n" +
                "                  firstname varchar(50) ,\n" +
                "                 lastname varchar(50),\n" +
                "                  middlename varchar(50),\n" +
                "                 specialization varchar(50), \n" +
                "                 CONSTRAINT doctors_pk PRIMARY KEY (doctor_id)\n" +
                "                );";

        String patientsSQL="CREATE TABLE IF NOT EXISTS patients\n" +
                "                ( patient_id bigint NOT NULL, \n" +
                "                  firstname varchar(50) ,\n" +
                "                  lastname varchar(50),\n" +
                "                  middlename varchar(50),\n" +
                "                 phonenumber varchar(50), \n" +
                "                 CONSTRAINT patients_pk PRIMARY KEY (patient_id)\n" +
                "                );";
        String recipesSQL="CREATE TABLE IF NOT EXISTS recipes\n" +
                "                ( recipe_id bigint NOT NULL,\n" +
                "\t\t\t\t doctor_id bigint ,\n" +
                "\t\t\t\t patient_id bigint ,\n" +
                "                 creation_date date NOT NULL,\n" +
                "\t\t\t\t duration bigint NOT NULL,\n" +
                "                 description varchar(50),\n" +
                "\t\t\t\t priority varchar(50),\n" +
                "                 CONSTRAINT recipes_pk PRIMARY KEY (recipe_id),\n" +
                "\t\t\t\t CONSTRAINT fk_doctors\n" +
                "    FOREIGN KEY (doctor_id)\n" +
                "    REFERENCES doctors(doctor_id),\n" +
                "\t\t\t\t CONSTRAINT fk_patients\n" +
                "    FOREIGN KEY (patient_id)\n" +
                "    REFERENCES patients(patient_id),\n" +
                "                );";
        PreparedStatement preparedStatement=connection.prepareStatement(patientsSQL);
        System.out.println( preparedStatement.executeUpdate());
        preparedStatement.close();

        preparedStatement=connection.prepareStatement(doctorsSQL);
        System.out.println(preparedStatement.executeUpdate());
        preparedStatement.close();

        preparedStatement=connection.prepareStatement(recipesSQL);
        System.out.println(preparedStatement.executeUpdate());
        preparedStatement.close();


    }

}
