package org.hubert_lasota.BusinessManagement.console.menu;

import static org.hubert_lasota.BusinessManagement.console.input.UserInputReader.readLine;
import static org.hubert_lasota.BusinessManagement.console.ui.BorderGenerator.createBorder;

/**
 * The {@code Menu} interface defines a method for generating a menu.
 * Implementations of this interface handle the creation and display of menus for user interaction.
 */
public interface Menu {

    /**
     * Generates and displays a menu for user interaction.
     */
    void generateMenu();

    default boolean continueOperation(String message, String keyToStop) {
        System.out.println(createBorder(message));
        String userInput = readLine();
        return !userInput.equalsIgnoreCase(keyToStop);
    }

    default boolean continueInput(String message, String keyToStop) {
        return continueOperation(message, keyToStop);
    }

}
