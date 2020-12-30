package core.momoinfo;

import core.common.Color;
import core.common.exception.EmptyListException;
import core.common.exception.ExitException;
import core.common.exception.NoIncomingException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.DetailsOption;
import core.momoinfo.option.InOutOption;
import core.momoinfo.statistcs.TotalPayment;

import java.util.List;
import java.util.Optional;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;
import static core.momoinfo.option.DetailsOption.*;
import static core.momoinfo.option.InOutOption.*;

public class MomoInfoViewImpl implements MomoInfoView {

    @Override
    public InOutOption inOut() {
        String select = inputUserChoice("InOut Menu", IN_SPOT.toString(), OUT_SPOT.toString(), EXIT_SPOT.toString());

        return parseInOutOption(select);
    }

    @Override
    public DetailsOption details() {
        String select = inputUserChoice("InOut Details",
                IN_DETAILS.toString(),
                OUT_DETAILS.toString(),
                ALL_DETAILS.toString(),
                EXIT_DETAILS.toString());

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
            noticeError(new Exception("저장된 유저가 없습니다."));
            return Optional.empty();
        }
        while (true) {
            setTempLength(80);
            printSubList("User List");
            String header = String.format("    %-4s        %-20s\t  %-20s    ", "No.", "Member ID", "Member Name");
            printContent(header, 2);
            printDivider();
            int i = 1;
            for (Member member : list) {
                String message = String.format("    %-4s        %-20s\t  %-20s    ", i + "", member.getMemberId(), member.getName());
                printContent(message, 2);
                i++;
            }
            printDivider();
            String message = String.format(Color.ANSI_PURPLE + "    %-4s        %-20s\t  " + Color.ANSI_RESET, 0, "ALL");
            printContent(message, -7);
            printBottom();

            String select = inputString("확인할 유저의 행 번호");

            try {
                int rowNum = Integer.parseInt(select);
                if(rowNum == 0) {
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
    public MomoInfo selectOutItem(List<MomoInfo> inItems) throws ExitException, NoIncomingException {
        if (inItems.size() == 0) throw new NoIncomingException();

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
                return any.get();
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
    public String statisticsMenu() {
        String[] commands = {"Total", "Monthly"};
        return inputUserChoice("Statistics Menu", commands);
    }
}
