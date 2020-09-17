package com.testtask;

import com.testtask.domain.Doctor;
import com.testtask.domain.Entity;
import com.testtask.domain.Patient;
import com.testtask.domain.Recipe;
import com.testtask.tables.DoctorsService;
import com.testtask.tables.PatientsService;
import com.testtask.tables.RecipesService;
import com.testtask.tables.ServiceInterface;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.ComboBox;

import java.sql.Date;
import java.sql.SQLException;

public class CRUDWindow extends VerticalLayout {
    RegexpValidator numberValidator = new RegexpValidator("^[0-9+()-]{1,20}$", "invalid input");
    RegexpValidator stringValidator = new RegexpValidator("^[А-ЯЁа-яёA-Za-z]{1,20}$", "invalid input");
public Long newIndex(ServiceInterface service) throws SQLException {
    Long max=0l;
    for(Object entity:service.findAll())
    {
        Long currentId=((Entity)entity).getId();
        if(currentId>max)
            max=currentId;
    }
    return max+1;
}

    public CRUDWindow(Window redactionWindow, DoctorsService service) {
        //insert into groups
        GridLayout grid = new GridLayout(4, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField firstNameTextField = new TextField("FIRSTNAME");
        firstNameTextField.addValidator(stringValidator);
        TextField lastNameTextField = new TextField("LASTNAME");
        lastNameTextField.addValidator(stringValidator);
        TextField middleNameTextField = new TextField("MIDDLENAME");
        middleNameTextField.addValidator(stringValidator);
        TextField specializationTextField = new TextField("SPECIALIZATION");
        specializationTextField.addValidator(stringValidator);
        Button acceptButton = new Button("OK");
        acceptButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if(firstNameTextField.isValid()&&lastNameTextField.isValid()&&middleNameTextField.isValid()&&
                specializationTextField.isValid())
                {
                try {
                    Doctor doctor = new Doctor(newIndex(service), firstNameTextField.getValue(), lastNameTextField.getValue(), middleNameTextField.getValue(),
                            specializationTextField.getValue(), 0);

                    service.insert(doctor);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                firstNameTextField.clear();
                lastNameTextField.clear();
                middleNameTextField.clear();
                specializationTextField.clear();
                try {
                    UI.getCurrent().setContent(new MainWindow()); //for refresh main layout with grids
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                redactionWindow.close();
            }
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent(firstNameTextField, 0, 0);
        grid.addComponent(lastNameTextField, 1, 0);
        grid.addComponent(middleNameTextField,2,0);
        grid.addComponent(specializationTextField,3,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }
    public CRUDWindow(Window redactionWindow, DoctorsService service,String[] selectRow) {

        GridLayout grid = new GridLayout(4, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField firstNameTextField = new TextField("FIRSTNAME");
        firstNameTextField.addValidator(stringValidator);
        TextField lastNameTextField = new TextField("LASTNAME");
        lastNameTextField.addValidator(stringValidator);
        TextField middleNameTextField = new TextField("MIDDLENAME");
        middleNameTextField.addValidator(stringValidator);
        TextField specializationTextField = new TextField("SPECIALIZATION");
        specializationTextField.addValidator(stringValidator);
        firstNameTextField.setValue(selectRow[1]);
        lastNameTextField.setValue(selectRow[2]);
        middleNameTextField.setValue(selectRow[3]);
        specializationTextField.setValue(selectRow[4]);
        Button acceptButton = new Button("Update");
        acceptButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (firstNameTextField.isValid() && lastNameTextField.isValid() && middleNameTextField.isValid() &&
                        specializationTextField.isValid()) {
                    try {
                        Doctor doctor = new Doctor(Long.parseLong(selectRow[0]), firstNameTextField.getValue(), lastNameTextField.getValue(), middleNameTextField.getValue(),
                                specializationTextField.getValue(), Integer.parseInt(selectRow[5]));
                        System.out.println(doctor.getMiddleName());
                        service.update(doctor);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    middleNameTextField.clear();
                    specializationTextField.clear();
                    try {
                        UI.getCurrent().setContent(new MainWindow()); //for refresh main layout with grids
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redactionWindow.close();
                }
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent(firstNameTextField, 0, 0);
        grid.addComponent(lastNameTextField, 1, 0);
        grid.addComponent(middleNameTextField,2,0);
        grid.addComponent(specializationTextField,3,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }
    public CRUDWindow(Window redactionWindow, PatientsService service) {
        //insert into groups
        GridLayout grid = new GridLayout(4, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField firstNameTextField = new TextField("FIRSTNAME");
        firstNameTextField.addValidator(stringValidator);
        TextField lastNameTextField = new TextField("LASTNAME");
        lastNameTextField.addValidator(stringValidator);
        TextField middleNameTextField = new TextField("MIDDLENAME");
        middleNameTextField.addValidator(stringValidator);
        TextField phoneNumberTextField = new TextField("PHONENUMBER");
        phoneNumberTextField.addValidator(numberValidator);
        Button acceptButton = new Button("OK");
        acceptButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (firstNameTextField.isValid() && lastNameTextField.isValid() && middleNameTextField.isValid() &&
                        phoneNumberTextField.isValid()) {
                    try {
                        Patient patient = new Patient(newIndex(service), firstNameTextField.getValue(), lastNameTextField.getValue(), middleNameTextField.getValue(),
                                phoneNumberTextField.getValue());

                        service.insert(patient);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    firstNameTextField.clear();
                    lastNameTextField.clear();
                    middleNameTextField.clear();
                    phoneNumberTextField.clear();
                    try {
                        UI.getCurrent().setContent(new MainWindow()); //for refresh main layout with grids
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redactionWindow.close();
                }
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent(firstNameTextField, 0, 0);
        grid.addComponent(lastNameTextField, 1, 0);
        grid.addComponent(middleNameTextField,2,0);
        grid.addComponent(phoneNumberTextField,3,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }
    public CRUDWindow(Window redactionWindow, PatientsService service, String[] selectRow) {
        //insert into groups
        GridLayout grid = new GridLayout(4, 6);
        grid.setSpacing(true);
        grid.setSizeFull();
        TextField firstNameTextField = new TextField("FIRSTNAME");
        firstNameTextField.addValidator(stringValidator);
        TextField lastNameTextField = new TextField("LASTNAME");
        lastNameTextField.addValidator(stringValidator);
        TextField middleNameTextField = new TextField("MIDDLENAME");
        middleNameTextField.addValidator(stringValidator);
        TextField phoneNumberTextField = new TextField("PHONENUMBER");
        phoneNumberTextField.addValidator(numberValidator);
        firstNameTextField.setValue(selectRow[1]);
        lastNameTextField.setValue(selectRow[2]);
        middleNameTextField.setValue(selectRow[3]);
        phoneNumberTextField.setValue(selectRow[4]);
        Button acceptButton = new Button("Update");
        acceptButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                if (firstNameTextField.isValid() && lastNameTextField.isValid() && middleNameTextField.isValid() &&
                        phoneNumberTextField.isValid()) {
                    try {
                        Patient patient = new Patient(Long.parseLong(selectRow[0]), firstNameTextField.getValue(), lastNameTextField.getValue(), middleNameTextField.getValue(),
                                phoneNumberTextField.getValue());

                        service.update(patient);

                        firstNameTextField.clear();
                        lastNameTextField.clear();
                        middleNameTextField.clear();
                        phoneNumberTextField.clear();

                        UI.getCurrent().setContent(new MainWindow()); //for refresh main layout with grids
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redactionWindow.close();
                }
            }
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent(firstNameTextField, 0, 0);
        grid.addComponent(lastNameTextField, 1, 0);
        grid.addComponent(middleNameTextField,2,0);
        grid.addComponent(phoneNumberTextField,3,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }
    public CRUDWindow(Window redactionWindow, RecipesService service,PatientsService patientsService,DoctorsService doctorsService) throws SQLException {

        GridLayout grid = new GridLayout(8, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField descriptionTextField = new TextField("DESCRIPTION");
        DateField dateField= new DateField("CREATIONDATE");
        ComboBox comboBoxPatient = new ComboBox("PATIENT");
        TextField durationTextField = new TextField("DURATION");
        durationTextField.addValidator(numberValidator);
        ComboBox comboBoxPriority = new  ComboBox("PRIORITY");
        ComboBox comboBoxDoctor = new  ComboBox("DOCTOR");
        Button acceptButton = new Button("OK");
        for(Patient p:patientsService.findAll()) {
            comboBoxPatient.addItem(p);
            comboBoxPatient.setItemCaption(p, p.getLastName()+" "+p.getFirstName()+" "+p.getMiddleName());
        }


        comboBoxPriority.addItem(Database.normalPriority);
        comboBoxPriority.addItem(Database.citoPriority);
        comboBoxPriority.addItem(Database.statimPriority);
        for(Doctor d:doctorsService.findAll())
        {
            comboBoxDoctor.addItem(d);
            comboBoxDoctor.setItemCaption(d,d.getLastName()+" "+d.getFirstName()+" "+d.getMiddleName());


        }
        Button cancelButton = new Button("Cancel");
        acceptButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (durationTextField.isValid()) {
                    try {
                        Recipe recipe = new Recipe(newIndex(service), descriptionTextField.getValue(), (Doctor) comboBoxDoctor.getValue(), (Patient) comboBoxPatient.getValue(),
                                dateField.getValue(), Long.parseLong(durationTextField.getValue()), (String) comboBoxPriority.getValue());
                        service.insert(recipe);
                        UI.getCurrent().setContent(new MainWindow());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redactionWindow.close();
                }
            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent( descriptionTextField, 0, 0);
        grid.addComponent(dateField, 1, 0);
        grid.addComponent(durationTextField,2,0);
        grid.addComponent(comboBoxPriority,3,0);
                grid.addComponent(comboBoxDoctor ,4,0);
                grid.addComponent(comboBoxPatient,5,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }
    public CRUDWindow(Window redactionWindow, RecipesService service,PatientsService patientsService,DoctorsService doctorsService,String[] selectedRow) throws SQLException {

        GridLayout grid = new GridLayout(8, 6);
        grid.setSpacing(true);
        grid.setSizeFull();

        TextField descriptionTextField = new TextField("DESCRIPTION");
        DateField dateField= new DateField("CREATIONDATE");
        ComboBox comboBoxPatient = new ComboBox("PATIENT");
        TextField durationTextField = new TextField("DURATION");
        durationTextField.addValidator(numberValidator);
        ComboBox comboBoxPriority = new  ComboBox("PRIORITY");
        ComboBox comboBoxDoctor = new  ComboBox("DOCTOR");
        Button acceptButton = new Button("Update");
        for(Patient p:patientsService.findAll()) {
            comboBoxPatient.addItem(p);
            comboBoxPatient.setItemCaption(p, p.getLastName()+" "+p.getFirstName()+" "+p.getMiddleName());
        }


        comboBoxPriority.addItem(Database.normalPriority);
        comboBoxPriority.addItem(Database.citoPriority);
        comboBoxPriority.addItem(Database.statimPriority);
        for(Doctor d:doctorsService.findAll())
        {
            comboBoxDoctor.addItem(d);
            comboBoxDoctor.setItemCaption(d,d.getLastName()+" "+d.getFirstName()+" "+d.getMiddleName());


        }
        comboBoxPatient.select(selectedRow[3]);
        descriptionTextField.setValue(selectedRow[1]);
        comboBoxDoctor.select(selectedRow[6]);
        durationTextField.setValue(selectedRow[4]);
        comboBoxPriority.select(selectedRow[5]);

        Button cancelButton = new Button("Cancel");
        acceptButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (durationTextField.isValid()) {
                    try {
                        Recipe recipe = new Recipe(Long.parseLong(selectedRow[0]), descriptionTextField.getValue(), (Doctor) comboBoxDoctor.getValue(), (Patient) comboBoxPatient.getValue(),
                                dateField.getValue(), Long.parseLong(durationTextField.getValue()), (String) comboBoxPriority.getValue());
                        service.update(recipe);
                        UI.getCurrent().setContent(new MainWindow());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    redactionWindow.close();
                }
            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                redactionWindow.close();
            }
        });
        grid.addComponent( descriptionTextField, 0, 0);
        grid.addComponent(dateField, 1, 0);
        grid.addComponent(durationTextField,2,0);
        grid.addComponent(comboBoxPriority,3,0);
        grid.addComponent(comboBoxDoctor ,4,0);
        grid.addComponent(comboBoxPatient,5,0);
        grid.addComponent(acceptButton, 0, 1);
        grid.addComponent(cancelButton, 1, 1);

        addComponent(grid);

    }

}

