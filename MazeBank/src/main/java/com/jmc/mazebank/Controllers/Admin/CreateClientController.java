package com.jmc.mazebank.Controllers.Admin;

import com.jmc.mazebank.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Random;

public class CreateClientController implements Initializable {
    public TextField fName_fld;
    public TextField lName_fld;
    public TextField password_fld;
    public CheckBox pAddress_box;
    public Label pAddress_fld;
    public CheckBox ch_acc_box;
    public TextField ch_amount_fld;
    public CheckBox sv_acc_box;
    public TextField sv_amount_fld;
    public Button create_client_btn;
    public Label error_lbl;

    private String payeeAddress;
    private Boolean createCheckingAccountFlag = false;
    private Boolean createSavingsAccountFlag = false;



    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        create_client_btn.setOnAction(event -> createClient());
        pAddress_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            } else {
                payeeAddress = null;
                pAddress_fld.setText("");
            }
        });

        ch_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            createCheckingAccountFlag = newValue;
        });
        sv_acc_box.selectedProperty().addListener((observable, oldValue, newValue) -> {
            createSavingsAccountFlag = newValue;
        });

    }

    private void createClient() {
       // create Checking Account
        if (createCheckingAccountFlag) {
            createAccount("Checking");
        }
        // create Savings Account
        if (createSavingsAccountFlag) {
            createAccount("Saving");
        }
        String fName = fName_fld.getText();
        String lName = lName_fld.getText();
        String password = password_fld.getText();
        Model.getInstance().getDatabaseDriver().createClient(fName, lName, payeeAddress,password , LocalDate.now());
        error_lbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold; -fx-font-size: 15px;");
        error_lbl.setText("Client Created Successfully");
        Model.getInstance().setClients();
        emptyFields();

    }

    private void createAccount(String accountType) {
        double balance = accountType.equals("Checking")
                ? Double.parseDouble(ch_amount_fld.getText())
                : Double.parseDouble(sv_amount_fld.getText());
        //generate account number
        String firstSection = "3201";
        String lastSection = Integer.toString((new Random()).nextInt(9999) + 1000);
        String accountNumber = firstSection + "  " + lastSection;
        //create the checking account
        if (accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
        } else {
            Model.getInstance().getDatabaseDriver().createSavingAccount(payeeAddress, accountNumber, 100, balance);
        }
    }

    private void onCreatePayeeAddress(){
        if (pAddress_fld.getText() != null) {
           pAddress_fld.setText(payeeAddress);
        }
    }

    private  String createPayeeAddress() {
        int id = Model.getInstance().getDatabaseDriver().getLastClientId() + 1;
        char fChar = Character.toLowerCase(fName_fld.getText().charAt(0));
        return "@"+fChar+lName_fld.getText().toLowerCase()+"_"+id;
    }


    private void emptyFields(){
        fName_fld.setText("");
        lName_fld.setText("");
        password_fld.setText("");
        pAddress_fld.setText("");
        pAddress_box.setSelected(false);
        ch_acc_box.setSelected(false);
        sv_acc_box.setSelected(false);
        ch_amount_fld.setText("");
        sv_amount_fld.setText("");
        createCheckingAccountFlag = false;
        createSavingsAccountFlag = false;
    }






}

