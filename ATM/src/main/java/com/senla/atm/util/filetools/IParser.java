package com.senla.atm.util.filetools;


import com.senla.atm.entity.BankCard;
import java.util.List;

public interface IParser {
    List<BankCard> parseFile(String[] lines);
}
