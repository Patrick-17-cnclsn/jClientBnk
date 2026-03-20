package com.jmc.mazebank.Models;

import javafx.beans.property.SimpleDoubleProperty;

public class CheckingAccount extends Account{


    // the number of transaction a client is allowed to do per day
    private final SimpleDoubleProperty transactionLimit;

    public CheckingAccount(String owner, String accountNumber, double balance, int tLimit) {
        super(owner, accountNumber, balance);
        this.transactionLimit = new SimpleDoubleProperty(this, "TransactionLimit", tLimit);
    }

    public SimpleDoubleProperty transactionLimitProp() {
        return transactionLimit;
    }

    @Override
    public String toString() {
        return  accountNumberProperty().get();
    }

    /*
    client section
     */

    /*
     * Admin section
     */

    /*
    utilily section
     */
}
