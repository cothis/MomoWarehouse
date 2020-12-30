package core.item;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

public class ItemViewImpl implements ItemView {
    @Override
    public String itemIndex() {
        String[] commands = {"Add", "Delete", "View", "Change"};
        return inputUserChoice("Item Menu", commands);
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

        String name = inputString("이름");
        int priceByHour = inputPriceByHour();

        return new Item(name, priceByHour);
    }

    @Override
    public Item deleteUI(List<Item> list) throws ExitException, EmptyListException {
        printHead("Delete Item Menu");

        return selectOneItem(list, "삭제");
    }

    @Override
    public void printList(List<Item> list) {
        printSubList("Item List");
        printContent(Item.getHeader(),0);
        printDivider();
        for (Item item : list) {
            printContent(item,4);
        }
        printBottom();
    }

    @Override
    public Item updateUI(List<Item> list) throws ExitException, EmptyListException {
        printHead("Change Item Menu");

        Item item = selectOneItem(list, "변경");
        item.setName(inputString("이름"));
        item.setPriceByHour(inputPriceByHour());

        return item;
    }

    private Item selectOneItem(List<Item> list, String jobType) throws ExitException, EmptyListException {
        Item item = null;

        if(list == null || list.size() ==0) {
            throw new EmptyListException();
        }
        printList(list);

        while (true) {
            String select = inputString(jobType + "할 항목 id");
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
