package com.jmc.mazebank.Controllers.Admin;

import com.jmc.mazebank.Models.Client;
import com.jmc.mazebank.Models.Model;
import com.jmc.mazebank.Views.DepositClientCellFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class DepositController implements Initializable {

    public Button search_btn;
    public TextField pAddress_fld;
    public TextField amount_fld;
    public Button deposit_btn;
    public ListView<Client> result_listview;
    public Label selected_client_lbl;

    private Client selectedClient = null;
    private final ObservableList<Client> searchResults = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up listeners
        search_btn.setOnAction(event -> onSearchClient());
        deposit_btn.setOnAction(event -> onDeposit());

        // Set up ListView
        result_listview.setItems(searchResults);
        result_listview.setCellFactory(param -> new DepositClientCellFactory());

        // Enable selection mode
        result_listview.getSelectionModel().setSelectionMode(javafx.scene.control.SelectionMode.SINGLE);

        // Listen for selection changes
        result_listview.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            selectedClient = newVal;
            if (newVal != null) {
                String clientInfo = newVal.firstNameProperty().get() + " " +
                                   newVal.lastNameProperty().get() + " (" +
                                   newVal.payeeAddressProperty().get() + ")";
                selected_client_lbl.setText("Selected: " + clientInfo);
                selected_client_lbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                System.out.println("Client sélectionné: " + clientInfo);
            } else {
                selected_client_lbl.setText("No client selected");
                selected_client_lbl.setStyle("-fx-text-fill: gray;");
            }
        });
    }

    private void onSearchClient() {
        String searchAddress = pAddress_fld.getText().trim();

        if (searchAddress.isEmpty()) {
            showAlert("Error", "Please enter a payee address to search.");
            return;
        }

        // Clear previous results
        searchResults.clear();

        // Load all clients if not already loaded
        if (Model.getInstance().getClients().isEmpty()) {
            Model.getInstance().setClients();
        }

        // Search for clients matching the address
        for (Client client : Model.getInstance().getClients()) {
            if (client.payeeAddressProperty().get().toLowerCase().contains(searchAddress.toLowerCase())) {
                searchResults.add(client);
            }
        }

        if (searchResults.isEmpty()) {
            showAlert("No Results", "No client found with address: " + searchAddress);
        }
    }

    private void onDeposit() {
        if (selectedClient == null) {
            showAlert("Error", "Please select a client from the search results.");
            return;
        }

        String amountStr = amount_fld.getText().trim();
        if (amountStr.isEmpty()) {
            showAlert("Error", "Please enter an amount to deposit.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                showAlert("Error", "Amount must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Invalid amount format.");
            return;
        }

        // Deposit to the client's saving account
        String clientAddress = selectedClient.payeeAddressProperty().get();
        boolean success = Model.getInstance().getDatabaseDriver().depositToSavingAccount(clientAddress, amount);

        if (success) {
            showAlert("Success", "Deposited $" + String.format("%.2f", amount) + " to " + clientAddress);
            amount_fld.clear();

            // Refresh the client list
            Model.getInstance().setClients();
            onSearchClient(); // Refresh search results
        } else {
            showAlert("Error", "Deposit failed. Client may not have a saving account.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
