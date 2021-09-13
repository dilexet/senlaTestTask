package com.senla.atm.console.menu;

import com.senla.atm.console.actions.IAction;

public class MenuItem {
    private final String title;
    private final IAction action;

    public MenuItem(String title, IAction action) {
        this.title = title;
        this.action = action;
    }

    public void doAction(Object obj) {
        getAction().execute(obj);
    }

    public String getTitle() {
        return title;
    }

    public IAction getAction() {
        return action;
    }
}
