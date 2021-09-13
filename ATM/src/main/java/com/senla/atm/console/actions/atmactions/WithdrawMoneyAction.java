package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WithdrawMoneyAction implements IAction {
    private static final String MSG_SUM_WITHDRAW = "Enter the amount to withdraw money: ";
    private static final String ERROR_INPUT_AMOUNT = "Amount input error";
    private final Administrator administrator;
    private final Logger logger = LoggerFactory.getLogger(WithdrawMoneyAction.class);

    public WithdrawMoneyAction(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Object execute(Object obj) {
        Scanner scanner = new Scanner(System.in);
        double money;
        try {
            do {
                System.out.println(MSG_SUM_WITHDRAW);
                money = scanner.nextDouble();
            } while (money <= 0);
            System.out.println(administrator.withdrawMoney(obj.toString(), money));
        } catch (InputMismatchException e) {
            logger.error(ERROR_INPUT_AMOUNT);
        }
        return null;
    }
}
