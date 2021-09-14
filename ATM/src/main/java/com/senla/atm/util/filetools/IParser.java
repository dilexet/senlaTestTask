package com.senla.atm.util.filetools;


import com.senla.atm.entity.Account;
import com.senla.atm.entity.BankCard;
import java.util.List;

public interface IParser {
    List<Account> parseFile(String[] lines);
}
