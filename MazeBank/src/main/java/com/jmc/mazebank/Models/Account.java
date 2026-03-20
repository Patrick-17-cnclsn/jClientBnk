package com.jmc.mazebank.Models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Account {
    private final StringProperty owner;
    private final StringProperty accountNumber;
    private final DoubleProperty balance;

    public Account(String owner, String accountNumber, Double balance) {
        this.owner = new SimpleStringProperty(this, "owner", owner);
        this.accountNumber = new SimpleStringProperty(this, "accountNumber", accountNumber);
        this.balance = new SimpleDoubleProperty(this, "balance", balance);
    }

    public StringProperty ownerProperty() {
        return this.owner;
    }

    public StringProperty accountNumberProperty() {
        return this.accountNumber;
    }

    public DoubleProperty balanceProperty() {
        return this.balance;
    }


}
