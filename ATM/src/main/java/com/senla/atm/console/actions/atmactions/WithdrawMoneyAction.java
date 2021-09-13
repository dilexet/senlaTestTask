package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.InputMismatchException;
import java.util.Scanner;

public class WithdrawMoneyAction implements IAction {
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
                System.out.println("Введите сумму которую хотите снять: ");
                money = scanner.nextDouble();
            } while (money <= 0);
        } catch (InputMismatchException e) {
            logger.error("Ошибка ввода числа");
            return null;
        }
        System.out.println(administrator.withdrawMoney(obj.toString(), money));
        return null;
    }
}
