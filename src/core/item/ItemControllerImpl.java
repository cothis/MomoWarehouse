package core.item;

import java.util.List;

public class ItemControllerImpl implements ItemController {

    private final ItemDao dao;
    private final ItemView view;

    public ItemControllerImpl(ItemDao dao, ItemView view) {
        this.dao = dao;
        this.view = view;
    }

    @Override
    public void itemMenu() {
        boolean exit = false;
        while(!exit) {
            String select = view.itemIndex();
            switch (select) {
                case "항목추가":
                    create();
                    break;
                case "항목삭제":
                    delete();
                    break;
                case "항목조회":
                    read();
                    break;
                case "항목변경":
                    update();
                    break;
                case "종료":
                    exit = true;
                    break;
            }
        }
    }

    @Override
    public void create() {
        Item item = view.addUI();
        dao.addItem(item);
    }

    @Override
    public void delete() {
        List<Item> items = dao.selectAll();
        Item item = view.deleteUI(items);
        dao.delete(item);

    }

    @Override
    public void read() {
        List<Item> items = dao.selectAll();
        view.printList(items);
    }

    @Override
    public void update() {
        List<Item> items = dao.selectAll();
        Item item = view.updateUI(items);
        dao.update(item);

    }
}
