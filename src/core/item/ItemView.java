package core.item;

import core.common.exception.ExitException;

import java.util.List;

public interface ItemView {
    String itemIndex();
    Item addUI() throws ExitException;
    Item deleteUI(List<Item> list);
    void printList(List<Item> list);
    Item updateUI(List<Item> list) throws ExitException;
}
