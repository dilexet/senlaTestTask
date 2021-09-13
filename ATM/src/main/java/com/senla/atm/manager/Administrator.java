package com.senla.atm.manager;

import com.senla.atm.service.IAtmManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Administrator {
    private final IAtmManagement atmManagement;
    private final Logger logger = LoggerFactory.getLogger(Administrator.class);

    public Administrator(IAtmManagement atmManagement) {
        this.atmManagement = atmManagement;
    }

    public boolean checkBankCard(String cardNumber) {
        var isCardFound = atmManagement.checkCard(cardNumber);
        if (!isCardFound) {
            logger.info("Карточка не найдена или заблокирована");
        } else {
            logger.info("Карточка действительна");
        }
        return isCardFound;
    }

    public boolean authorize(String cardNumber, int password) {
        var isAuthorize = atmManagement.authorize(cardNumber, password);
        if (!isAuthorize) {
            logger.info("Пароль введён неверно");
        } else {
            logger.info("Авторизация прошла успешно");
        }
        return isAuthorize;
    }

    public String checkBalance(String cardNumber) {
        var balance = atmManagement.checkBalance(cardNumber);
        return "Ваш баланс: " + balance;
    }

    public String withdrawMoney(String cardNumber, double money) {
        var result = atmManagement.withdrawMoney(cardNumber, money);
        return result ? "Операция прошла успешно" : "Недостаточно средств на карте";
    }

    public String putMoney(String cardNumber, double money) {
        var result = atmManagement.putMoney(cardNumber, money);
        return result ? "Операция прошла успешно" : "Превышен лимит пополнения";
    }

    public String lock(String cardNumber) {
        var date = atmManagement.lock(cardNumber);
        return "Ваша карта заблокирована до: '" + date.toString() + "'";
    }

    public void unlock() {
        atmManagement.unlockAll();
    }
}
