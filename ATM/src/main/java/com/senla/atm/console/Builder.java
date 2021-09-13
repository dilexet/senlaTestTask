package com.senla.atm.console;

import com.senla.atm.console.actions.ExitAction;
import com.senla.atm.console.actions.atmactions.CheckBalanceAction;
import com.senla.atm.console.actions.atmactions.PutMoneyAction;
import com.senla.atm.console.actions.atmactions.WithdrawMoneyAction;
import com.senla.atm.manager.Administrator;

public class Builder {
    private Menu rootMenu;

    public void buildMenu(Administrator administrator) {
        Menu rootMenu = new Menu();
        rootMenu.setName("Выберите операцию: ");
        MenuItem checkBalance = new MenuItem("Проверить баланс", new CheckBalanceAction(administrator));
        MenuItem putMoney = new MenuItem("Положить деньги на счёт", new PutMoneyAction(administrator));
        MenuItem withdrawMoney = new MenuItem("Снять деньги со счёта", new WithdrawMoneyAction(administrator));

        MenuItem exit = new MenuItem("Exit", new ExitAction());

        rootMenu.setMenuItems(new MenuItem[]
                {
                        checkBalance,
                        putMoney,
                        withdrawMoney,
                        exit
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
