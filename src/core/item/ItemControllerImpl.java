package core.item;

import core.common.InputValidator;

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
        while(true) {
            String select = view.itemIndex();
            switch (select) {
                case "항목추가":
                    add();
                    break;
                case "항목삭제":
                    delete();
                    break;
                case "항목조회":
                    select();
                    break;
                case "항목변경":
                    update();
                    break;
            }
            if("exit".equals(select)) {
                break;
            }
        }
    }

    @Override
    public void add() {
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
    public void select() {
        List<Item> items = dao.selectAll();
        view.printList(items);
    }

    @Override
    public void update() {

    }
}
