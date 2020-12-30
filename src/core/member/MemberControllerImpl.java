package core.member;

import core.common.exception.*;
import core.member.option.InfoItem;

import java.util.List;

import static core.ApplicationConfig.*;
import static core.common.CommonView.*;

public class MemberControllerImpl implements MemberController {

    private final MemberView view;
    private final MemberDao dao;
    private Member session;

    public MemberControllerImpl(MemberView view, MemberDao dao) {
        this.view = view;
        this.dao = dao;
    }

    @Override
    public void indexMenu() {
        boolean exit = false;
        while (!exit) {
            session = null;
            String select = view.index();

            //1.회원가입 2.로그인 3.종료
            switch (select) {
                case "JOIN":
                    join();
                    break;
                case "LOG IN":
                    login();
                    break;
                case "EXIT":
                    exit = true;
                    break;
            }
        }
    }

    @Override
    public void join() {
        try {
            Member newMember = view.joinUI(getSpotController().findAll(), dao);
            dao.insert(newMember);//입력받아 JOIN에서 MEMBER 객체 DB에 저장
            printMessage("가입되었습니다.");
        } catch (Exception exception) {
            noticeError(exception);
        }
    }

    @Override
    public void login() {
        try {
            LoginInfo loginInfo = view.loginUI();//로그인해서 ID,PW 배열에 받음
            session = dao.select(loginInfo); //다오 셀렉 유저I,P 넣어 찾아서 멤버객체로 받음

            String grade = session.getGrade();
            if (grade.equals("USER")) {
                userMenu();
            } else if (grade.equals("ADMIN")) {
                adminMenu();
            }
        } catch (Exception exception) {
            noticeError(exception);
        }
    }

    /*회원메뉴*/
    @Override
    public void userMenu() {
        //"회원정보 수정", "입출고", "입출고내역", "충전", "로그아웃"
        printMessage("로그인 완료!");
        printMessage(session.getUserInfo());

        boolean exit = false;
        while (!exit) {
            String select = view.userUI();
            try {
                switch (select) {
                    case "EDIT PROFILE":
                        changeUserInfoMenu();
                        break;
                    case "MY INFO":
                        view.printMemberInfo(session);
                        break;
                    case "IN-OUT":
                        getMomoInfoController().inOutMenu(session);
                        break;
                    case "DETAILS":
                        getMomoInfoController().inOutDetails(session);
                        break;
                    case "CHARGE":
                        chargeMoney();
                        break;
                    case "STATISTICS":
                        getMomoInfoController().statistics(session);
                        break;
                    case "LOG OUT":
                        exit = true;
                        break;
                }
            } catch (SignOutException e) {
              noticeError(e);
              exit = true;
            } catch (Exception e) {
                noticeError(e);
            }
        }
    }

    /*회원정보 수정*/
    @Override
    public void changeUserInfoMenu() throws Exception {
        String select = view.changeInfoMenu();

        switch (select) {
            case "CHANGE INFO": {
                //정보수정 메뉴 뜨고 입력값 받음
                InfoItem infoItem = view.selectInformationToChange();
                if(infoItem == InfoItem.EXIT) {
                    throw new ExitException();
                }
                inputUserInfoItem(infoItem);
                printMessage("변경되었습니다.");
                break;
            }
            case "LEAVE": {
                view.checkSingOutPassword(session);
                getMomoInfoController().checkHasIncomingByUser(session);
                dao.delete(session);

                printMessage("탈퇴완료. 안녕히가십시오...");
                throw new SignOutException();
            }
        }
    }

    //정보수정 -> 값 입력 -> dao에서 수정처리
    private void inputUserInfoItem(InfoItem infoItem) throws ExitException {
        //입력값의 수정내용을 받음
        while (true) {
            try {
                String infoItemValue = view.inputSelectedInformation(infoItem);
                Member member = new Member(session);

                dao.update(member, infoItem, infoItemValue);
                session = member;
                break;
            } catch (IllegalStateException | PasswordLengthException e) {
                noticeError(e);
            }
        }
    }

    @Override
    public void chargeMoney() throws ExitException, ChargeMoneyException {
        int originCash = session.getCash(); //기존 금액
        int updatingCash; //충전금액

        updatingCash = view.chargeMoneyUI();
        int newCash = originCash + updatingCash;

        dao.updatingCash(session, newCash);
        printMessage(updatingCash + "원을 충전 완료하였습니다. 총 금액 : " + session.getCash());
    }

    /*관리자메뉴*/
    @Override
    public void adminMenu() {
        boolean exit = false;

        printMessage("관리자님 안녕하세요!");

        while (!exit) {

            try {
                String select = view.adminUI();
                switch (select) {
                    case "MANAGE ITEM": {
                        getItemController().itemMenu();
                        break;
                    }
                    case "MANAGE SPOT": {
                        getSpotController().spotMenu();
                        break;
                    }
                    case "MEMBER LOG": {
                        getMemberLogController().logMenu();
                        break;
                    }
                    case "MEMBER LIST": {
                        read();
                        break;
                    }
                    case "IN-OUT HISTORY": {
                        getMomoInfoController().inOutDetails(session);
                        break;
                    }
                    case "STATISTICS": {
                        getMomoInfoController().statistics(session);
                        break;
                    }
                    case "LOG OUT": {
                        exit = true;
                        break;
                    }
                }
            } catch (ExitException | EmptyListException e) {
                noticeError(e);
            }
        }
    }

    @Override
    public void updateCash(int newMoney) throws ChargeMoneyException {
        dao.updatingCash(session, newMoney);
    }

    public void read(){
        List<Member> list = dao.findAll();
        view.printAll(list);
    }
}
