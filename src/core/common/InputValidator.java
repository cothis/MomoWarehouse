package core.common;

import java.util.*;

public class InputValidator {
    public static Scanner sc = new Scanner(System.in);

    public static String inputUserChoice(String subject, String... commands) {

        String result = null;
        boolean exit = false;

        List<String> list = new ArrayList<>(Arrays.asList(commands));

        if (list.stream()
                .noneMatch(command -> command.equals("EXIT") || command.equals("Log Out"))) {
            list.add("EXIT");
        }

        while (!exit) {
            CommonView.printSubject(subject);
            StringBuilder content = new StringBuilder();

            for (int i = 0; i < list.size(); i++) {
                content.append(String.format("%d.%s ", i + 1, list.get(i)));
            }
            String format = "| %-" + (CommonView.length() -3) + "s|\n";
            System.out.printf(format, content.toString());

            String formatEnd = "└%-" + (CommonView.length() -2) + "s┘\n";
            String replace = String.format(formatEnd, "").replace(' ', '-');
            System.out.print(replace);

            System.out.print(" >> ");
            result = sc.nextLine();

            // 숫자도 입력받을 수 있게
            for (int i = 0; i < list.size(); i++) {
                if ((i + 1 + "").equals(result) || list.get(i).equalsIgnoreCase(result)) {
                    result = list.get(i);
                    exit = true;
                    break;
                }
            }
        }
        return result.toUpperCase();
    }
}
