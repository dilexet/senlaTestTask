package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;

public class CheckBalanceAction implements IAction {
    private final Administrator administrator;

    public CheckBalanceAction(Administrator administrator) {
        this.administrator = administrator;
    }
    @Override
    public Object execute(Object obj) {
        System.out.println(administrator.checkBalance(obj.toString()));
        return null;
    }
}
