package core.item;

import core.common.CommonView;
import core.common.InputValidator;

import java.util.List;
import java.util.Scanner;

import static core.common.CommonView.*;

public class ItemViewImpl implements ItemView {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String itemIndex() {
        String[] commands = {"Add", "Delete", "View", "Change"};
        return InputValidator.inputUserChoice("Item Menu", commands);
    }

    private String inputName() {
        String name;
        while (true) {
            try {
                System.out.println("이름을 입력해주세요. 취소(exit)");
                System.out.print("이름 : ");
                name = sc.nextLine();
                if ("exit".equals(name)) {
                    return name;
                }
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
        printHead("Add Item Menu");
        String name;
        int priceByHour;

        name = inputName();
        if(name.equals("exit")) {
            return null;
        }

        priceByHour = inputPriceByHour();
        return new Item(name, priceByHour);
    }

    @Override
    public Item deleteUI(List<Item> list) {
        printHead("Delete Item Menu");

        return selectOneItem(list, "삭제");
    }

    @Override
    public void printList(List<Item> list) {
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Override
    public Item updateUI(List<Item> list) {
        printHead("Change Item Menu");

        Item item = selectOneItem(list, "변경");
        if(item == null) {
            return null;
        }
        item.setName(inputName());
        item.setPriceByHour(inputPriceByHour());

        return item;
    }

    private Item selectOneItem(List<Item> list, String jobType) {
        Item item = null;

        if(list == null || list.size() ==0) {
            CommonView.printMessage("항목이 없습니다.");
            return null;
        }
        printList(list);

        while (true) {
            System.out.print(jobType + "할 항목 id를 입력해주세요(취소 : exit) : ");
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
                System.out.println("항목이 없습니다.");
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return item;
    }

}
