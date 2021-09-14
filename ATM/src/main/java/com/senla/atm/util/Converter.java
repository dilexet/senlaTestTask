package com.senla.atm.util;

import com.senla.atm.entity.Account;
import com.senla.atm.entity.BankCard;
import com.senla.atm.enums.BankCardState;

import java.text.SimpleDateFormat;

public class Converter {
    private static final String COLUMN_SEPARATOR = "cvsSplitBy";
    private static final String DATE_FORMAT = "dateFormat";

    public static String convertToWritableString(Account account) {
        var split = Properties.getInstance().getProperty(COLUMN_SEPARATOR).charAt(1);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(account.getBankCard().getNumber())
                .append(split)
                .append(account.getBankCard().getPassword())
                .append(split)
                .append(account.getBalance())
                .append(split)
                .append(account.getState());
        if (account.getState() == BankCardState.LOCKED) {
            var dateFormat = new SimpleDateFormat(Properties.getInstance().getProperty(DATE_FORMAT));
            var dateLocked = dateFormat.format(account.getDateLocked());
            stringBuilder.append(split).append(dateLocked);
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
