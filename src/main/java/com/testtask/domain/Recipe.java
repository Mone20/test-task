package com.testtask.domain;


import java.util.Date;

public class Recipe implements Entity{
    private Long id;
    private String description;
    private Doctor doctor;
    private Patient patient;
    private Date creationDate;
    private Long duration;
    private String priority;
public Recipe()
{

}
    public Recipe(Long id, String description, Doctor doctor, Patient patient, Date creationDate, Long duration, String priority) {
        this.id = id;
        this.description = description;
        this.doctor = doctor;
        this.patient = patient;
        this.creationDate = creationDate;
        this.duration = duration;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
