package com.senla.atm.console.menu;

import com.senla.atm.console.actions.atmactions.AuthorizeAction;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class MenuController {
    private final MenuBuilder menuBuilder;
    private final MenuNavigator menuNavigator;
    private final Administrator administrator;
    private final Logger logger = LoggerFactory.getLogger(MenuController.class);

    public MenuController(MenuBuilder menuBuilder, MenuNavigator menuNavigator, Administrator administrator) {
        this.menuBuilder = menuBuilder;
        this.menuNavigator = menuNavigator;
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

        menuBuilder.buildMenu(administrator);
        menuNavigator.setCurrentMenu(menuBuilder.getRootMenu());
        menuNavigator.printMenu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            int choice = scanner.nextInt();
            try {
                menuNavigator.executeCommand(choice, validCardNumber);
            } catch (Exception e) {
                logger.error(e.getMessage());
                break;
            }
        }
    }
}
