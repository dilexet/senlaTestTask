package com.senla.atm.console.actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExitAction implements IAction {
    private static final String MSG_PROCESS_TERMINATED = "Process terminated";
    private final Logger logger = LoggerFactory.getLogger(ExitAction.class);

    @Override
    public Object execute(Object obj) {
        logger.info(MSG_PROCESS_TERMINATED);
        System.exit(0);
        return null;
    }
}
