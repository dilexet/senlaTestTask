package com.senla.atm.manager;

import com.senla.atm.entity.ATM;
import com.senla.atm.service.IAtmManagement;

public class Administrator {
    private static final String MSG_GET_BALANCE = "Your balance is ";
    private static final String MSG_BANK_CASH_LIMIT = "The bank has ";
    private static final String MSG_OPERATION_SUCCESS = "Operation was successfully completed";
    private static final String MSG_NOT_ENOUGH_MONEY = "You don't have enough money or bank limit exceeded";
    private static final String MSG_LIMIT_EXCEEDED = "Limit is exceeded";
    private static final String MSG_CARD_LOCK = "Your card is blocked until: ";


    private final IAtmManagement atmManagement;

    public Administrator(IAtmManagement atmManagement) {
        this.atmManagement = atmManagement;
    }

    public boolean checkBankCard(String cardNumber) {
        return atmManagement.checkCard(cardNumber);
    }

    public boolean authorize(String cardNumber, int password) {
        return atmManagement.authorize(cardNumber, password);
    }

    public String checkBalance(String cardNumber) {
        var balance = atmManagement.checkBalance(cardNumber);
        return MSG_GET_BALANCE + balance;
    }

    public String withdrawMoney(String cardNumber, double money) {
        System.out.println(MSG_BANK_CASH_LIMIT + ATM.getInstance().getBankCashLimit());
        var result = atmManagement.withdrawMoney(cardNumber, money);
        return result ? MSG_OPERATION_SUCCESS : MSG_NOT_ENOUGH_MONEY;
    }

    public String putMoney(String cardNumber, double money) {
        var result = atmManagement.putMoney(cardNumber, money);
        return result ? MSG_OPERATION_SUCCESS : MSG_LIMIT_EXCEEDED;
    }

    public String lock(String cardNumber) {
        var date = atmManagement.lock(cardNumber);
        return MSG_CARD_LOCK + date.toString();
    }

    public void unlock() {
        atmManagement.unlockAll();
    }
}
