package com.senla.atm.util.filetools.implementation;

import com.senla.atm.entity.Account;
import com.senla.atm.entity.BankCard;
import com.senla.atm.enums.BankCardState;
import com.senla.atm.util.filetools.IParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Parser implements IParser {
    private static final String ERROR_VALUES_IS_NULL = "Values is null";
    private static final String ERROR_DATE_IS_NULL = "Date is null";
    private final char csvSplitBy;
    private final String dateFormat;
    private final Logger logger = LoggerFactory.getLogger(Parser.class);

    public Parser(char csvSplitBy, String dateFormat) {
        this.csvSplitBy = csvSplitBy;
        this.dateFormat = dateFormat;
    }

    public List<Account> parseFile(String[] lines) {
        List<Account> accounts = new ArrayList<>();
        for (String line : lines) {
            if (!line.equals("")) {
                String[] values = line.split(String.valueOf(csvSplitBy));
                accounts.add(converter(values));
            }
        }
        return accounts;
    }

    private Account converter(String[] values) {
        if (values == null || values[0] == null || values[1] == null || values[2] == null || values[3] == null) {
            logger.error(ERROR_VALUES_IS_NULL);
            return null;
        } else {
            var bankCard = new BankCard();
            var account = new Account();

            bankCard.setNumber(values[0]);
            bankCard.setPassword(Integer.parseInt(values[1]));
            account.setBankCard(bankCard);

            account.setBalance(Double.parseDouble(values[2]));
            account.setState(BankCardState.valueOf(values[3]));
            if (account.getState() == BankCardState.LOCKED && values[4] != null && values[5] != null) {
                var date = values[4] + " " + values[5];
                Date dateLocked = converterDateLocked(date);
                account.setDateLocked(dateLocked);
            }
            return account;
        }
    }

    private Date converterDateLocked(String date) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Date dateLocked = null;
        try {
            if (date == null) {
                throw new IllegalArgumentException(ERROR_DATE_IS_NULL);
            }
            dateLocked = format.parse(date);
        } catch (ParseException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
        return dateLocked;
    }
}
