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
        return null;
    }

    @Override
    public Item deleteUI(List<Item> list) {
        return null;
    }

    @Override
    public void printList(List<Item> list) {

    }

    @Override
    public Item updateUI(List<Item> list) {
        return null;
    }
}
