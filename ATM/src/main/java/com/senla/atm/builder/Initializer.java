package com.senla.atm.builder;

import com.senla.atm.console.menu.MenuBuilder;
import com.senla.atm.console.menu.MenuController;
import com.senla.atm.console.menu.MenuNavigator;
import com.senla.atm.dao.implementation.AtmFileDao;
import com.senla.atm.manager.Administrator;
import com.senla.atm.service.implementation.AtmManagement;
import com.senla.atm.util.Properties;
import com.senla.atm.util.filetools.implementation.FileStreamReader;
import com.senla.atm.util.filetools.implementation.FileStreamWriter;
import com.senla.atm.util.filetools.implementation.Parser;

public class Initializer {
    private static final String COLUMN_SEPARATOR = "cvsSplitBy";
    private static final String DATE_FORMAT = "dateFormat";

    public MenuController initialize() {
        FileStreamWriter fileStreamWriter = new FileStreamWriter();
        FileStreamReader fileStreamReader = new FileStreamReader();

        char cvsSplitBy = Properties.getInstance().getProperty(COLUMN_SEPARATOR).charAt(1);
        String dateFormat = Properties.getInstance().getProperty(DATE_FORMAT);

        Parser parserCSV = new Parser(cvsSplitBy, dateFormat);


        AtmFileDao atmFileDao = new AtmFileDao(
                parserCSV,
                fileStreamWriter,
                fileStreamReader);

        AtmManagement atmManagement = new AtmManagement(atmFileDao);
        var admin = new Administrator(atmManagement);
        return new MenuController(new MenuBuilder(), new MenuNavigator(), admin);
    }
}
