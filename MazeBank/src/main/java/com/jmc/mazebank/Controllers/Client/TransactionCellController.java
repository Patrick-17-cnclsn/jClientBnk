package com.jmc.mazebank.Controllers.Client;

import com.jmc.mazebank.Models.Transaction;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.kordamp.ikonli.javafx.FontIcon;

import java.net.URL;
import java.util.ResourceBundle;

public class TransactionCellController implements Initializable {

    public FontIcon in_icon;
    public FontIcon out_icon;
    public Label trans_date_lbl;
    public Label sender_lbl;
    public Label receiver_lbl;
    public Label amount_label;

    private final Transaction transaction;

    public  TransactionCellController(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Bind transaction data to labels
        sender_lbl.textProperty().bind(transaction.senderProperty());
        receiver_lbl.textProperty().bind(transaction.receiverProperty());
        amount_label.textProperty().bind(transaction.amountProperty().asString("%.2f"));
        trans_date_lbl.textProperty().bind(transaction.dateProperty().asString());

        // Show appropriate icon based on transaction direction
        // You can customize this logic based on current user
        in_icon.setVisible(true);
        out_icon.setVisible(false);
    }
}
