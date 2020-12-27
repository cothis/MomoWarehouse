package core.momoinfo;

import core.ApplicationConfig;
import core.item.Item;
import core.item.ItemController;
import core.member.Member;
import core.momoinfo.option.HistoryOption;
import core.momoinfo.option.InOutOption;
import core.spot.SpotController;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static core.ApplicationConfig.*;

public class MomoInfoControllerImpl implements MomoInfoController {

    private final MomoInfoDao dao;
    private final MomoInfoView view;

    private Member session;

    public MomoInfoControllerImpl(MomoInfoDao dao, MomoInfoView view) {
        this.dao = dao;
        this.view = view;
    }

    private void selectUser() {
        Member selectedUser;
        if (session.getGrade().equals("USER")) {
            selectedUser = session;
        } else {
            List<Member> list = dao.findUser();
            Optional<Member> member = view.selectUser(list);
            selectedUser = member.orElseGet(() -> session);
        }
        dao.setSelectedUser(selectedUser);
    }

    @Override
    public void inOutMenu(Member member) {
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
                    break;
                }
                case OUT_SPOT: {
                    List<MomoInfo> inItems = dao.find(HistoryOption.IN_HISTORY);
                    if (inItems.size() == 0) break;

                    Optional<MomoInfo> momoInfo = view.selectOutItem(inItems);
                    if (!momoInfo.isPresent()) break;

                    MomoInfo momoItem = momoInfo.get();
                    int payPrice = calculateStoragePrice(momoItem);
                    int newMoney = session.getCash() - payPrice;
                    if (newMoney >= 0) {
                        if (getMemberController().updateCash(newMoney)) {
                            dao.update(momoItem);
                        }
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
        int elapsedHour = elapsedTime / 60; //원래 3600인데 임시로 분당 요금으로 처리

        return priceByHour * elapsedHour;
    }

    @Override
    public void inOutHistory(Member member) {
        session = member;
        selectUser();

        boolean exit = false;
        while(!exit) {
            HistoryOption select = view.history();

            switch (select) {
                case IN_HISTORY: {
                    List<MomoInfo> list = dao.find(HistoryOption.IN_HISTORY);
                    view.printList(list);
                    break;
                }
                case OUT_HISTORY: {
                    List<MomoInfo> list = dao.find(HistoryOption.OUT_HISTORY);
                    view.printList(list);
                    break;
                }
                case ALL_HISTORY: {
                    List<MomoInfo> list = dao.find(HistoryOption.ALL_HISTORY);
                    view.printList(list);
                    break;
                }
                case EXIT_HISTORY:
                    exit = true;
                    break;
            }
        }
    }

}
