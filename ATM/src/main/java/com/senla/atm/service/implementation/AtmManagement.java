package com.senla.atm.service.implementation;

import com.senla.atm.dao.IAtmDao;
import com.senla.atm.entity.ATM;
import com.senla.atm.entity.Account;
import com.senla.atm.enums.BankCardState;
import com.senla.atm.service.IAtmManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AtmManagement implements IAtmManagement {
    private static final String INFO_BANK_CARD_UNLOCKED = "Bank card is unlocked: ";
    private final IAtmDao atmDao;
    private final Logger logger = LoggerFactory.getLogger(AtmManagement.class);

    public AtmManagement(IAtmDao atmDao) {
        this.atmDao = atmDao;
    }

    public boolean checkCard(String cardNumber) {
        var account = atmDao.getByCardNumber(cardNumber);
        return account != null && account.getBankCard() != null && account.getState() == BankCardState.ACTIVE;
    }

    public boolean authorize(String cardNumber, int password) {
        var account = atmDao.getByCardNumber(cardNumber);
        return account != null && account.getBankCard() != null && account.getBankCard().getPassword() == password;
    }

    public double checkBalance(String cardNumber) {
        return atmDao.getByCardNumber(cardNumber).getBalance();
    }

    public boolean withdrawMoney(String cardNumber, double money) {
        var atmLimit = ATM.getInstance().getBankCashLimit();
        var bankCard = atmDao.getByCardNumber(cardNumber);
        var balance = bankCard.getBalance();
        if (balance - money >= 0 && atmLimit > money) {
            bankCard.setBalance(balance - money);
            ATM.getInstance().setBankCashLimit(atmLimit - money);
            atmDao.update(bankCard);
            return true;
        }
        return false;
    }

    public boolean putMoney(String cardNumber, double money) {
        var atmLimit = ATM.getInstance().getBankCashLimit();
        if (money > 1000000) {
            return false;
        }
        var bankCard = atmDao.getByCardNumber(cardNumber);
        var balance = bankCard.getBalance();
        bankCard.setBalance(balance + money);
        ATM.getInstance().setBankCashLimit(atmLimit + money);
        atmDao.update(bankCard);
        return true;
    }

    public Date lock(String cardNumber) {
        var bankCard = atmDao.getByCardNumber(cardNumber);
        bankCard.setState(BankCardState.LOCKED);
        var dateLocked = new GregorianCalendar();
        bankCard.setDateLocked(dateLocked.getTime());
        atmDao.update(bankCard);
        dateLocked.add(Calendar.DATE, 1);
        return dateLocked.getTime();
    }

    public void unlockAll() {
        atmDao.getAccounts().stream().filter(card -> card.getState() == BankCardState.LOCKED).forEach(this::unlock);
    }

    private void unlock(Account account) {
        boolean result = checkDate(account.getDateLocked());
        if (result) {
            account.setState(BankCardState.ACTIVE);
            account.setDateLocked(new GregorianCalendar().getTime());
            atmDao.update(account);
            logger.info(INFO_BANK_CARD_UNLOCKED + account.getBankCard().getNumber());
        }
    }

    private boolean checkDate(Date dateLocked) {
        var dateNow = new GregorianCalendar();
        dateNow.add(Calendar.DATE, -1);
        return dateLocked.before(dateNow.getTime());
    }
}
