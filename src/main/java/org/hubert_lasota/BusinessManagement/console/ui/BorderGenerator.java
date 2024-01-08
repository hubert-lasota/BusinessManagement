package org.hubert_lasota.BusinessManagement.console.ui;

/**
 * The {@code BorderGenerator} class provides static methods for creating bordered strings, allowing users
 * to generate both normal and star borders around specified content.
 *
 * <p>Example:
 * <pre>{@code
 * String[] content = {"Row 1", "Row 2", "Longest Row"};
 *
 * // Creating a normal border
 * String normalBorder = BorderGenerator.createBorder(content);
 * // Output:
 * // +-------------+
 * // | Row 1       |
 * // | Row 2       |
 * // | Longest Row |
 * // +-------------+
 *
 * // Creating a star border
 * String starBorder = BorderGenerator.createStarBorder(content);
 * // Output:
 * // ***************
 * // * Row 1       *
 * // * Row 2       *
 * // * Longest Row *
 * // ***************
 * }</pre>
 */
public class BorderGenerator {

    private static String corner;
    private static String side;
    private static String floorAndCeiling;
    private enum TypeOfBorder { STAR, NORMAL }

    /**
     * Creates a bordered string with a star border around the specified content.
     *
     * @param content The content to be surrounded by a star border.
     * @return A string with a star border around the content.
     */
    public static String createStarBorder(String... content) {
        return createBorder(TypeOfBorder.STAR, content);
    }

    /**
     * Creates a bordered string with a normal border around the specified content.
     *
     * @param content The content to be surrounded by a normal border.
     * @return A string with a normal border around the content.
     */
    public static String createBorder(String... content) {
        return createBorder(TypeOfBorder.NORMAL, content);
    }

    private static String createBorder(TypeOfBorder tob, String... content) {
        assignBorderType(tob);
        int length = findLongestLengthInContent(content) + 2;
        int whitespacesAfterContent;

        StringBuilder sb = new StringBuilder();
        sb.append(corner).append(floorAndCeiling.repeat(length)).append(corner).append("\n");
        for(String row : content) {
            whitespacesAfterContent = length - row.length();
            sb.append(side).append(" ").append(row).append(" ".repeat(whitespacesAfterContent-1)).append(side).append("\n");
        }
        sb.append(corner).append(floorAndCeiling.repeat(length)).append(corner).append("\n");

        return sb.toString();
    }

    private static void assignBorderType(TypeOfBorder tob) {
        if(tob.equals(TypeOfBorder.NORMAL)) {
            corner = "+";
            side = "|";
            floorAndCeiling = "-";
        } else if(tob.equals(TypeOfBorder.STAR)) {
            corner = "*";
            side = "*";
            floorAndCeiling = "*";
        }
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
