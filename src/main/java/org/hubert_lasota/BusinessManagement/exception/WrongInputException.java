package org.hubert_lasota.BusinessManagement.exception;

import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createStarBorder;

public class WrongInputException extends RuntimeException {

    public WrongInputException() {
        super("WRONG INPUT!");
    }

    public static void throwAndCatchException() {
        try {
            throw new WrongInputException();
        } catch (WrongInputException e) {
            System.out.println(createStarBorder(e.getMessage()));
        }
    }
}
