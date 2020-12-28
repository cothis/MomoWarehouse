package core.member;

import static core.ApplicationConfig.*;
import static core.common.CommonView.*;

import java.util.List;

import core.ApplicationConfig;
import core.common.CommonView;

public class MemberControllerImpl implements MemberController{
	
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
		while(!exit) {
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
		Member newMember = view.joinUI(getSpotController().findAll(), dao);
		int result = dao.insert(newMember);//입력받아 JOIN에서 MEMBER 객체 DB에 저장
		if (result > 0) {
			printMessage("가입되었습니다.");
			printMessage("처리건수 : "+ result);
		} else {
			printMessage("잘 못 입력하셨습니다.");
		}
	}

	@Override
	public void login() {
		loginInfo = view.loginUI();//로그인해서 ID,PW 배열에 받음
		session = dao.select(loginInfo); //다오 셀렉 유저I,P 넣어 찾아서 멤버객체로 받음
		if (session == null) {
			return;
		}
		String grade = session.getGrade();
		view.print("로그인 완료! " + session);
		if(grade.equals("USER")) {
			userMenu();
		}else if(grade.equals("ADMIN")) {
			adminMenu();
		}

	}
	
	/*회원메뉴*/
	@Override
	public void userMenu() {
		//"회원정보 수정", "입출고", "입출고내역", "충전", "로그아웃"

		boolean exit = false;
		while (!exit) {
			String select = view.userUI();
			switch (select) {
				case "CHANGE INFO":
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
		}
	}
	
	
	/*회원정보 수정*/
	@Override
	public boolean userUpdating(Member member) {
		boolean signOut = false;
		int result = 0;
		String id = member.getMemberId();
		
		String userMenuSelect = view.userUpdateUI();
		
		if(userMenuSelect.equals("정보수정")) {
			//정보수정 메뉴 뜨고 입력값 받음
			String userRudSelect = view.userRudUI();
			if ("종료".equals(userRudSelect)) {
				return false;
			}
			//정보수정 후 처리건수 int값으로 받음
			result = userUpdatingInput(userRudSelect);
			
			if(result > 0) {
				view.print("처리건수 : " + result);
			}else {
				view.print("다시 입력해 주세요.");
			}
			
		}else if(userMenuSelect.equals("탈퇴")) {
			String pw = view.userOutUI();
			if(pw.equals(member.getPw())) {
				result = dao.delete(session);
				if(result > 0) {
					view.print("탈퇴완료. 안녕히가십시오...");
					signOut = true;
				}
			}else {
				view.print("비밀번호가 일치하지 않습니다.");
			}
		}
		return signOut;
	}
	
	//나의정보
	public void myInfo(LoginInfo loginInfo) {
		session = dao.select(loginInfo);
		view.print(session.toString());
	}

	//정보수정 -> 값 입력 -> dao에서 수정처리
	@Override
	public int userUpdatingInput(String userRudSelect) {
		//입력값의 수정내용을 받음
		String userInfoUp = view.updateObjectUI(userRudSelect);
		int result = 0;
		Member member = new Member(session);

		switch (userRudSelect) {
			case "PASSWORD":
				member.setPw(userInfoUp);
				result = dao.update(member, "PW");
				break;
			case "NAME":
				member.setName(userInfoUp);
				result = dao.update(member, "NAME");
				break;
			case "PHONE":
				member.setPhone(userInfoUp);
				result = dao.update(member, "PHONE");
				break;
			case "EMAIL":
				member.setEmail(userInfoUp);
				result = dao.update(member, "EMAIL");
				break;
		}
		if (result > 0) {
			session = member;
			loginInfo = new LoginInfo(session.getMemberId(), session.getPw());
		}
		
		return result;
	}

	@Override
	public void chargeMoney() {
		int originCash = session.getCash(); //기존 금액
		int updatingCash = view.chargeMoneyUI(); //충전금액
		
		if(updatingCash > 0) {
			int newCash = originCash + updatingCash;
			if (dao.updatingCash(session, newCash)) {
				view.print(updatingCash+"원을 충전 완료하였습니다. 총 금액 : " + session.getCash());
			}
		} else {
			view.print("올바르지 않은 금액입니다. 다시 입력하세요.");
		}
		
	}
	
	
	/*관리자메뉴*/
	@Override
	public void adminMenu() {
		boolean exit = false;

		printMessage("관리자님 안녕하세요!");

		while (!exit) {
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
					dao.findAll();
					break;
				}
				case "IN-OUT HISTORY": {
					getMomoInfoController().inOutHistory(session);
					break;
				}
				case "LOG OUT": {
					exit = true;
					break;
				}
			}
		}
	}

	@Override
	public boolean updateCash(int newMoney) {
		return dao.updatingCash(session, newMoney);
	}
}
