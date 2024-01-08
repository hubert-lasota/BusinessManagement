package org.hubert_lasota.BusinessManagement.console.input;

import org.hubert_lasota.BusinessManagement.exception.WrongInputException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class UserInputReader {
    private static final Scanner userInput = new Scanner(System.in);

    private UserInputReader() { }

    public static int readInt() {
       return readNumber(Integer.class);
    }

    public static long readLong() {
        return readNumber(Long.class);
    }

    public static BigDecimal readBigDecimal() {
        return readNumber(BigDecimal.class);
    }

    public static String readLine() {
        return userInput.nextLine();
    }

    private static <T extends Number> T readNumber(Class<T> clazz) {
        String result;
        while (true) {
            try {
                result = userInput.nextLine();
                return checkTypeAndReturnNumber(clazz, result);
            } catch (NumberFormatException e) {
                WrongInputException.throwAndCatchException();
            }
        }
    }

    private static <T extends Number> T checkTypeAndReturnNumber(Class<T> clazz, String number) {
            if(Integer.class.equals(clazz)) {
                return clazz.cast(Integer.parseInt(number));
            } else if (Long.class.equals(clazz)) {
                return clazz.cast(Long.parseLong(number));
            } else if (BigDecimal.class.equals(clazz)) {
                return clazz.cast(new BigDecimal(number));
            } else {
                throw new IllegalArgumentException("Unsupported number type");
            }
    }

    public static LocalDate readLocalDate() {
        while (true) {
            try {
                String localDate = readLine();
                return LocalDate.parse(localDate);
            } catch (DateTimeParseException e) {
                WrongInputException.throwAndCatchException();
            }
        }
    }

}
