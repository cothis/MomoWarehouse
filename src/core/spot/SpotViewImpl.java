package core.spot;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

public class SpotViewImpl implements SpotView {
    @Override
    public String spotIndex() {
        String[] commands = {"Add", "Delete", "View", "Change"};
        return inputUserChoice("Spot Menu", commands);
    }

    @Override
    public Spot addUI() throws ExitException {
        printHead("Add Spot Menu");
        String name;
        String location;

        name = inputString("이름");

        location = inputString("지역");
        return new Spot(name, location);
    }

    @Override
    public Spot deleteUI(List<Spot> list) throws ExitException, EmptyListException {
        printHead("Delete Spot Menu");
        return selectOneSpot(list);
    }

    @Override
    public void printList(List<Spot> list) {
        printSubList("Spot List");
        printContent(Spot.getHeader(),0);
        printDivider();
        for (Spot spot : list) {
            printContent(spot,6);
        }
        printBottom();
    }


    @Override
    public Spot updateUI(List<Spot> list) throws ExitException, EmptyListException {
        printHead("Change Spot Menu");

        Spot Spot = selectOneSpot(list);
        if(Spot == null) {
            return null;
        }
        Spot.setName(inputString("이름"));
        Spot.setLocation(inputString("지역"));

        return Spot;
    }

    private Spot selectOneSpot(List<Spot> list) throws ExitException, EmptyListException {
        Spot Spot = null;

        if(list == null || list.size() ==0) {
            throw new EmptyListException();
        }
        printList(list);

        while (true) {
            String select = inputString("보관소 id");

            try {
                int id = Integer.parseInt(select);
                for (Spot selected : list) {
                    if (selected.getId() == id) {
                        Spot = selected;

                        break;
                    }
                }
                if (Spot != null) break;
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return Spot;
    }
}
