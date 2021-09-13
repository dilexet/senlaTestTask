package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PutMoneyAction implements IAction {
    private static final String MSG_SUM_PUT = "Enter the amount to top up the balance: ";
    private static final String ERROR_INPUT_AMOUNT = "Amount input error";

    private final Administrator administrator;
    private final Logger logger = LoggerFactory.getLogger(PutMoneyAction.class);

    public PutMoneyAction(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Object execute(Object obj) {
        Scanner scanner = new Scanner(System.in);
        double money;
        try {
            do {
                System.out.println(MSG_SUM_PUT);
                money = scanner.nextDouble();
            } while (money <= 0);
            System.out.println(administrator.putMoney(obj.toString(), money));
        } catch (InputMismatchException e) {
            logger.error(ERROR_INPUT_AMOUNT);
        }
        return null;
    }
}
