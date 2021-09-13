package com.senla.atm.console.menu;

import com.senla.atm.console.actions.ExitAction;
import com.senla.atm.console.actions.atmactions.CheckBalanceAction;
import com.senla.atm.console.actions.atmactions.PutMoneyAction;
import com.senla.atm.console.actions.atmactions.WithdrawMoneyAction;
import com.senla.atm.manager.Administrator;

public class MenuBuilder {
    private static final String MENU_NAME = "Select operation";
    private static final String[] MENU_ITEM_NAME = {
            "Exit",
            "Check balance",
            "Put money into the account",
            "Withdraw money from the account",
    };


    private Menu rootMenu;

    public void buildMenu(Administrator administrator) {
        Menu rootMenu = new Menu();
        rootMenu.setTitle(MENU_NAME);
        MenuItem exit = new MenuItem(MENU_ITEM_NAME[0], new ExitAction());
        MenuItem checkBalance = new MenuItem(MENU_ITEM_NAME[1], new CheckBalanceAction(administrator));
        MenuItem putMoney = new MenuItem(MENU_ITEM_NAME[2], new PutMoneyAction(administrator));
        MenuItem withdrawMoney = new MenuItem(MENU_ITEM_NAME[3], new WithdrawMoneyAction(administrator));


        rootMenu.setMenuItems(new MenuItem[]
                {
                        exit,
                        checkBalance,
                        putMoney,
                        withdrawMoney
                });
        setRootMenu(rootMenu);
    }

    public Menu getRootMenu() {
        return rootMenu;
    }

    public void setRootMenu(Menu rootMenu) {
        this.rootMenu = rootMenu;
    }
}
