package com.senla.atm.entity;

import com.senla.atm.enums.BankCardState;

import java.util.Date;

public class BankCard {
    private String number;
    private int password;
    private double balance;
    private BankCardState state;
    private Date dateLocked;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

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
}
