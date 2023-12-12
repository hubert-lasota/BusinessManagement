package org.hubert_lasota.BusinessManagement.ui;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BorderGeneratorTest {

    @Test
    public void shouldCreateNormalBorderWithCorrectStructure() {
        String[] content = {"Row 1", "Row 2"};

        String expectedBorder = "+-------+\n" +
                                "| Row 1 |\n" +
                                "| Row 2 |\n" +
                                "+-------+\n";

        String actualBorder = BorderGenerator.createBorder(content);

        assertEquals(expectedBorder, actualBorder);
    }

    @Test
    public void shouldCreateStarBorderWithCorrectStructure() {
        String[] content = {"Row 1", "Row 2"};

        String expectedStarBorder = "*********\n" +
                                    "* Row 1 *\n" +
                                    "* Row 2 *\n" +
                                    "*********\n";

        String actualStarBorder = BorderGenerator.createStarBorder(content);

        assertEquals(expectedStarBorder, actualStarBorder);
    }

    @Test
    public void shouldHandleEmptyContent() {
        String[] content = {""};

        String expectedEmptyBorder = "+--+\n" +
                                     "|  |\n" +
                                     "+--+\n";

        String actualEmptyBorder = BorderGenerator.createBorder(content);

        assertEquals(expectedEmptyBorder, actualEmptyBorder);
    }

    @Test
    public void shouldHandleSingleRowContent() {
        String[] content = {"Single Row"};

        String expectedSingleRowStarBorder =
                "**************\n" +
                "* Single Row *\n" +
                "**************\n";

        String actualSingleRowStarBorder = BorderGenerator.createStarBorder(content);

        assertEquals(expectedSingleRowStarBorder, actualSingleRowStarBorder);
    }


}
