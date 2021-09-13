package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;
import com.senla.atm.util.Properties;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AuthorizeAction implements IAction {
    private final Administrator administrator;

    public AuthorizeAction(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Object execute(Object obj) {
        var regForValidCard = Properties.getInstance().getProperty("regForValidCard");
        Scanner scanner = new Scanner(System.in);

        String cardNumber;
        int password;
        boolean isValid;
        int numberOfAttempts = 3;

        do {
            System.out.println("Введите номер карточки в формате: 'xxxx-xxxx-xxxx-xxxx'");
            cardNumber = scanner.nextLine();
            isValid = Pattern.matches(regForValidCard, cardNumber);
        } while (!isValid);

        var isActive = administrator.checkBankCard(cardNumber);
        if (!isActive) {
            System.out.println("Card is invalid or locked");
            return null;
        }
        System.out.println("Введите пин-код в формате: 'xxxx'");

        while (numberOfAttempts > 0) {
            System.out.println("Попыток осталось: " + numberOfAttempts);
            password = scanner.nextInt();
            if (administrator.authorize(cardNumber, password)) {
                return cardNumber;
            } else {
                --numberOfAttempts;
            }
        }
        System.out.println(administrator.lock(cardNumber));
        return null;
    }
}
