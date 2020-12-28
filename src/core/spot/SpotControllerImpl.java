package core.spot;

import java.util.List;

import static core.common.CommonView.*;

public class SpotControllerImpl implements SpotController {
    private final SpotDao dao;
    private final SpotView view;

    public SpotControllerImpl(SpotDao dao, SpotView view) {
        this.dao = dao;
        this.view = view;
    }

    @Override
    public void spotMenu() {
        boolean exit = false;
        while (!exit) {
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
                case "EXIT":
                    exit = true;
                    break;
            }
        }
    }

    @Override
    public void create() {
        try {
            Spot spot = view.addUI();
            dao.addSpot(spot);
        } catch (Exception exception) {
            noticeError(exception);
        }
    }

    @Override
    public void delete() {
        List<Spot> spots = dao.selectAll();
        try {
            Spot spot = view.deleteUI(spots);
            dao.delete(spot);
        } catch (Exception exception) {
            noticeError(exception);
        }

    }

    @Override
    public void read() {
        List<Spot> spots = dao.selectAll();
        view.printList(spots);
    }

    @Override
    public void update() {
        List<Spot> spots = dao.selectAll();
        try {
            Spot spot = view.updateUI(spots);
            dao.update(spot);
        } catch (Exception exception) {
            noticeError(exception);
        }

    }

    @Override
    public List<Spot> findAll() {
        return dao.selectAll();
    }
}
