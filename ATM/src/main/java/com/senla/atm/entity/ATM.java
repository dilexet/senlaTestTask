package com.senla.atm.entity;


import java.util.Random;

public class ATM {
    private static ATM atm = null;
    private double bankCashLimit;

    private ATM() {
        Random random = new Random();
        var res = random.nextDouble() + random.nextInt(10000000);
        setBankCashLimit(res);
    }

    public static ATM getInstance() {
        if (atm == null) {
            atm = new ATM();
        }
        return atm;
    }

    public double getBankCashLimit() {
        return bankCashLimit;
    }

    public void setBankCashLimit(double bankCashLimit) {
        this.bankCashLimit = bankCashLimit;
    }
}
