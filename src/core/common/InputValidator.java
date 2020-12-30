package core.common;

import core.common.exception.ExitException;

import java.util.*;

import static core.common.Color.*;
import static core.common.CommonView.*;

public class InputValidator {
    public static Scanner sc = new Scanner(System.in);

    public static String inputString(String valueName) throws ExitException {
        System.out.println(valueName + "을(를) 입력해주세요. 취소(exit)");

        String str = null;
        boolean exit = false;
        while (!exit) {
            System.out.print(valueName + " : ");

            str = sc.nextLine();
            if (str.equalsIgnoreCase("exit")) {
                throw new ExitException();
            }
            if (!str.trim().equals("")) exit = true;
        }
        return str.trim();
    }

    public static String inputUserChoice(String subject, String... commands) {
        String result = null;
        boolean exit = false;

        List<String> list = new ArrayList<>(Arrays.asList(commands));

        if (list.stream()
                .noneMatch(command -> command.equals("EXIT") || command.equals("Log Out"))) {
            list.add("EXIT");
        }

        boolean onlyEnter = false;
        while (!exit) {

            if (!onlyEnter) {
                printSubject(subject);
                StringBuilder content = new StringBuilder();

                for (int i = 0; i < list.size(); i++) {
                    String message = String.format("%d.%s  ", i + 1, list.get(i));
                    if (i == list.size() - 1) {
                        message = ANSI_RED + message + ANSI_RESET;
                    }
                    content.append(message);
                }
                String format = String.format(" %-" + (length() -3) + "s", content.toString());
                printContent(format,-9);

                printBottom();
            }

            System.out.print(" >> ");
            result = sc.nextLine();
            onlyEnter = result.trim().equals("");
            if (onlyEnter) continue;

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
