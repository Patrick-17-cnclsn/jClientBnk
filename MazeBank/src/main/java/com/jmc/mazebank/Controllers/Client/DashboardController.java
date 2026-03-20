package com.jmc.mazebank.Controllers.Client;

import com.jmc.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    public Text user_name;
    public Label login_date;
    public Label checking_bal;
    public Label checking_acc_num;
    public Label saving_bal;
    public Label saving_acc_num;
    public Label income_bal;
    public Label expenses_bal;
    public ListView transaction_listview;
    public TextField payee_fld;
    public TextField amount_fld;
    public TextArea message_fld;
    public Button send_money_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bindData();
        send_money_btn.setOnAction(event -> onSendMoney());
    }

    private void bindData() {
        user_name.setText("Hi, " + Model.getInstance().getClient().firstNameProperty().get());
        login_date.setText("Today, " + java.time.LocalDate.now());

        // Bind Checking Account
        if (Model.getInstance().getClient().checkingAccountProperty().get() != null) {
            var checkingAccount = Model.getInstance().getClient().checkingAccountProperty().get();
            checking_bal.textProperty().bind(checkingAccount.balanceProperty().asString("%.2f"));
            checking_acc_num.textProperty().bind(checkingAccount.accountNumberProperty());
            System.out.println("Checking Account Balance: " + checkingAccount.balanceProperty().get());
        } else {
            checking_bal.setText("N/A");
            checking_acc_num.setText("No Account");
            System.out.println("No Checking Account found");
        }

        // Bind Saving Account
        if (Model.getInstance().getClient().savingsAccountProperty().get() != null) {
            var savingAccount = Model.getInstance().getClient().savingsAccountProperty().get();
            saving_bal.textProperty().bind(savingAccount.balanceProperty().asString("%.2f"));
            saving_acc_num.textProperty().bind(savingAccount.accountNumberProperty());
            System.out.println("Saving Account Balance: " + savingAccount.balanceProperty().get());
        } else {
            saving_bal.setText("N/A");
            saving_acc_num.setText("No Account");
            System.out.println("No Saving Account found");
        }

        // Calculate and display Income/Expenses from transactions
        updateAccountSummary();
    }

    private void updateAccountSummary() {
        String pAddress = Model.getInstance().getClient().payeeAddressProperty().get();
        double income = 0.0;
        double expenses = 0.0;

        // Get all transactions for this client
        var transactions = Model.getInstance().getTransactions();
        for (var transaction : transactions) {
            if (transaction.receiverProperty().get().equals(pAddress)) {
                // Money received = income
                income += transaction.amountProperty().get();
            } else if (transaction.senderProperty().get().equals(pAddress)) {
                // Money sent = expense
                expenses += transaction.amountProperty().get();
            }
        }

        // Update labels
        income_bal.setText(String.format("+$%.2f", income));
        expenses_bal.setText(String.format("-$%.2f", expenses));
    }

    private void onSendMoney() {
        String receiver = payee_fld.getText();
        String amountStr = amount_fld.getText();
        String message = message_fld.getText();

        // Validation
        if (receiver.isEmpty() || amountStr.isEmpty()) {
            showAlert("Error", "Please fill in all required fields (Payee and Amount).");
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

        // Check if sender has enough balance
        double currentBalance = Model.getInstance().getClient().checkingAccountProperty().get().balanceProperty().get();
        if (currentBalance < amount) {
            showAlert("Error", "Insufficient balance in checking account.");
            return;
        }

        // Send money
        String senderAddress = Model.getInstance().getClient().payeeAddressProperty().get();
        boolean success = Model.getInstance().getDatabaseDriver().sendMoney(senderAddress, receiver, amount, message);

        if (success) {
            showAlert("Success", "Money sent successfully!");
            // Clear fields
            payee_fld.clear();
            amount_fld.clear();
            message_fld.clear();

            // Refresh account balances by reloading from database
            refreshAccounts();

            // Refresh account summary (income/expenses)
            updateAccountSummary();
        } else {
            showAlert("Error", "Transaction failed. Please check the receiver address.");
        }
    }

    private void refreshAccounts() {
        String pAddress = Model.getInstance().getClient().payeeAddressProperty().get();
        System.out.println("Refreshing accounts for: " + pAddress);

        // Reload checking account
        var checkingAccount = Model.getInstance().getCheckingAccount(pAddress);
        if (checkingAccount != null) {
            Model.getInstance().getClient().checkingAccountProperty().set(checkingAccount);
            System.out.println("Refreshed Checking Balance: " + checkingAccount.balanceProperty().get());
        } else {
            System.out.println("Failed to reload checking account");
        }

        // Reload saving account
        var savingAccount = Model.getInstance().getSavingAccount(pAddress);
        if (savingAccount != null) {
            Model.getInstance().getClient().savingsAccountProperty().set(savingAccount);
            System.out.println("Refreshed Saving Balance: " + savingAccount.balanceProperty().get());
        } else {
            System.out.println("Failed to reload saving account");
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
