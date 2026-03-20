package com.jmc.mazebank;

import com.jmc.mazebank.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage)  {
        // Initialiser les données de test si la base est vide (pour projet BTS)
        Model.getInstance().getDatabaseDriver().initTestData();

        // Afficher la fenêtre de login
        Model.getInstance().getViewFactory().showLoginWindow();
    }
}
