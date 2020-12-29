package core.momoinfo;

import core.common.exception.ExitException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.HistoryOption;
import core.momoinfo.option.InOutOption;
import core.momoinfo.statistcs.TotalPayment;

import java.util.List;
import java.util.Optional;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;
import static core.momoinfo.option.HistoryOption.*;
import static core.momoinfo.option.InOutOption.*;

public class MomoInfoViewImpl implements MomoInfoView {

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
    public void printList(List<MomoInfo> list, String header) {
        setTempLength(150);
        printSubList(header);
        printContent(MomoInfo.getHeader(), 0);
        printDivider();
        for (MomoInfo momoInfo : list) {
            printContent(momoInfo, 5);
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
            try {
                String select = inputString("아이템 ID");

                Optional<Item> any = read.stream()
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
    public Optional<MomoInfo> selectOutItem(List<MomoInfo> inItems) throws ExitException {
        printList(inItems, "Incoming Items");

        while(true) {
            try {
                String select = inputString("출고할 MOMO ID");

                Optional<MomoInfo> any = inItems.stream()
                        .filter(momoInfo -> momoInfo.getMomoId() == Integer.parseInt(select))
                        .findAny();
                if (!any.isPresent()) {
                    throw new IllegalStateException("잘못 입력하셨습니다.");
                }
                return any;
            } catch (NumberFormatException e) {
                System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    @Override
    public void printTotalPaymentStatistics(List<TotalPayment> list) {
        printSubList("Total Payment Statistics");
        printContent(TotalPayment.getHeader(), 0);
        printDivider();
        for (TotalPayment totalPayment : list) {
            printContent(totalPayment,0);
        }
        printBottom();
    }

    @Override
    public void printMonthlyPaymentStatistics(List<TotalPayment> list, Member session) {
        setTempLength(220);
        printSubList("Monthly Payment Statistics");
        printContent(TotalPayment.getMonthlyHeader(), 0);
        printDivider();
        for (int i = 0; i < list.size() - 1; i++) {
            TotalPayment totalPayment = list.get(i);
            printContent(totalPayment.getMonthlyDataString(), 0);
        }
        if (session.getGrade().equalsIgnoreCase("ADMIN")) {
            printDivider();
            printContent(list.get(list.size()-1).getMonthlyDataString(), 0);
        }
        printBottom();
    }

    @Override
    public String staticMenu() {
        String[] commands = {"Total", "Monthly"};
        return inputUserChoice("Statistics Menu", commands);
    }
}
