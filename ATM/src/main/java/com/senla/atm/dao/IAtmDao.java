package com.senla.atm.dao;

import com.senla.atm.entity.Account;
import com.senla.atm.entity.BankCard;

import java.util.List;

public interface IAtmDao {
    void update(Account account);

    Account getByCardNumber(String id);

    List<Account> getAccounts();
}
