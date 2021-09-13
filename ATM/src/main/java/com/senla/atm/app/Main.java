package com.senla.atm.app;

import com.senla.atm.builder.Initializer;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Administrator.class);
        try {
            var menu = new Initializer().initialize();
            menu.run();
            System.out.println("Press enter to exit...");
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
