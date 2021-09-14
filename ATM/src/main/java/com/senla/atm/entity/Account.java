package com.senla.atm.entity;

import com.senla.atm.enums.BankCardState;

import java.util.Date;

public class Account {
    private BankCard bankCard;
    private double balance;
    private BankCardState state;
    private Date dateLocked;

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BankCardState getState() {
        return state;
    }

    public void setState(BankCardState state) {
        this.state = state;
    }

    public Date getDateLocked() {
        return dateLocked;
    }

    public void setDateLocked(Date dateLocked) {
        this.dateLocked = dateLocked;
    }

    public BankCard getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }
}
