package com.jmc.mazebank.Controllers.Client;

import com.jmc.mazebank.Models.Model;
import com.jmc.mazebank.Models.SavingAccount;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountsController implements Initializable {
    public Label ch_acc_num;
    public Label transaction_limit;
    public Label ch_acc_date;
    public Label ch_acc_bal;
    public Label sv_acc_num;
    public Label withdrawal_limit;
    public Label sv_acc_date;
    public Label sv_acc_bal;
    public TextField amount_to_sv;
    public Button trans_to_sv_btn;
    public TextField amount_to_ch;
    public Button trans_to_cv_btn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindData();
    }

    private void bindData() {
        // Bind Checking Account
        if (Model.getInstance().getClient().checkingAccountProperty().get() != null) {
            var checkingAccount = Model.getInstance().getClient().checkingAccountProperty().get();
            ch_acc_num.textProperty().bind(checkingAccount.accountNumberProperty());
            ch_acc_bal.textProperty().bind(checkingAccount.balanceProperty().asString("%.2f"));
            System.out.println("Accounts View - Checking Balance: " + checkingAccount.balanceProperty().get());
        } else {
            ch_acc_num.setText("No Account");
            ch_acc_bal.setText("0.00");
        }

        // Bind Saving Account
        if (Model.getInstance().getClient().savingsAccountProperty().get() != null) {
            var savingAccount = (SavingAccount) Model.getInstance().getClient().savingsAccountProperty().get();
            sv_acc_num.textProperty().bind(savingAccount.accountNumberProperty());
            sv_acc_bal.textProperty().bind(savingAccount.balanceProperty().asString("%.2f"));
            withdrawal_limit.textProperty().bind(savingAccount.withdrawLimitProp().asString("%.2f"));
            System.out.println("Accounts View - Saving Balance: " + savingAccount.balanceProperty().get());
        } else {
            sv_acc_num.setText("No Account");
            sv_acc_bal.setText("0.00");
            withdrawal_limit.setText("0.00");
        }

        // Bind dates
        ch_acc_date.textProperty().bind(Model.getInstance().getClient().dateProperty().asString());
        sv_acc_date.textProperty().bind(Model.getInstance().getClient().dateProperty().asString());
    }
}
