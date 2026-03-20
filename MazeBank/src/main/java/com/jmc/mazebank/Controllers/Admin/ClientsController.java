package com.jmc.mazebank.Controllers.Admin;

import com.jmc.mazebank.Models.Client;
import com.jmc.mazebank.Models.Model;
import com.jmc.mazebank.Views.ClientCellFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ClientsController implements Initializable {
    @FXML
    public ListView<Client> clients_listview;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        iniData();
        clients_listview.setCellFactory(param -> new ClientCellFactory());
    }

    private void iniData() {
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }
        clients_listview.setItems(Model.getInstance().getClients());
    }

}

