package com.senla.atm.console.actions.atmactions;

import com.senla.atm.console.actions.IAction;
import com.senla.atm.manager.Administrator;
import com.senla.atm.util.Properties;

import java.util.Scanner;
import java.util.regex.Pattern;

public class AuthorizeAction implements IAction {
    private static final String REGEX_VALID_CARD = "regForValidCard";
    private static final String MSG_FORMAT_NUMBER_CARD = "Enter the card number in the format: 'xxxx-xxxx-xxxx-xxxx'";
    private static final String MSG_ERROR_CARD_INVALID = "Card is invalid or locked";
    private static final String MSG_PASSWORD_CARD = "Enter the card password in the format: 'xxxx'";
    private static final String MSG_NUMBER_ATTEMPTS = "Attempts remaining: ";

    private final Administrator administrator;

    public AuthorizeAction(Administrator administrator) {
        this.administrator = administrator;
    }

    @Override
    public Object execute(Object obj) {
        var regForValidCard = Properties.getInstance().getProperty(REGEX_VALID_CARD);
        Scanner scanner = new Scanner(System.in);

        String cardNumber;
        int password;
        boolean isValid;
        int numberOfAttempts = 3;

        do {
            System.out.println(MSG_FORMAT_NUMBER_CARD);
            cardNumber = scanner.nextLine();
            isValid = Pattern.matches(regForValidCard, cardNumber);
        } while (!isValid);

        var isActive = administrator.checkBankCard(cardNumber);
        if (!isActive) {
            System.out.println(MSG_ERROR_CARD_INVALID);
            return null;
        }
        System.out.println(MSG_PASSWORD_CARD);

        while (numberOfAttempts > 0) {
            System.out.println(MSG_NUMBER_ATTEMPTS + numberOfAttempts);
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
