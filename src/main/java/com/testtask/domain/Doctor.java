package com.testtask.domain;

public class Doctor implements Entity {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String specialization;
    private int countOfRecipes;

    public Doctor(Long id, String firstName, String lastName, String middleName, String specialization,int countOfRecipes) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.specialization = specialization;
        this.countOfRecipes=countOfRecipes;
    }

    public int getCountOfRecipes() {
        return countOfRecipes;
    }

    public void setCountOfRecipes(int countOfRecipes) {
        this.countOfRecipes = countOfRecipes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
