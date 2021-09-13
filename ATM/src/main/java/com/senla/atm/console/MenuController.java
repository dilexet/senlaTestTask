package com.senla.atm.console;

import com.senla.atm.console.actions.atmactions.AuthorizeAction;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MenuController {
    private final Builder builder;
    private final Navigator navigator;
    private final Administrator administrator;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    public MenuController(Builder builder, Navigator navigator, Administrator administrator) {
        this.builder = builder;
        this.navigator = navigator;
        this.administrator = administrator;
    }

    public void run() {
        Runnable unlock = administrator::unlock;
        Thread thread = new Thread(unlock);
        thread.start();
        var validCardNumber = new AuthorizeAction(administrator).execute(null);
        if (validCardNumber == null) {
            return;
        }

        builder.buildMenu(administrator);
        navigator.setCurrentMenu(builder.getRootMenu());
        navigator.printMenu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();
            try {
                navigator.navigate(choice, validCardNumber);
            } catch (Exception e) {
                logger.error(e.getMessage());
                break;
            }
        }
    }
}
