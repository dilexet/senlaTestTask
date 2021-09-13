package com.senla.atm.dao;

import com.senla.atm.entity.BankCard;

import java.util.List;

public interface IAtmDao {
    void update(BankCard bankCard);

    BankCard getByCardNumber(String id);

    List<BankCard> getBankCard();
}
