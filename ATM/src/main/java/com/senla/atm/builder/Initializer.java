package com.senla.atm.builder;

import com.senla.atm.console.Builder;
import com.senla.atm.console.MenuController;
import com.senla.atm.console.Navigator;
import com.senla.atm.dao.implementation.AtmFileDao;
import com.senla.atm.manager.Administrator;
import com.senla.atm.service.implementation.AtmManagement;
import com.senla.atm.util.Properties;
import com.senla.atm.util.filetools.implementation.FileStreamReader;
import com.senla.atm.util.filetools.implementation.FileStreamWriter;
import com.senla.atm.util.filetools.implementation.Parser;

public class Initializer {
    public MenuController initialize() {
        FileStreamWriter fileStreamWriter = new FileStreamWriter();
        FileStreamReader fileStreamReader = new FileStreamReader();

        char cvsSplitBy = Properties.getInstance().getProperty("cvsSplitBy").charAt(1);
        String dateFormat = Properties.getInstance().getProperty("dateFormat");

        Parser parserCSV = new Parser(cvsSplitBy, dateFormat);


        AtmFileDao atmFileDao = new AtmFileDao(
                parserCSV,
                fileStreamWriter,
                fileStreamReader);

        AtmManagement atmManagement = new AtmManagement(atmFileDao);
        var admin = new Administrator(atmManagement);
        return new MenuController(new Builder(), new Navigator(), admin);
    }
}
