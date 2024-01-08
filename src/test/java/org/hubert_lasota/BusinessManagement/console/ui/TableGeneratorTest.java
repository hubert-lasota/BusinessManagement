package org.hubert_lasota.BusinessManagement.console.ui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TableGeneratorTest {

    @Test
    public void shouldCreateTableWithCorrectStructure() {
        String title = "Sample Table";
        String[] content = { "Row 1", "Row 2" };

        String expectedTable =  "+--------------------------------------------------+\n" +
                                "|                   Sample Table                   |\n" +
                                "+--------------------------------------------------+\n" +
                                "|                      Row 1                       |\n" +
                                "|                      Row 2                       |\n" +
                                "+--------------------------------------------------+\n";
        String actualTable = TableGenerator.createTable(title, content);

        assertEquals(expectedTable, actualTable);
    }

    @Test
    public void shouldCreateHeaderWithCorrectStructure() {
        String title = "Sample Title";

        String expectedHeader = "+--------------------------------------------------+\n" +
                                "|                   Sample Title                   |\n" +
                                "+--------------------------------------------------+\n";

        String actualHeader = TableGenerator.createTableHeader(title);

        assertEquals(expectedHeader, actualHeader);
    }

    @Test
    public void shouldThrowExceptionForTooLargeContent() {
        String title = "Sample Title";
        String tooLargeContent = "This content is too large and exceeds the limit!".repeat(5);

        assertThrows(UnsupportedOperationException.class, () -> {
            TableGenerator.createTable(title, tooLargeContent);
        });
    }

    @Test
    public void shouldThrowExceptionForTooLargeTitle() {
        String largeTitle = "This title is too large and exceeds the limit!".repeat(5);

        assertThrows(UnsupportedOperationException.class, () -> {
            TableGenerator.createTableHeader(largeTitle);
        });
    }

}
