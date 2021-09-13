package com.senla.atm.console.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitAction implements IAction {
    private final Logger logger = LoggerFactory.getLogger(ExitAction.class);

    @Override
    public Object execute(Object obj) {
        logger.info("Process terminated");
        System.exit(0);
        return null;
    }
}
