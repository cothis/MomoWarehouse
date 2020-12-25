package core.item;

import core.common.InputValidator;

import java.util.List;
import java.util.Scanner;

public class ItemViewImpl implements ItemView {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String itemIndex() {
        System.out.println("항목 관리 화면입니다.");
        String[] commands = {"항목추가", "항목삭제", "항목조회", "항목변경"};
        return InputValidator.inputUserChoice(commands);
    }

    private String inputName() {
        String name;
        while (true) {
            try {
                System.out.print("이름 : ");
                name = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return name;
    }

    private int inputPriceByHour() {
        int priceByHour;
        while (true) {
            try {
                System.out.print("시간 당 가격 : ");
                priceByHour = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return priceByHour;
    }

    @Override
    public Item addUI() {
        System.out.println("항목 추가 화면입니다.");
        Item item = null;
        String name;
        int priceByHour;

        name = inputName();

        priceByHour = inputPriceByHour();
        item = new Item(name, priceByHour);
        return item;
    }

    @Override
    public Item deleteUI(List<Item> list) {
        System.out.println("항목 삭제 화면입니다.");
        return selectOneItem(list);
    }

    @Override
    public void printList(List<Item> list) {
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Override
    public Item updateUI(List<Item> list) {
        System.out.println("항목 변경 화면입니다.");

        Item item = selectOneItem(list);
        if(item == null) {
            return null;
        }
        item.setName(inputName());
        item.setPriceByHour(inputPriceByHour());

        return item;
    }

    private Item selectOneItem(List<Item> list) {
        Item item = null;

        if(list == null || list.size() ==0) {
            System.out.println("항목이 없습니다");
            return null;
        }
        printList(list);

        while (true) {
            System.out.print("변경할 항목 id를 입력해주세요 : ");
            String select = sc.nextLine();
            if ("exit".equals(select)) {
                break;
            }

            try {
                int id = Integer.parseInt(select);
                for (Item selected : list) {
                    if (selected.getItemId() == id) {
                        item = selected;

                        break;
                    }
                }
                if (item != null) break;
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return item;
    }

}
