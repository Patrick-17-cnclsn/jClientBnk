package com.jmc.mazebank.Models;

import com.jmc.mazebank.Views.ViewFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    // client data section
    private final Client client;
    private boolean clientLoginSuccessFlag;

    // admin data section
    private boolean adminLoginSuccessFlag;
    private final ObservableList<Client>clients;
    private String accountNumber;

    // transactions data section
    private final ObservableList<Transaction> transactions;

    private Model() {
        this.viewFactory = new ViewFactory();
        this.databaseDriver = new DatabaseDriver();
        // client data section
        this.clientLoginSuccessFlag = false;
        this.client = new Client("","","","",null,null,null);

        // admin data section
        this.adminLoginSuccessFlag = false;
        this.clients = FXCollections.observableArrayList();

        // transactions data section
        this.transactions = FXCollections.observableArrayList();
    }
    public static synchronized Model getInstance(){
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {return databaseDriver;}


    /*
    client metho section
     */
    public boolean getClientLoginSuccessFlag() {return clientLoginSuccessFlag;}
    public void setClientLoginSuccessFlag(boolean flag) {this.clientLoginSuccessFlag = flag;}

    public Client getClient() {return client;}

    private static String firstNonNullColumn(ResultSet resultSet, String... columnNames) {
        for (String columnName : columnNames) {
            try {
                String value = resultSet.getString(columnName);
                if (value != null) {
                    return value;
                }
            } catch (SQLException ignored) {
            }
        }
        return null;
    }

    public void evaluateClientCred(String text, String passwordFldText) {
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        ResultSet resultSet = databaseDriver.getClientData(text, passwordFldText);

        try {
            if (resultSet != null && resultSet.next()) {
                this.client.firstNameProperty().set(firstNonNullColumn(resultSet, "FirstName", "firstName"));
                this.client.lastNameProperty().set(firstNonNullColumn(resultSet, "LastName", "lastName"));
                this.client.payeeAddressProperty().set(firstNonNullColumn(resultSet, "PayeeAddress", "payeeAddress"));
                String dateStr = resultSet.getString("dateOfBirth");
                if (dateStr != null) {
                    String[] dateParts = dateStr.split("-");
                    LocalDate date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                    this.client.dateProperty().set(date);
                }
                checkingAccount = getCheckingAccount(this.client.payeeAddressProperty().get());
                savingAccount = getSavingAccount(this.client.payeeAddressProperty().get());
                this.client.checkingAccountProperty().set(checkingAccount);
                this.client.savingsAccountProperty().set(savingAccount);
                this.clientLoginSuccessFlag = true;
            }
        } catch (SQLException e) {
            this.clientLoginSuccessFlag = false;
            throw new RuntimeException(e);
        }
    }
    /*
    admin section
     */
    public boolean getAdminLoginSuccessFlag() {return adminLoginSuccessFlag;}
    public void setAdminLoginSuccessFlag(boolean adminLoginSuccessFlag) {this.adminLoginSuccessFlag = adminLoginSuccessFlag;}

    public void evaluateAdminCred(String username, String password) {
        ResultSet resultSet = databaseDriver.getAdminData(username, password);
        try {
            this.adminLoginSuccessFlag = resultSet.isBeforeFirst();
        } catch (SQLException e) {
            this.adminLoginSuccessFlag = false;
            throw new RuntimeException(e);
        }
    }

   public ObservableList<Client> getClients() {return clients;}

    public void setClients() {
        CheckingAccount checkingAccount;
        SavingAccount savingAccount;
        ResultSet resultSet = databaseDriver.getAllClientsData();
        try {
            this.clients.clear();
            while (resultSet.next()) {
                String fName = resultSet.getString("FirstName");
                String lName = resultSet.getString("LastName");
                String pAddress = resultSet.getString("PayeeAddress");
                String dateStr = resultSet.getString("dateOfBirth");
                LocalDate date = null;
                if (dateStr != null) {
                    String[] dateParts = dateStr.split("-");
                    date = LocalDate.of(Integer.parseInt(dateParts[0]), Integer.parseInt(dateParts[1]), Integer.parseInt(dateParts[2]));
                }
                checkingAccount = getCheckingAccount(pAddress);
                savingAccount = getSavingAccount(pAddress);
                this.clients.add(new Client(fName, lName, pAddress, null, checkingAccount, savingAccount, date));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    utility methode
     */

    public CheckingAccount getCheckingAccount(String pAddress) {
        CheckingAccount account = null;
        ResultSet resultSet = databaseDriver.getCheckingAccountDta(pAddress);
        try {
            if (resultSet.next()) {
                String num = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");
                int wLimit = resultSet.getInt("Limit");
                account = new CheckingAccount(pAddress, num, balance, wLimit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    public SavingAccount getSavingAccount(String pAddress) {
        SavingAccount account = null;
        ResultSet resultSet = databaseDriver.getSavingAccountDta(pAddress);
        try {
            if (resultSet.next()) {
                String num = resultSet.getString("AccountNumber");
                double balance = resultSet.getDouble("Balance");
                double wLimit = resultSet.getDouble("WithdrawalLimit");
                account = new SavingAccount(pAddress, num, balance, wLimit);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return account;
    }

    /*
    transactions section
     */

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(String pAddress) {
        ResultSet resultSet = databaseDriver.getTransactionsByClient(pAddress);
        try {
            this.transactions.clear();
            while (resultSet.next()) {
                String sender = resultSet.getString("Sender");
                String receiver = resultSet.getString("Receiver");
                double amount = resultSet.getDouble("Amount");
                String dateStr = resultSet.getString("Date");
                String message = resultSet.getString("Message");
                LocalDate date = LocalDate.parse(dateStr);
                this.transactions.add(new Transaction(sender, receiver, amount, date, message));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
