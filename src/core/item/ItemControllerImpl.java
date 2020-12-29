package core.item;

import core.common.exception.ExitException;

import java.util.List;

import static core.common.CommonView.*;

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
        try {
            Item item = view.addUI();
            dao.addItem(item);
        } catch (Exception e) {
            noticeError(e);
        }
    }

    @Override
    public void delete() {
        List<Item> items = dao.selectAll();
        try {
            Item item = view.deleteUI(items);
            dao.delete(item);
        } catch (ExitException e) {
            noticeError(e);
        }
    }

    @Override
    public List<Item> read() {
        return dao.selectAll();
    }

    @Override
    public void update() {
        List<Item> items = read();
        try {
            Item item = view.updateUI(items);
            dao.update(item);
        } catch (Exception exception) {
            noticeError(exception);
        }
    }
}
