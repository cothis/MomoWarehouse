package core.momoinfo;

import core.common.exception.ExitException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.HistoryOption;
import core.momoinfo.option.InOutOption;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;
import static core.momoinfo.option.HistoryOption.*;
import static core.momoinfo.option.InOutOption.*;

public class MomoInfoViewImpl implements MomoInfoView {

    private final Scanner sc = new Scanner(System.in);

    @Override
    public InOutOption inOut() {
        String select = inputUserChoice("InOut Menu", IN_SPOT.toString(), OUT_SPOT.toString(), EXIT_SPOT.toString());

        return parseInOutOption(select);
    }

    @Override
    public HistoryOption history() {
        String select = inputUserChoice("InOut History",
                IN_HISTORY.toString(),
                OUT_HISTORY.toString(),
                ALL_HISTORY.toString(),
                EXIT_HISTORY.toString());

        return parseHistoryOption(select);
    }

    @Override
    public void printList(List<MomoInfo> list) {
        setTempLength(130);
        printSubList("In out History");
        printContent(MomoInfo.getHeader(), 3);
        printDivider();
        for (MomoInfo momoInfo : list) {
            printContent(momoInfo, 4);
        }
        printBottom();
    }

    @Override
    public Optional<Member> selectUser(List<Member> list) throws ExitException {
        if (list == null || list.size() == 0) {
            System.out.println("저장된 유저가 없습니다.");
            return Optional.empty();
        }
        while (true) {
            System.out.println("확인할 유저의 행 번호를 선택해주세요.");

            int i = 1;
            for (Member member : list) {
                System.out.printf("%d. id: %s, name: %s%n", i, member.getMemberId(), member.getName());
                i++;
            }
            System.out.printf("%d. 전체보기%n", i);
            String select = inputString("확인할 유저의 행 번호");

            try {
                int rowNum = Integer.parseInt(select);
                if(rowNum == i) {
                    return Optional.empty();
                } else {
                    return Optional.of(list.get(rowNum - 1));
                }
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.\n");
            }
        }
    }

    @Override
    public Optional<Item> selectItem(List<Item> read) throws Exception {
        for (Item item : read) {
            System.out.println(item);
        }
        while (true) {
            Optional<Item> any = Optional.empty();
            try {
                String select = inputString("아이템 ID");

                any = read.stream()
                        .filter(item -> item.getItemId() == Integer.parseInt(select))
                        .findAny();
                if (!any.isPresent()) {
                    throw new IllegalStateException("잘못 입력하셨습니다.");
                }
                return any;
            } catch (IllegalStateException e) {
                noticeError(e);
            }
        }
    }

    @Override
    public Optional<MomoInfo> selectOutItem(List<MomoInfo> inItems) {
        for (MomoInfo inItem : inItems) {
            System.out.println(inItem);
        }
        while(true) {
            try {
                System.out.println("출고할 MOMO ID를 입력해주세요");
                System.out.print(">> ");
                String select = sc.nextLine();
                if (select.equals("exit")) {
                    return Optional.empty();
                }
                Optional<MomoInfo> any = inItems.stream()
                        .filter(momoInfo -> momoInfo.getMomoId() == Integer.parseInt(select))
                        .findAny();
                if (!any.isPresent()) {
                    throw new IllegalStateException("잘못 입력하셨습니다.");
                }
                return any;
            } catch (Exception e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }
}
