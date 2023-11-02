package org.hubert_lasota.BusinessManagement.ui;


public class FrameGenerator {
    private static final int WIDTH = 50;
    private static final int MIDDLE_OF_TABLE = WIDTH / 2;
    private static String corner;
    private static String side;
    private static String floorAndCeiling;

    private FrameGenerator() { }

    private enum TypeOfFrame{STAR, TABLE}

    public static String createTable(String title, String... content) {
        int height = content.length;
        int whiteSpacesBeforeContent = MIDDLE_OF_TABLE - 7;
        int whiteSpacesAfterContent;

        StringBuilder sb = new StringBuilder();
        String titleFrame = createTitleOfTable(title);
        sb.append(titleFrame);
        for(int i = 0; i < height; i++) {
            whiteSpacesAfterContent = MIDDLE_OF_TABLE - (content[i].length()) + 7;
            sb.append("|").append(" ".repeat(whiteSpacesBeforeContent)).append(content[i]).append(" ".repeat(whiteSpacesAfterContent)).append("|\n");
        }
        sb.append("+").append("-".repeat(WIDTH)).append("+\n");

        return sb.toString();
    }

    public static String createTitleOfTable(String title) {
        int whiteSpacesBeforeTitle = MIDDLE_OF_TABLE - (title.length() / 2);
        int whiteSpacesAfterTitle = WIDTH - (whiteSpacesBeforeTitle + title.length());

        StringBuilder sb = new StringBuilder();
        sb.append("+").append("-".repeat(WIDTH)).append("+\n");
        sb.append("|").append(" ".repeat(whiteSpacesBeforeTitle)).append(title).append(" ".repeat(whiteSpacesAfterTitle)).append("|\n");
        sb.append("+").append("-".repeat(WIDTH)).append("+\n");

        return sb.toString();
    }

    public static String createStarFrame(String... content) {
        return createFrame(TypeOfFrame.STAR, content);
    }

    public static String createTableFrame(String... content) {
        return createFrame(TypeOfFrame.TABLE, content);
    }

    private static String createFrame(TypeOfFrame tof, String... content) {
        createFramePattern(tof);
        int length = findLongestLengthInContent(content);
        int whiteSpacesAfterContent;

        StringBuilder sb = new StringBuilder();
        sb.append(corner).append(floorAndCeiling.repeat(length)).append(corner).append("\n");
        for(String s : content) {
            whiteSpacesAfterContent = length - s.length();
            sb.append(side).append(s).append(" ".repeat(whiteSpacesAfterContent)).append(side).append("\n");
        }
        sb.append(corner).append(floorAndCeiling.repeat(length)).append(corner).append("\n");

        return sb.toString();
    }

    private static void createFramePattern(TypeOfFrame tof) {
        if(tof.equals(TypeOfFrame.TABLE)) {
            corner = "+";
            side = "|";
            floorAndCeiling = "-";
        } else if(tof.equals(TypeOfFrame.STAR)) {
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
