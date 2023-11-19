package org.hubert_lasota.BusinessManagement.exception;

import static org.hubert_lasota.BusinessManagement.ui.FrameGenerator.createStarFrame;

public class WrongInputException extends Exception {

    public WrongInputException() {
        super("WRONG INPUT!");
    }

    public static void throwAndCatchException() {
        try {
            throw new WrongInputException();
        } catch (WrongInputException e) {
            System.out.println(createStarFrame(e.getMessage()));
        }
    }
}
