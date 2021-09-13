package com.senla.atm.util;

import com.senla.atm.entity.BankCard;
import com.senla.atm.enums.BankCardState;

import java.text.SimpleDateFormat;

public class Converter {
    public static String convertToWritableString(BankCard bankCard) {
        var split = Properties.getInstance().getProperty("cvsSplitBy").charAt(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(bankCard.getNumber())
                .append(split)
                .append(bankCard.getPassword())
                .append(split)
                .append(bankCard.getBalance())
                .append(split)
                .append(bankCard.getState());
        if (bankCard.getState() == BankCardState.LOCKED) {
            var dateFormat = new SimpleDateFormat(Properties.getInstance().getProperty("dateFormat"));
            var dateLocked = dateFormat.format(bankCard.getDateLocked());
            stringBuilder.append(split).append(dateLocked);
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}