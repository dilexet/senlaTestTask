package com.senla.atm.service.implementation;

import com.senla.atm.dao.IAtmDao;
import com.senla.atm.entity.ATM;
import com.senla.atm.entity.BankCard;
import com.senla.atm.enums.BankCardState;
import com.senla.atm.service.IAtmManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AtmManagement implements IAtmManagement {
    private final IAtmDao atmDao;
    private final Logger logger = LoggerFactory.getLogger(AtmManagement.class);

    public AtmManagement(IAtmDao atmDao) {
        this.atmDao = atmDao;
    }

    public boolean checkCard(String cardNumber) {
        var bankCard = atmDao.getByCardNumber(cardNumber);
        return bankCard != null && bankCard.getState() == BankCardState.ACTIVE;
    }

    public boolean authorize(String cardNumber, int password) {
        var bankCard = atmDao.getByCardNumber(cardNumber);
        return bankCard != null && bankCard.getPassword() == password;
    }

    public double checkBalance(String cardNumber) {
        return atmDao.getByCardNumber(cardNumber).getBalance();
    }

    public boolean withdrawMoney(String cardNumber, double money) {
        var atmLimit = ATM.getInstance().getBankCashLimit();
        logger.info("Имеется в банкомате: " + ATM.getInstance().getBankCashLimit());
        var bankCard = atmDao.getByCardNumber(cardNumber);
        var balance = bankCard.getBalance();
        if (balance - money >= 0 && atmLimit > money) {
            bankCard.setBalance(balance - money);
            ATM.getInstance().setBankCashLimit(atmLimit - money);
            atmDao.update(bankCard);
            logger.info("Имеется в банкомате: " + ATM.getInstance().getBankCashLimit());
            return true;
        }
        return false;
    }

    public boolean putMoney(String cardNumber, double money) {
        var atmLimit = ATM.getInstance().getBankCashLimit();
        logger.info("Имеется в банкомате: " + ATM.getInstance().getBankCashLimit());
        if (money > 1000000) {
            return false;
        }
        var bankCard = atmDao.getByCardNumber(cardNumber);
        var balance = bankCard.getBalance();
        bankCard.setBalance(balance + money);
        ATM.getInstance().setBankCashLimit(atmLimit + money);
        atmDao.update(bankCard);
        logger.info("Имеется в банкомате: " + ATM.getInstance().getBankCashLimit());
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
        atmDao.getBankCard().stream().filter(card -> card.getState() == BankCardState.LOCKED).forEach(this::unlock);
    }

    private void unlock(BankCard bankCard) {
        boolean result = checkDate(bankCard.getDateLocked());
        if (result) {
            bankCard.setState(BankCardState.ACTIVE);
            bankCard.setDateLocked(new GregorianCalendar().getTime());
            atmDao.update(bankCard);
            logger.info("Bank card with number " + bankCard.getNumber() + " is unlocked");
        }
    }

    private boolean checkDate(Date dateLocked) {
        var dateNow = new GregorianCalendar();
        dateNow.add(Calendar.DATE, -1);
        return dateLocked.before(dateNow.getTime());
    }
}
