package org.hubert_lasota.BusinessManagement.reader;

import java.util.Scanner;

public class Reader {
    private static final Scanner userInput = new Scanner(System.in);

    private Reader() { }

    public static int readInt() {
        int result = userInput.nextInt();
        userInput.nextLine();
        return result;
    }

    public static long readLong() {
        long result = userInput.nextInt();
        userInput.nextLine();
        return result;
    }

    public static String readLine() {
        return userInput.nextLine();
    }
}
