package core.member;

import core.common.exception.ChargeMoneyException;
import core.common.exception.EmptyListException;
import core.common.exception.ExitException;

import java.util.List;

import static core.ApplicationConfig.*;
import static core.common.CommonView.*;

public class MemberControllerImpl implements MemberController {

    private final MemberView view;
    private final MemberDao dao;
    private Member session;
    private LoginInfo loginInfo;

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
            loginInfo = view.loginUI();//로그인해서 ID,PW 배열에 받음
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
        printMessage("로그인 완료! ");
        printMessage(session.userInfoPrint());

        boolean exit = false;
        while (!exit) {
            String select = view.userUI();
            try {
                switch (select) {
                    case "EDIT PROFILE":
                        exit = userUpdating(session);
                        break;
                    case "MY INFO":
                        myInfo(loginInfo);
                        break;
                    case "IN-OUT":
                        getMomoInfoController().inOutMenu(session);
                        break;
                    case "HISTORY":
                        getMomoInfoController().inOutHistory(session);
                        break;
                    case "CHARGE":
                        chargeMoney();
                        break;
                    case "LOG OUT":
                        exit = true;
                        break;
                }
            } catch (Exception exception) {
                noticeError(exception);
            }
        }
    }


    /*회원정보 수정*/
    @Override
    public boolean userUpdating(Member member) {
        boolean signOut = false;

        String select = view.changeInfoMenu();

        switch (select) {
            case "CHANGE INFO": {
                try {
                //정보수정 메뉴 뜨고 입력값 받음
                    String targetInformation = view.selectInformationToChange();
                    if("EXIT".equals(targetInformation)) {
                        break;
                    }

                    userUpdatingInput(targetInformation);
                    printMessage("변경되었습니다.");
                } catch (ExitException e) {
                    noticeError(e);
                }
                break;
            }
            case "LEAVE": {
                try {
                    String pw = view.userOutUI();
                    if (pw.equals(member.getPw())) {
                        getMomoInfoController().checkHasIncomingByUser(session);
                        dao.delete(session);
                        printMessage("탈퇴완료. 안녕히가십시오...");
                        signOut = true;
                    } else {
                        throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
                    }
                } catch (Exception exception) {
                    noticeError(exception);
                }
                break;
            }
            case "EXIT": {
                break;
            }
        }

        return signOut;
    }

    //나의정보
    public void myInfo(LoginInfo loginInfo) {
        session = dao.select(loginInfo);
        printMessage(session.userInfoPrint());
    }

    //정보수정 -> 값 입력 -> dao에서 수정처리
    @Override
    public void userUpdatingInput(String selectInformationToChange) throws ExitException {
        //입력값의 수정내용을 받음
        String selectedInfo = view.inputSelectedInformation(selectInformationToChange);
        Member member = new Member(session);

        switch (selectInformationToChange) {
            case "PASSWORD":
                member.setPw(selectedInfo);
                dao.update(member, "PW");
                break;
            case "NAME":
                member.setName(selectedInfo);
                dao.update(member, "NAME");
                break;
            case "PHONE":
                member.setPhone(selectedInfo);
                dao.update(member, "PHONE");
                break;
            case "EMAIL":
                member.setEmail(selectedInfo);
                dao.update(member, "EMAIL");
                break;
        }
        session = member;
        loginInfo = new LoginInfo(session.getMemberId(), session.getPw());
    }

    @Override
    public void chargeMoney() {
        int originCash = session.getCash(); //기존 금액
        int updatingCash = 0; //충전금액
        try {
            updatingCash = view.chargeMoneyUI();
            int newCash = originCash + updatingCash;
            if (dao.updatingCash(session, newCash)) {
                printMessage(updatingCash + "원을 충전 완료하였습니다. 총 금액 : " + session.getCash());
            }
        } catch (Exception exception) {
            noticeError(exception);
        }
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
                        getMomoInfoController().inOutHistory(session);
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
    public boolean updateCash(int newMoney) throws ChargeMoneyException {
        return dao.updatingCash(session, newMoney);
    }

    public void read(){
        List<Member> list = dao.findAll();
        view.printAll(list);
    }
}
