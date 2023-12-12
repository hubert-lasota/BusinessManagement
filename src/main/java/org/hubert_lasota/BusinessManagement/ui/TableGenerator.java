package org.hubert_lasota.BusinessManagement.ui;

/**
 * The {@code TableGenerator} class provides static methods for generating simple text-based tables
 * with headers. The tables have a fixed width, and the content is centered within the specified width.
 * The class includes methods to create both table content and headers with a uniform appearance.
 *
 *
 * <p>Note: The maximum width of the table is set to 50 characters to maintain a readable format.
 *
 * <p>Example Usage:
 * <pre>{@code
 * String[] content = {"Row 1", "Row 2", "Longest Row"};
 * String title = "Sample Table";
 *
 *    // Creating a table
 *    String table = TableGenerator.createTable(title, content);
 *    System.out.println(table);
 * // Output:
 * //     +--------------------------------------------------+
 * //     |                   Sample Table                   |
 * //     +--------------------------------------------------+
 * //     |                   Row 1                          |
 * //     |                   Row 2                          |
 * //     |                   Longest Row                    |
 * //     +--------------------------------------------------+
 *
 *    // Creating a table header
 *    String tableHeader = TableGenerator.createTableHeader(title);
 *    System.out.println(tableHeader);
 * // Output:
 * //     +--------------------------------------------------+
 * //     |                   Sample Table                   |
 * //     +--------------------------------------------------+
 *
 * }</pre>
 *
 */
public class TableGenerator {
    private final static int WIDTH = 50;
    private final static int MIDDLE_OF_TABLE = WIDTH / 2;
    private static int height;
    private static int longestLengthOfContent;
    private static int whitespacesBeforeContent;
    private static int whitespacesAfterContent;
    private static int whitespacesBeforeTitle;
    private static int whitespacesAfterTitle;
    private TableGenerator() { }

    /**
     * Generates a text-based table with a centered title and content.
     *
     * @param title   The title of the table.
     * @param content The content rows of the table.
     * @return A string representing the generated table.
     * @throws UnsupportedOperationException If the length of the title or content exceeds the maximum allowed width which is 50.
     */
    public static String createTable(String title, String... content) {
        assignSizes(content);
        assignWhitespacesBeforeContent();
        StringBuilder sb = new StringBuilder();
        sb.append(createTableHeader(title));
        for(int i = 0; i < height; i++) {
            assignWhitespacesAfterContent(content[i]);
            sb.append("|").append(" ".repeat(whitespacesBeforeContent)).append(content[i]).append(" ".repeat(whitespacesAfterContent)).append("|\n");
        }
        sb.append("+").append("-".repeat(TableGenerator.WIDTH)).append("+\n");

        return sb.toString();
    }

    /**
     * Generates the header for a text-based table with a centered title.
     * It can be used without any rows
     *
     * @param title The title of the table.
     * @return A string representing the generated table header.
     * @throws UnsupportedOperationException If the length of the title exceeds the maximum allowed width which is 50.
     */
    public static String createTableHeader(String title) {
        assignHeaderWhitespaces(title);
        StringBuilder sb = new StringBuilder();
        sb.append("+").append("-".repeat(WIDTH)).append("+\n")
                .append("|").append(" ".repeat(whitespacesBeforeTitle))
                .append(title).append(" ".repeat(whitespacesAfterTitle)).append("|\n")
                .append("+").append("-".repeat(WIDTH)).append("+\n");

        return sb.toString();
    }

    private static void assignSizes(String... content) {
        longestLengthOfContent = findLongestLengthInContent(content);
        if(longestLengthOfContent > 50) {
            throw new UnsupportedOperationException("The length of content is too large. It can not exceed 50!");
        }
        height = content.length;
    }

    private static void assignWhitespacesBeforeContent() {
        whitespacesBeforeContent = MIDDLE_OF_TABLE;
        whitespacesBeforeContent -= isEven(longestLengthOfContent) ?
                longestLengthOfContent/2 : longestLengthOfContent/2 + 1;
    }

    private static void assignWhitespacesAfterContent(String lineOfContent) {
        whitespacesAfterContent = WIDTH - (whitespacesBeforeContent + lineOfContent.length());
    }

    private static void assignHeaderWhitespaces(String title) {
        if(title.length() > 50) {
            throw new UnsupportedOperationException("The length of title is too large. It can not exceed 50!");
        }
        whitespacesBeforeTitle = MIDDLE_OF_TABLE - (title.length() / 2);
        whitespacesAfterTitle = WIDTH - (whitespacesBeforeTitle + title.length());
    }
    private static boolean isEven(int num) {
        return num % 2 == 0;
    }

    private static int findLongestLengthInContent(String... content) {
        int longestLength = 0;
        for(String s : content) {
            if(longestLength < s.length()) {
                longestLength = s.length();
            }
        }
        return longestLength;
    }

}
