package core.common;

import static core.common.Color.*;

public class CommonView {
    private static final int presetLength = 90;
    private static int tempLength = 0;
    private static int length = 0;

    public static int length() {
        return length;
    }

    private static String Pad(String insertString, char spaceChar,
                              boolean subject, String backgroundColor, String foregroundColor) {
        String prefix;
        String suffix;
        if (subject) {
            prefix = "┌%-";
            suffix = "s┐";
        } else {
            prefix = "%-";
            suffix = "s";
        }
        insertString = "  " + insertString + "  ";

        int remain = insertString.length() % 2;

        int acceptLength;
        if(tempLength == 0) {
            acceptLength = presetLength;
        } else {
            acceptLength = tempLength;
        }

        int halfLength = acceptLength / 2 - insertString.length() / 2;
        insertString = backgroundColor + foregroundColor + insertString + ANSI_RESET;

        String formatStart = prefix + (halfLength - remain) + "s";
        String formatEnd = "%-" + halfLength + suffix;
        return String.format(formatStart, "").replace(' ', spaceChar) +
                insertString + String.format(formatEnd, "").replace(' ', spaceChar);
    }

    public static void printSubject(String subject) {
        String subjectLine = Pad(subject, '=', true, ANSI_YELLOW_BACKGROUND, ANSI_BLACK);
        System.out.println("\n" + subjectLine);
        length = subjectLine.length() - 14;
    }

    public static void printSubList(String subject) {
        String subjectLine = Pad(subject, '=', true, ANSI_GREEN_BACKGROUND, ANSI_BLACK);
        System.out.println("\n" + subjectLine);
        length = subjectLine.length() - 14;
    }

    public static void printHead(String head) {
        String headLine = Pad(head, '-', false, ANSI_YELLOW_BACKGROUND, ANSI_BLACK);
        System.out.println("\n" + headLine);
    }

    public static void printBottom() {
        String formatEnd = "└%-" + (length -2) + "s┘\n";
        String replace = String.format(formatEnd, "").replace(' ', '-');
        System.out.print(replace);
        tempLength = 0;
    }

    public static void printDivider() {
        String formatEnd = "|%-" + (length -2) + "s|\n";
        String replace = String.format(formatEnd, "").replace(' ', '-');
        System.out.print(replace);
    }

    public static void printContent(Object content, int adjust) {
        System.out.printf("|%-" + (length - 2 - adjust) + "s|\n", content.toString());
    }

    public static void printContent(String content, int adjust) {
        System.out.printf("|%-" + (length - 2 - adjust) + "s|\n", content);
    }

    public static void setTempLength(int length) {
        tempLength = length;
    }

    public static void printMessage(String message) {
        System.out.println("\n\"" + message + "\"");
    }

    public static void noticeError(Exception e) {
//        e.printStackTrace();
        System.out.println(ANSI_YELLOW + e.getMessage() + ANSI_RESET);
    }

    public static void logo() {
        String str = "___  ___       ___  ___        _    _                     _   _                          \n" +
                "|  \\/  |       |  \\/  |       | |  | |                   | | | |                         \n" +
                "| .  . |  ___  | .  . |  ___  | |  | |  __ _  _ __   ___ | |_| |  ___   _   _  ___   ___ \n" +
                "| |\\/| | / _ \\ | |\\/| | / _ \\ | |/\\| | / _` || '__| / _ \\|  _  | / _ \\ | | | |/ __| / _ \\\n" +
                "| |  | || (_) || |  | || (_) |\\  /\\  /| (_| || |   |  __/| | | || (_) || |_| |\\__ \\|  __/\n" +
                "\\_|  |_/ \\___/ \\_|  |_/ \\___/  \\/  \\/  \\__,_||_|    \\___|\\_| |_/ \\___/  \\__,_||___/ \\___|\n" +
                "                                                                                         \n" +
                "                                                                                         ";
        str.chars().forEach((ch) -> {
            System.out.print((char) ch);
            try {
                Thread.sleep(1, 500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
