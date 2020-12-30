package core.item;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

import static core.ApplicationConfig.getItemController;
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
        while (!exit) {
            String select = view.itemIndex();
            switch (select) {
                case "ADD":
                    create();
                    break;
                case "DELETE":
                    delete();
                    break;
                case "VIEW":
                    read();
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
        } catch (ExitException e) {
            noticeError(e);
        }
    }

    @Override
    public void delete() {
        List<Item> items = dao.selectAll();
        try {
            Item item = view.deleteUI(items);
            dao.delete(item);
        } catch (ExitException | EmptyListException e) {
            noticeError(e);
        }
    }

    @Override
    public void read() {
        view.printList(findAll());
    }

    @Override
    public void update() {
        List<Item> items = findAll();
        try {
            Item item = view.updateUI(items);
            dao.update(item);
        } catch (ExitException | EmptyListException e) {
            noticeError(e);
        }
    }

    @Override
    public List<Item> findAll() {
        return dao.selectAll();
    }

    @Override
    public Item selectItem() throws ExitException, EmptyListException {
        List<Item> read = getItemController().findAll();
        return view.selectItem(read);
    }
}
