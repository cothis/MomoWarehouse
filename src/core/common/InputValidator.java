package core.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputValidator {
    public static Scanner sc = new Scanner(System.in);

    public static String inputUserChoice(String... commands) {
        String result = null;
        boolean exit = false;

        List<String> list = new ArrayList<>(Arrays.asList(commands));


        if (list.stream()
                .noneMatch(command -> command.equals("종료") || command.equals("로그아웃"))) {
            list.add("종료");
        }

        while (!exit) {
            for (int i = 0; i < list.size(); i++) {
                System.out.printf("%d.%s ", i + 1, list.get(i));
            }
            System.out.println();
            System.out.print(">>");
            result = sc.nextLine();

            // 숫자도 입력받을 수 있게
            for (int i = 0; i < list.size(); i++) {
                if ((i + 1 + "").equals(result) || list.get(i).equals(result)) {
                    result = list.get(i);
                    exit = true;
                    break;
                }
            }
        }
        return result;
    }
}
