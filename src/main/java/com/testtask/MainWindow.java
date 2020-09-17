package com.testtask;

import com.testtask.domain.Doctor;
import com.testtask.domain.Patient;
import com.testtask.domain.Recipe;
import com.testtask.tables.DoctorsService;
import com.testtask.tables.PatientsService;
import com.testtask.tables.RecipesService;
import com.testtask.tables.ServiceInterface;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class MainWindow extends VerticalLayout {
    DoctorsService doctorsService=new DoctorsService();
    PatientsService patientsService=new PatientsService();
    RecipesService recipesService=new RecipesService();
    private final Integer UPDATE_DOCTORS_CONSTANT=1;
    private final Integer ADD_DOCTORS_CONSTANT=2;
    private final Integer ADD_PATIENT_CONSTANT=4;
    private final Integer UPDATE_PATIENT_CONSTANT=5;
    private final Integer ADD_RECIPE_CONSTANT=7;
    private final Integer UPDATE_RECIPE_CONSTANT=8;
public String [] getSelectedRow(Grid grid)
{
    Object selectRow = grid.getSelectedRow();
    selectRow = grid.getContainerDataSource().getItem(selectRow);
    String[] str =null;
    if(selectRow!=null)
       str=selectRow.toString().split(" ");
    return str;
}
    public Window getRedactionWindow(ServiceInterface service, Grid grid,Integer mode) throws SQLException {
        Window window=new Window();
        if(mode==ADD_DOCTORS_CONSTANT)
            window.setContent(new CRUDWindow(window,(DoctorsService)service));
        if(mode==UPDATE_DOCTORS_CONSTANT)
            window.setContent(new CRUDWindow(window,(DoctorsService)service,getSelectedRow(grid)));
        if(mode==ADD_PATIENT_CONSTANT)
            window.setContent(new CRUDWindow(window,(PatientsService)service));
        if(mode==UPDATE_PATIENT_CONSTANT)
            window.setContent(new CRUDWindow(window,(PatientsService)service,getSelectedRow(grid)));
        if(mode==ADD_RECIPE_CONSTANT)
            window.setContent(new CRUDWindow(window,(RecipesService)service,patientsService,doctorsService));
        if(mode==UPDATE_RECIPE_CONSTANT)
            window.setContent(new CRUDWindow(window,(RecipesService)service,patientsService,doctorsService,getSelectedRow(grid)));

        window.center();
        window.setModal(true);
        window.setHeight("200px");
        window.setWidth("1200px");
        UI.getCurrent().addWindow(window);
        return window;
    }
    public MainWindow() throws SQLException {
        GridLayout grid = new GridLayout(7, 10);
        grid.setSpacing(true);
        grid.addComponent(new Label("Doctors"), 0, 0);
        grid.addComponent(new Label("Patients"), 1, 0);
        grid.addComponent(new Label("Recipes"),2,0);


        List<Doctor> doctorsList = doctorsService.findAll();
        List<Patient> patientsList = patientsService.findAll();
        List<Recipe> recipeList=recipesService.findAll();

        Grid doctorsGrid = new Grid(); //doctors
        doctorsGrid.addColumn("ID");
        doctorsGrid.addColumn("FIRSTNAME");
        doctorsGrid.addColumn("LASTNAME");
        doctorsGrid.addColumn("MIDDLENAME");
        doctorsGrid.addColumn("SPECIALIZATION");
        doctorsGrid.addColumn("COUNTRECIPES");
        Button addDoctorButton = new Button("Add Doctor");
        Button updateDoctorButton = new Button("Update Doctor");
        Button deleteDoctorButton = new Button("Delete Doctor");
        String [] selectedRow=null;
        doctorsList.forEach(doctors -> doctorsGrid.addRow(String.valueOf(doctors.getId()),doctors.getFirstName(), doctors.getLastName(),
                doctors.getMiddleName(),doctors.getSpecialization(),String.valueOf(doctors.getCountOfRecipes())));
addDoctorButton.addClickListener(new Button.ClickListener() {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {

        try {
            getRedactionWindow(doctorsService,doctorsGrid,ADD_DOCTORS_CONSTANT);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
});
updateDoctorButton.addClickListener(new Button.ClickListener() {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if(getSelectedRow(doctorsGrid)!=null) {
            try {
                getRedactionWindow(doctorsService,doctorsGrid,UPDATE_DOCTORS_CONSTANT);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
});
deleteDoctorButton.addClickListener(new Button.ClickListener() {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        String[] selectedRow=getSelectedRow(doctorsGrid);
        if(selectedRow!=null) {
            try {
                doctorsService.delete(Long.parseLong(selectedRow[0]));
                UI.getCurrent().setContent(new MainWindow());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
});

        Grid patientsGrid = new Grid(); //PATIENTS
        patientsGrid.addColumn("ID");
        patientsGrid.addColumn("FIRSTNAME");
        patientsGrid.addColumn("LASTNAME");
        patientsGrid.addColumn("MIDDLENAME");
        patientsGrid.addColumn("PHONENUMBER");
        Button addPatientButton = new Button("Add Patient");
        Button updatePatientButton = new Button("Update Patient");
        Button deletePatientButton = new Button("Delete Patient");
        patientsList.forEach(patients -> patientsGrid.addRow(String.valueOf(patients.getId()),patients.getFirstName(),patients.getLastName(),
                patients.getMiddleName(),patients.getPhoneNumber()));
        addPatientButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                try {
                    getRedactionWindow(patientsService,patientsGrid,ADD_PATIENT_CONSTANT);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        updatePatientButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if(getSelectedRow(patientsGrid)!=null) {
                    try {
                        getRedactionWindow(patientsService,patientsGrid,UPDATE_PATIENT_CONSTANT);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        deletePatientButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String[] selectedRow=getSelectedRow(patientsGrid);
                if(selectedRow!=null) {
                    try {
                        patientsService.delete(Long.parseLong(selectedRow[0]));
                        UI.getCurrent().setContent(new MainWindow());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        Grid recipesGrid= new Grid();//RECIPES
        recipesGrid.addColumn("ID");
        recipesGrid.addColumn("DESCRIPTION");
        recipesGrid.addColumn("CREATION-DATE");
        recipesGrid.addColumn("PATIENT");
        recipesGrid.addColumn("DURATION");
        recipesGrid.addColumn("PRIORITY");
        recipesGrid.addColumn("DOCTOR");

        setRecipeGrid(recipeList,recipesGrid);
        Button addRecipeButton = new Button("Add Recipe");
        Button updateRecipeButton = new Button("Update Recipe");
        Button deleteRecipeButton = new Button("Delete Recipe");
        ComboBox patientCriteriaCB=new ComboBox("Patient");
        TextArea descriptionCriteria=new TextArea();
        ComboBox priorityCriteriaCB=new ComboBox("Priority");
        for(Patient p:patientsList) {
            patientCriteriaCB.addItem(p);
            patientCriteriaCB.setItemCaption(p, p.getLastName()+" "+p.getFirstName()+" "+p.getMiddleName());
        }
        priorityCriteriaCB.addItem(Database.normalPriority);
        priorityCriteriaCB.addItem(Database.citoPriority);
        priorityCriteriaCB.addItem(Database.statimPriority);
        Button criteriaButton=new Button("Show");

        criteriaButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                recipesGrid.getContainerDataSource().removeAllItems();
                HashMap<String,String> criteriaMap=new HashMap<>();
                if(patientCriteriaCB.getValue()!=null)
                    criteriaMap.put(RecipesService.selectCriteriaPatient,String.valueOf(((Patient)patientCriteriaCB.getValue()).getId()));
                if(priorityCriteriaCB.getValue()!=null)
                    criteriaMap.put(RecipesService.selectCriteriaPriority,(String)priorityCriteriaCB.getValue());
                if(!descriptionCriteria.isEmpty())
                    criteriaMap.put(RecipesService.selectCriteriaDescription,descriptionCriteria.getValue());
                try {
                  setRecipeGrid(recipesService.criteriaSelect(criteriaMap),recipesGrid);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        addRecipeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                try {
                    getRedactionWindow(recipesService,recipesGrid,ADD_RECIPE_CONSTANT);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        updateRecipeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

                try {
                    if(getSelectedRow(recipesGrid)!=null) {
                        getRedactionWindow(recipesService, recipesGrid, UPDATE_RECIPE_CONSTANT);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        deleteRecipeButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                String[] selectedRow=getSelectedRow(recipesGrid);
                if(selectedRow!=null) {
                    try {
                        recipesService.delete(Long.parseLong(selectedRow[0]));
                        UI.getCurrent().setContent(new MainWindow());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        grid.addComponent(doctorsGrid, 0, 1);
        doctorsGrid.setWidth("700px");
        grid.addComponent(patientsGrid, 1, 1);
        grid.addComponent(recipesGrid,2,1);

        grid.addComponent(addDoctorButton,0,2);
        grid.addComponent(updateDoctorButton,0,3);
        grid.addComponent(deleteDoctorButton,0,4);

        grid.addComponent(addPatientButton,1,2);
        grid.addComponent(updatePatientButton,1,3);
        grid.addComponent(deletePatientButton,1,4);

        grid.addComponent(addRecipeButton,2,2);
        grid.addComponent(updateRecipeButton,2,3);
        grid.addComponent(deleteRecipeButton,2,4);
        grid.addComponent(patientCriteriaCB,2,5);
        grid.addComponent(priorityCriteriaCB,2,6);
        grid.addComponent(descriptionCriteria,2,7);
        grid.addComponent(criteriaButton,2,8);
        grid.setImmediate(true);
       addComponent(grid);
      setImmediate(true);
        setComponentAlignment(grid, Alignment.TOP_LEFT);
    }
    private void setRecipeGrid(List<Recipe> recipeList,Grid recipesGrid)
    {
        for(Recipe recipes:recipeList) {
            String doctor;
            String patient;
            if(recipes.getDoctor()==null)
                doctor="empty";
            else
                doctor= recipes.getDoctor().getLastName() + recipes.getDoctor().getFirstName() + recipes.getDoctor().getMiddleName();
            if(recipes.getPatient()==null)
                patient="empty";
            else
                patient= recipes.getPatient().getLastName() + recipes.getPatient().getFirstName() + recipes.getPatient().getMiddleName();
            recipesGrid.addRow(String.valueOf(recipes.getId()), recipes.getDescription(), recipes.getCreationDate().toString(),patient
                    , String.valueOf(recipes.getDuration()), recipes.getPriority(),doctor);
        }
    }
    }

