package com.senla.atm.console.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class MenuNavigator {
    private static final String ERROR_INDEX_OUT = "Index out of range";
    private static final String MSG_PRESS_ENTER = "Press enter to continue";

    private final Logger logger = LoggerFactory.getLogger(MenuNavigator.class);
    private Menu currentMenu;

    public void printMenu() {
        System.out.println(System.lineSeparator() + getCurrentMenu().getTitle());
        int itemOrdinalNumber = 0;
        for (MenuItem item : getCurrentMenu().getMenuItems()) {
            itemOrdinalNumber++;
            System.out.println(itemOrdinalNumber + " " + item.getTitle());
        }
    }

    public void executeCommand(Integer index, Object obj) throws Exception {
        if (Arrays.stream(currentMenu.getMenuItems()).count() < index) {
            logger.error(ERROR_INDEX_OUT);
            printMenu();
            return;
        }
        MenuItem menuItem = currentMenu.getMenuItems()[index - 1];
        if (menuItem.getAction() != null) {
            menuItem.doAction(obj);
            System.out.println(MSG_PRESS_ENTER);
            //noinspection ResultOfMethodCallIgnored
            System.in.read();
        }
        printMenu();
    }

    public Menu getCurrentMenu() {
        return currentMenu;
    }

    public void setCurrentMenu(Menu currentMenu) {
        this.currentMenu = currentMenu;
    }
}
