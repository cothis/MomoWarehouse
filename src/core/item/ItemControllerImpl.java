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
                case "ADD":
                    create();
                    break;
                case "DELETE":
                    delete();
                    break;
                case "VIEW":
                    List<Item> read = read();
                    view.printList(read);
                    break;
                case "CHANGE":
                    update();
                    break;
                case "EXIT":
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
    public List<Item> read() {
        return dao.selectAll();
    }

    @Override
    public void update() {
        List<Item> items = read();
        Item item = view.updateUI(items);
        dao.update(item);
    }
}
