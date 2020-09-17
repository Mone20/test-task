package com.testtask;

import com.testtask.domain.Doctor;
import com.testtask.domain.Patient;
import com.testtask.domain.Recipe;
import com.testtask.tables.DoctorsService;
import com.testtask.tables.PatientsService;
import com.testtask.tables.RecipesService;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Container;
import com.vaadin.event.Action;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import javax.print.Doc;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {


    public MainUI() throws SQLException {
    }

    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout verticalLayout = new VerticalLayout();
        setContent(verticalLayout);
        try {
            verticalLayout.addComponent(new MainWindow());
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}