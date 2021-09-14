package com.senla.atm.dao.implementation;

import com.senla.atm.dao.IAtmDao;
import com.senla.atm.entity.Account;
import com.senla.atm.util.Converter;
import com.senla.atm.util.Properties;
import com.senla.atm.util.filetools.IParser;
import com.senla.atm.util.filetools.implementation.FileStreamReader;
import com.senla.atm.util.filetools.implementation.FileStreamWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class AtmFileDao implements IAtmDao {
    private static final String ERROR_CARD_NOT_FOUND = "Bank card not found";
    private static final String ERROR_CARD_NUMBER_EMPTY = "Card number is empty";
    private static final String FILE_PATH = "filePath";

    private final IParser parser;
    private final FileStreamWriter fileStreamWriter;
    private final FileStreamReader fileStreamReader;
    private final Logger logger = LoggerFactory.getLogger(AtmFileDao.class);

    public AtmFileDao(IParser parser, FileStreamWriter fileStreamWriter, FileStreamReader fileStreamReader) {
        this.parser = parser;
        this.fileStreamWriter = fileStreamWriter;
        this.fileStreamReader = fileStreamReader;
    }

    @Override
    public void update(Account account) {
        List<Account> accounts = getAccounts();
        var value = accounts.stream().filter(s -> s.getBankCard().getNumber().equals(account.getBankCard().getNumber())).findFirst().orElse(null);
        if (value == null) {
            logger.error(ERROR_CARD_NOT_FOUND);
        }
        var index = accounts.indexOf(value);
        accounts.set(index, account);
        StringBuilder data = new StringBuilder();
        for (var item : accounts) {
            data.append(Converter.convertToWritableString(item));
        }
        fileStreamWriter.fileWrite(Properties.getInstance().getProperty(FILE_PATH), data.toString(), false);

    }

    @Override
    public Account getByCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.equals("")) {
            logger.error(ERROR_CARD_NUMBER_EMPTY);
        }
        var bankCard = getAccounts().stream().filter(r -> r.getBankCard().getNumber().equals(cardNumber)).findFirst().orElse(null);
        if (bankCard == null) {
            logger.error(ERROR_CARD_NOT_FOUND);
        }
        return bankCard;
    }

    @Override
    public List<Account> getAccounts() {
        var fileData = fileStreamReader.fileRead(Properties.getInstance().getProperty(FILE_PATH));
        return parser.parseFile(fileData);
    }
}
