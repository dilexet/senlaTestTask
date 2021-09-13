package com.senla.atm.util.filetools.implementation;

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
    private final char csvSplitBy;
    private final String dateFormat;
    private final Logger logger = LoggerFactory.getLogger(Parser.class);

    public Parser(char csvSplitBy, String dateFormat) {
        this.csvSplitBy = csvSplitBy;
        this.dateFormat = dateFormat;
    }

    public List<BankCard> parseFile(String[] lines) {
        List<BankCard> rooms = new ArrayList<>();
        for (String line : lines) {
            if (!line.equals("")) {
                String[] values = line.split(String.valueOf(csvSplitBy));
                rooms.add(converter(values));
            }
        }
        return rooms;
    }

    private BankCard converter(String[] values) {
        if (values == null || values[0] == null || values[1] == null || values[2] == null || values[3] == null) {
            logger.error("values is empty");
            return null;
        } else {
            var bankCard = new BankCard();
            bankCard.setNumber(values[0]);
            bankCard.setPassword(Integer.parseInt(values[1]));
            bankCard.setBalance(Double.parseDouble(values[2]));
            bankCard.setState(BankCardState.valueOf(values[3]));
            if (bankCard.getState() == BankCardState.LOCKED) {
                var date = values[4] + " " + values[5];
                var format = new SimpleDateFormat(dateFormat);
                Date dateLocked = null;
                try {
                    dateLocked = format.parse(date);
                } catch (ParseException e) {
                    logger.error(e.getMessage());
                }
                bankCard.setDateLocked(dateLocked);
            }
            return bankCard;
        }
    }
}
