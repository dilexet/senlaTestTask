package com.senla.atm.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class Navigator {
    private final Logger logger = LoggerFactory.getLogger(Navigator.class);
    private Menu currentMenu;

    public void printMenu() {
        System.out.println(System.lineSeparator() + getCurrentMenu().getName());
        int itemOrdinalNumber = 0;
        for (MenuItem item : getCurrentMenu().getMenuItems()) {
            itemOrdinalNumber++;
            System.out.println(itemOrdinalNumber + " " + item.getTitle());
        }
    }

    public void navigate(Integer index, Object obj) throws Exception {
        if (Arrays.stream(currentMenu.getMenuItems()).count() <= index) {
            logger.error("Index out of range");
            return;
        }
        MenuItem menuItem = currentMenu.getMenuItems()[index - 1];
        if (menuItem.getAction() != null) {
            menuItem.doAction(obj);
            System.out.println("Press enter to continue");
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
