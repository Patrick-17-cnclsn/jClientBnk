package com.jmc.mazebank.Controllers.Client;

import com.jmc.mazebank.Models.Model;
import com.jmc.mazebank.Models.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionsController implements Initializable {
    public TableView<Transaction> transactions_table;
    public TableColumn<Transaction, String> sender_col;
    public TableColumn<Transaction, String> receiver_col;
    public TableColumn<Transaction, Double> amount_col;
    public TableColumn<Transaction, LocalDate> date_col;
    public TableColumn<Transaction, String> message_col;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        sender_col.setCellValueFactory(new PropertyValueFactory<>("sender"));
        receiver_col.setCellValueFactory(new PropertyValueFactory<>("receiver"));
        amount_col.setCellValueFactory(new PropertyValueFactory<>("amount"));
        date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
        message_col.setCellValueFactory(new PropertyValueFactory<>("message"));

        // Load transactions for the current client
        String clientAddress = Model.getInstance().getClient().payeeAddressProperty().get();
        Model.getInstance().setTransactions(clientAddress);

        // Bind table to transactions list
        transactions_table.setItems(Model.getInstance().getTransactions());
    }
}
