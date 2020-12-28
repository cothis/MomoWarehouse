package core.item;

import core.common.CommonView;
import core.common.InputValidator;
import core.common.exception.ExitException;

import java.util.List;
import java.util.Scanner;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

public class ItemViewImpl implements ItemView {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String itemIndex() {
        String[] commands = {"Add", "Delete", "View", "Change"};
        return inputUserChoice("Item Menu", commands);
    }

    private String inputName() throws ExitException {
        return inputString("이름");
    }

    private int inputPriceByHour() throws ExitException {
        int priceByHour;
        while (true) {
            try {
                String str = inputString("시간 당 가격");
                priceByHour = Integer.parseInt(str);

                break;
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return priceByHour;
    }

    @Override
    public Item addUI() throws ExitException {
        printHead("Add Item Menu");
        String name;
        int priceByHour;

        name = inputName();
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
    public Item updateUI(List<Item> list) throws ExitException {
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
