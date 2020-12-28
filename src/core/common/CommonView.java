package core.common;

import static core.common.Color.*;

public class CommonView {
    private static final int presetLength = 90;
    private static int length = 0;

    public static int length() {
        return length;
    }

    private static String Pad(String insertString, char spaceChar, boolean subject) {
        String prefix;
        String suffix;
        if(subject) {
            prefix = "┌%-";
            suffix = "s┐";
        }
        else {
            prefix = "%-";
            suffix = "s";
        }
        insertString = "  " + insertString + "  ";

        int remain = insertString.length() % 2;

        int halfLength = presetLength / 2 - insertString.length() / 2;
        insertString = ANSI_WHITE_BACKGROUND + ANSI_BLACK + insertString + ANSI_RESET;


        String formatStart = prefix + (halfLength - remain) + "s";
        String formatEnd = "%-" + halfLength + suffix;
        return String.format(formatStart, "").replace(' ', spaceChar) +
                insertString + String.format(formatEnd, "").replace(' ', spaceChar);
    }

    public static void printSubject(String subject) {
        String subjectLine = Pad(subject, '=', true);
        System.out.println("\n" + subjectLine);
        length = subjectLine.length() - 14;
    }

    public static void printHead(String head) {
        String headLine = Pad(head, '-', false);
        System.out.println("\n" + headLine);
    }

    public static void printMessage(String message) {
        System.out.println("\n\"" + message + "\"");
    }

    public static void noticeError(Exception e) {
        System.out.println(ANSI_YELLOW + e.getMessage() + ANSI_RESET);
    }
}
