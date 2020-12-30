package core.momoinfo;

import core.common.exception.*;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.DetailsOption;
import core.momoinfo.option.InOutOption;
import core.momoinfo.statistcs.TotalPayment;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static core.ApplicationConfig.*;
import static core.common.CommonView.*;
import static core.momoinfo.option.DetailsOption.*;

public class MomoInfoControllerImpl implements MomoInfoController {

    private final MomoInfoDao dao;
    private final MomoInfoView view;

    private Member session;

    public MomoInfoControllerImpl(MomoInfoDao dao, MomoInfoView view) {
        this.dao = dao;
        this.view = view;
    }

    private void selectUser() throws ExitException {
        Member selectedUser;
        if (session.getGrade().equals("USER")) {
            selectedUser = session;
        } else {
            List<Member> list = dao.findUser();
            Optional<Member> member = view.selectUser(list);
            selectedUser = member.orElse(session);
        }
        dao.setSelectedUser(selectedUser);
    }

    @Override
    public void inOutMenu(Member member) throws Exception {
        session = member;
        selectUser();

        boolean exit = false;
        while(!exit) {
            InOutOption select = view.inOut();

            switch (select) {
                case IN_SPOT: {
                    List<Item> read = getItemController().read();
                    if (read.size() > 0) {
                        Optional<Item> item = view.selectItem(read);
                        item.ifPresent(dao::create);
                    }
                    printMessage("입고가 정상적으로 완료되었습니다.");
                    break;
                }
                case OUT_SPOT: {
                    try {
                        List<MomoInfo> inItems = dao.find(IN_DETAILS);
                        if (inItems.size() == 0) throw new NoIncomingException();

                        Optional<MomoInfo> momoInfo = view.selectOutItem(inItems);
                        if (!momoInfo.isPresent()) break;

                        MomoInfo momoItem = momoInfo.get();
                        int payPrice = calculateStoragePrice(momoItem);
                        int newMoney = session.getCash() - payPrice;
                        if (newMoney < 0) {
                            throw new LessMoneyException(payPrice, session.getCash());
                        }

                        getMemberController().updateCash(newMoney);
                        dao.update(momoItem, payPrice);
                        printMessage("결제금액 : " + payPrice + ", 현재잔액 : " + session.getCash());
                        printMessage("출고가 정상적으로 완료되었습니다.");
                    } catch (LessMoneyException | ExitException | NoIncomingException e) {
                        noticeError(e);
                    }

                    break;
                }
                case EXIT_SPOT: {
                    exit = true;
                    break;
                }
            }
        }
    }

    private int calculateStoragePrice(MomoInfo momoInfo) {
        int priceByHour = momoInfo.getPriceByHour();
        Date inTime = momoInfo.getInTime();
        Date outTime = new Date(System.currentTimeMillis());
        int elapsedTime = (int) ((outTime.getTime() - inTime.getTime()) / 1000);
        int elapsedHour = elapsedTime / 3600; //원래 3600인데 임시로 분당 요금으로 처리

        return priceByHour * elapsedHour;
    }

    @Override
    public void inOutDetails(Member member) throws ExitException {
        session = member;
        selectUser();

        boolean exit = false;
        while(!exit) {
            DetailsOption select = view.details();

            switch (select) {
                case IN_DETAILS: {
                    List<MomoInfo> list = dao.find(IN_DETAILS);
                    view.printList(list, "Incoming Details");
                    break;
                }
                case OUT_DETAILS: {
                    List<MomoInfo> list = dao.find(OUT_DETAILS);
                    view.printList(list, "Outgoing Details");
                    break;
                }
                case ALL_DETAILS: {
                    List<MomoInfo> list = dao.find(ALL_DETAILS);
                    view.printList(list, "In Out Details");
                    break;
                }
                case EXIT_DETAILS: {
                    exit = true;
                    break;
                }
            }
        }
    }

    @Override
    public void checkHasIncomingByUser(Member session) throws HasIncomingException {
        dao.checkHasIncomingByUser(session);
    }

    @Override
    public void statistics(Member member) throws EmptyListException, ExitException {
        session = member;
        selectUser();

        boolean exit = false;
        while(!exit){
            String select = view.staticMenu();

            switch (select){
                case "TOTAL" : {
                    List<TotalPayment> list = dao.findTotalPaymentByUser();
                    view.printTotalPaymentStatistics(list);
                    break;
                }
                case "MONTHLY" : {
                    List<TotalPayment> list = dao.findMonthlyPaymentByUser();
                    view.printMonthlyPaymentStatistics(list, session);
                    break;
                }
                case "EXIT": {
                    exit = true;
                    break;
                }
            }
        }
    }
}
