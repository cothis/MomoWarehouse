package core.spot;

import core.common.CommonView;
import core.common.InputValidator;

import java.util.List;
import java.util.Scanner;

import static core.common.CommonView.*;

public class SpotViewImpl implements SpotView {
    private final Scanner sc = new Scanner(System.in);

    @Override
    public String spotIndex() {
        printSubject("보관소 관리 Menu");
        String[] commands = {"보관소추가", "보관소삭제", "보관소조회", "보관소변경"};
        return InputValidator.inputUserChoice(commands);
    }

    private String inputString(String queryName) {
        String result;
        while (true) {
            try {
                System.out.print(queryName + " : ");
                result = sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
        return result;
    }

    @Override
    public Spot addUI() {
        printSubject("보관소 추가 Menu");
        String name;
        String location;

        name = inputString("이름");

        location = inputString("지역");
        return new Spot(name, location);
    }

    @Override
    public Spot deleteUI(List<Spot> list) {
        printSubject("보관소 삭제 Menu");
        return selectOneSpot(list);
    }

    @Override
    public void printList(List<Spot> list) {
        for (Spot spot : list) {
            System.out.println(spot);
        }
    }

    @Override
    public Spot updateUI(List<Spot> list) {
        printSubject("보관소 변경 Menu");

        Spot Spot = selectOneSpot(list);
        if(Spot == null) {
            return null;
        }
        Spot.setName(inputString("이름"));
        Spot.setLocation(inputString("지역"));

        return Spot;
    }

    private Spot selectOneSpot(List<Spot> list) {
        Spot Spot = null;

        if(list == null || list.size() ==0) {
            System.out.println("보관소가 없습니다");
            return null;
        }
        printList(list);

        while (true) {
            System.out.print("변경할 보관소 id를 입력해주세요 : ");
            String select = sc.nextLine();
            if ("exit".equals(select)) {
                break;
            }

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
