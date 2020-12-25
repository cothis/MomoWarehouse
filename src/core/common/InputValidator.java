package core.common;

import java.util.Scanner;

public class InputValidator {
    public static Scanner sc = new Scanner(System.in);

    public static String inputUserChoice(String[] commands) {
        String result = null;
        boolean exit = false;

        while (!exit) {
            for (int i = 0; i < commands.length; i++) {
                System.out.printf("%d.%s ", i + 1, commands[i]);
            }
            System.out.println();
            System.out.print(">>");
            result = sc.nextLine();

            for (int i = 0; i < commands.length; i++) {
                if ((i + 1 + "").equals(result) || commands[i].equals(result)) {
                    result = commands[i];
                    exit = true;
                    break;
                }
            }
            if ("exit".equals(result)) {
                result = "exit";
                exit = true;
            }
        }
        return result;

    }
}
