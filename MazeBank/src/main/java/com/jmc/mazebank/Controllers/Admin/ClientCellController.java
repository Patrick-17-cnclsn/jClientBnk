package com.jmc.mazebank.Controllers.Admin;

import com.jmc.mazebank.Models.Client;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ClientCellController implements Initializable {
    public Label fName_lbl;
    public Label lName_lbl;
    public Label pAddress_lbl;
    public Label ch_acc_lbl;
    public Label sv_acc_lbl;
    public Label date_lbl;
    public Button delete_btn;

    private final Client client;

    public ClientCellController(Client client) {
        this.client = client;

    }

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        fName_lbl.textProperty().bind(client.firstNameProperty());
        lName_lbl.textProperty().bind(client.lastNameProperty());
        pAddress_lbl.textProperty().bind(client.payeeAddressProperty());
        date_lbl.textProperty().bind(client.dateProperty().asString());
        if (client.checkingAccountProperty().get() != null) {
            ch_acc_lbl.textProperty().bind(client.checkingAccountProperty().get().accountNumberProperty());
        }
        if (client.savingsAccountProperty().get() != null) {
            sv_acc_lbl.textProperty().bind(client.savingsAccountProperty().get().accountNumberProperty());
        }

    }


}
