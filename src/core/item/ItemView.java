package core.item;

import java.util.List;

public interface ItemView {
    String itemIndex();
    Item addUI() throws Exception;
    Item deleteUI(List<Item> list);
    void printList(List<Item> list);
    Item updateUI(List<Item> list) throws Exception;
}
