package com.jmc.mazebank.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private final StringProperty password;
    private final ObjectProperty<Account> checkingAccount;

    private final ObjectProperty<Account> savingsAccount;

    private final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstName, String lastName, String payeeAddress, String password, Account checkingAccount, Account savingsAccount, LocalDate dateCreated) {
        this.firstName = new SimpleStringProperty(this, "firstName", firstName);
        this.lastName = new SimpleStringProperty(this, "lastName", lastName);
        this.payeeAddress = new SimpleStringProperty(this, "payeeAddress", payeeAddress);
        this.password = new SimpleStringProperty(this, "password", password);
        this.checkingAccount = new SimpleObjectProperty<>(this, "checkingAccount", checkingAccount);
        this.savingsAccount = new SimpleObjectProperty<>(this, "savingsAccount", savingsAccount);
        this.dateCreated = new SimpleObjectProperty<>(this, "dateCreated", dateCreated);
    }

    public StringProperty firstNameProperty() {return firstName;}

    public StringProperty lastNameProperty() {return lastName;}

    public StringProperty payeeAddressProperty() {return payeeAddress;}

    public StringProperty passwordProperty() {return password;}

    public ObjectProperty<Account> checkingAccountProperty() {return checkingAccount;}

    public ObjectProperty<Account> savingsAccountProperty() {return savingsAccount;}

    public ObjectProperty<LocalDate> dateProperty() {return dateCreated;}
}
