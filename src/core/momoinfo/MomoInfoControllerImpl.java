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
    public void inOutMenu(Member session) throws Exception {
        this.session = session;
        selectUser();

        boolean exit = false;
        while(!exit) {
            InOutOption select = view.inOut();

            switch (select) {
                case IN_SPOT: {
                    try {
                        Item item = getItemController().selectItem();

                        dao.create(item);
                        printMessage("입고가 정상적으로 완료되었습니다.");
                    } catch (ExitException | EmptyListException e) {
                        noticeError(e);
                    }
                    break;
                }
                case OUT_SPOT: {
                    try {
                        List<MomoInfo> inItems = dao.find(IN_DETAILS);
                        MomoInfo momoItem = view.selectOutItem(inItems);

                        int payPrice = calculateStoragePrice(momoItem);
                        getMemberController().updateCashToPayment(payPrice);

                        dao.update(momoItem, payPrice);

                        printMessage("결제금액 : " + payPrice + ", 현재잔액 : " + this.session.getCash());
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
        int elapsedHour = elapsedTime / 3600;

        return priceByHour * elapsedHour;
    }

    @Override
    public void inOutDetails(Member session) throws ExitException {
        this.session = session;
        selectUser();

        while(true) {
            DetailsOption select = view.details();

            if (select == EXIT_DETAILS) {
                break;
            }

            List<MomoInfo> list = dao.find(select);
            view.printList(list, select.getHeader());
        }
    }

    @Override
    public void checkHasIncomingByUser(Member session) throws HasIncomingException {
        dao.checkHasIncomingByUser(session);
    }

    @Override
    public void statistics(Member session) throws EmptyListException, ExitException {
        this.session = session;
        selectUser();

        boolean exit = false;
        while(!exit){
            String select = view.statisticsMenu();

            switch (select){
                case "TOTAL" : {
                    List<TotalPayment> list = dao.findTotalPaymentByUser();
                    view.printTotalPaymentStatistics(list);
                    break;
                }
                case "MONTHLY" : {
                    List<TotalPayment> list = dao.findMonthlyPaymentByUser();
                    view.printMonthlyPaymentStatistics(list, this.session);
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
