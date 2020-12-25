package core.item;

import core.common.InputValidator;

import java.util.List;

public class ItemViewImpl implements ItemView {

    @Override
    public String itemIndex() {
        System.out.println("항목 관리 화면입니다.");
        String[] commands = {"항목추가", "항목삭제", "항목조회", "항목변경"};
        return InputValidator.inputUserChoice(commands);
    }

    @Override
    public Item addUI() {
        System.out.println("항목 추가 화면입니다.");
        Item item = null;

        System.out.print("이름 : ");
        return null;
    }

    @Override
    public Item deleteUI(List<Item> list) {
        return null;
    }

    @Override
    public void printList(List<Item> list) {
        for (Item item : list) {
            System.out.println(item);
        }
    }

    @Override
    public Item updateUI(List<Item> list) {
        return null;
    }
}
