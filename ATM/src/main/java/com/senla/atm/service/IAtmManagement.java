package com.senla.atm.service;

import java.util.Date;

public interface IAtmManagement {
    boolean checkCard(String cardNumber);

    boolean authorize(String cardNumber, int password);

    double checkBalance(String cardNumber);

    boolean withdrawMoney(String cardNumber, double money);

    boolean putMoney(String cardNumber, double money);

    Date lock(String cardNumber);

    void unlockAll();
}
