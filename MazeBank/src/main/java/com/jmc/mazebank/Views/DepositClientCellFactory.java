package com.jmc.mazebank.Views;

import com.jmc.mazebank.Controllers.Admin.ClientCellController;
import com.jmc.mazebank.Models.Client;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;

public class DepositClientCellFactory extends ListCell<Client> {

    @Override
    public void updateItem(Client client, boolean empty) {
        super.updateItem(client, empty);
        if (empty || client == null) {
            setText(null);
            setGraphic(null);
        } else {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/Admin/ClientCell.fxml"));
           ClientCellController controller = new ClientCellController(client);
           loader.setController(controller);
           setText(null);
           try {
               setGraphic(loader.load());
               // Hide the delete button in deposit view
               if (controller.delete_btn != null) {
                   controller.delete_btn.setVisible(false);
                   controller.delete_btn.setManaged(false);
               }
           } catch (Exception e) {
               e.printStackTrace();
           }
        }
    }

}

