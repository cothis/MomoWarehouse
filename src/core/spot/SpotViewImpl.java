package core.spot;

import core.common.CommonView;
import core.common.InputValidator;

import java.util.List;
import java.util.Scanner;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

public class SpotViewImpl implements SpotView {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String spotIndex() {
        String[] commands = {"Add", "Delete", "View", "Change"};
        return inputUserChoice("Spot Menu", commands);
    }

    @Override
    public Spot addUI() throws Exception {
        printHead("Add Spot Menu");
        String name;
        String location;

        name = inputString("이름");

        location = inputString("지역");
        return new Spot(name, location);
    }

    @Override
    public Spot deleteUI(List<Spot> list) throws Exception {
        printHead("Delete Spot Menu");
        return selectOneSpot(list);
    }

    @Override
    public void printList(List<Spot> list) {
        for (Spot spot : list) {
            System.out.println(spot);
        }
    }

    @Override
    public Spot updateUI(List<Spot> list) throws Exception {
        printHead("Change Spot Menu");

        Spot Spot = selectOneSpot(list);
        if(Spot == null) {
            return null;
        }
        Spot.setName(inputString("이름"));
        Spot.setLocation(inputString("지역"));

        return Spot;
    }

    private Spot selectOneSpot(List<Spot> list) throws Exception {
        Spot Spot = null;

        if(list == null || list.size() ==0) {
            System.out.println("보관소가 없습니다");
            return null;
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
