package core.spot;

import java.util.ArrayList;
import java.util.List;

public class SpotControllerImpl implements SpotController{
    private final SpotDao dao;
    private final SpotView view;

    public SpotControllerImpl(SpotDao dao, SpotView view) {
        this.dao = dao;
        this.view = view;
    }

    @Override
    public void spotMenu() {
        boolean exit = false;
        while(!exit) {
            String select = view.spotIndex();
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
                case "종료":
                    exit = true;
                    break;
            }
        }
    }

    @Override
    public void create() {
        Spot spot = view.addUI();
        dao.addSpot(spot);
    }

    @Override
    public void delete() {
        List<Spot> spots = dao.selectAll();
        Spot spot = view.deleteUI(spots);
        dao.delete(spot);

    }

    @Override
    public void read() {
        List<Spot> spots = dao.selectAll();
        view.printList(spots);
    }

    @Override
    public void update() {
        List<Spot> spots = dao.selectAll();
        Spot spot = view.updateUI(spots);
        dao.update(spot);

    }

    @Override
    public List<Spot> findAll() {
        return dao.selectAll();
    }
}
