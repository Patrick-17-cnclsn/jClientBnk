package com.jmc.mazebank.Controllers.Client;

import com.jmc.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    public BorderPane client_parent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        client_parent.setCenter(Model.getInstance().getViewFactory().getDashboardView());

        // Listener sur le menu
        Model.getInstance().getViewFactory().getClientSelectedMenuItem().addListener((observableValue, oldValue, newValue) -> {
            switch (newValue) {
                case Transactions -> client_parent.setCenter(
                        Model.getInstance().getViewFactory().getTransactionView()
                );
                case Accounts -> client_parent.setCenter(
                        Model.getInstance().getViewFactory().getAccountsView()
                );
                default -> client_parent.setCenter(
                        Model.getInstance().getViewFactory().getDashboardView()
                );
            }
        });
    }
}
