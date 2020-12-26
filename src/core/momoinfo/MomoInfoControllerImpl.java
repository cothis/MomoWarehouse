package core.momoinfo;

import core.common.InputValidator;
import core.item.ItemController;
import core.member.Member;
import core.spot.SpotController;

import java.util.List;
import java.util.Optional;

public class MomoInfoControllerImpl implements MomoInfoController {

    private final SpotController spotCo;
    private final ItemController itemCo;
    private final MomoInfoDao dao;
    private final MomoInfoView view;

    private Member session;
    private Member selectedUser;

    public MomoInfoControllerImpl(SpotController spotCo, ItemController itemCo, MomoInfoDao dao, MomoInfoView view) {
        this.spotCo = spotCo;
        this.itemCo = itemCo;
        this.dao = dao;
        this.view = view;
    }

    private void selectUser() {
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
    }

    @Override
    public void inOutHistory(Member member) {
        session = member;
        selectUser();

        boolean exit = false;
        while(!exit) {
            String select = view.history();

            switch (select) {
                case "입고내역": {
                    List<MomoInfo> list = dao.find(HistoryOption.IN);
                    view.printList(list);
                    break;
                }
                case "출고내역": {
                    List<MomoInfo> list = dao.find(HistoryOption.OUT);
                    view.printList(list);
                    break;
                }
                case "전체보기": {
                    List<MomoInfo> list = dao.find(HistoryOption.ALL);
                    view.printList(list);
                    break;
                }
                case "종료":
                    exit = true;
                    break;
            }
        }
    }

}
