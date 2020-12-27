package core.member;

import core.item.ItemController;
import core.momoinfo.MomoInfoController;
import core.spot.SpotController;

public class MemberControllerImpl implements MemberController{
	
	private final MemberView view;
	private final MemberDao dao;
	private final MomoInfoController momoInfoController;
	private final ItemController itemController;
	private final SpotController spotController;
	private Member session;

	public MemberControllerImpl(MemberView view,
								MemberDao dao,
								MomoInfoController momoInfoControl,
								ItemController itemController,
								SpotController spotController) {
		this.view = view;
		this.dao = dao;
		this.momoInfoController = momoInfoControl;
		this.itemController = itemController;
		this.spotController = spotController;
	}
	
	@Override
	public void indexMenu() {
		//boolean b = true;
		while(true) {
			String select = view.index();
			
			//1.회원가입 2.로그인 3.종료
			if("회원가입".equals(select)) {
				session = view.joinUI(spotController.findAll());
				join(session); //입력받아 JOIN에서 MEMBER 객체 DB에 저장
			}else if("로그인".equals(select)) {
				String[] userInfo = view.loginUI(); //로그인해서 ID,PW 배열에 받음
				session = dao.selecte(userInfo); //다오 셀렉 유저I,P 넣어 찾아서 멤버객체로 받음
				if(session != null) {
					//String grade = member.getGrade();
					login(session); //해당 유저 멤버객체 매개변수
				}
				
			}else if("종료".equals(select)) {
				break;
			}
		}
			
	}


	@Override
	public void join(Member member) {
		int result = dao.insert(member);
		System.out.println("처리건수 : "+ result);
	}

	@Override
	public void login(Member member) {
		boolean loopcheck = true;
		String grade = member.getGrade();
		System.out.println("로그인 완료! " + member.toString());

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
				case "회원정보수정":
					exit = !userUpdating(session);
					break;
				case "입출고":
					momoInfoController.inOutMenu(session);
					break;
				case "입출고내역":
					momoInfoController.inOutHistory(session);
					break;
				case "충전":
					chargeMoney(session);
					break;
				case "로그아웃":
					exit = true;
					break;
			}
		}
	}
	
	
	/*회원정보 수정*/
	@Override
	public boolean userUpdating(Member member) {
		boolean loopcheck = true;
		int result = 0;

		String id = member.getMemberId();
		
		String userMenuSelect = view.userUpdateUI();
		
		if(userMenuSelect.equals("정보수정")) {
			//정보수정 메뉴 뜨고 입력값 받음
			String userRudSelect = view.userRudUI();
			//정보수정 후 처리건수 int값으로 받음
			result = userUpdatingInput(userRudSelect, id);
			
			if(result > 0) {
				System.out.println("처리건수 : " + result);
			}else {
				System.out.println("다시 입력해 주세요.");
			}
			
		}else if(userMenuSelect.equals("탈퇴")) {
			String pw = view.userOutUI(id);
			if(pw.equals(member.getPw())) {
				dao.delete(id);
				loopcheck = false;
				System.out.println("탈퇴완료. 안녕히가십시오...");
			}else {
				System.out.println("비밀번호가 일치하지 않습니다.");
			}
		}
		
		return loopcheck;
		
	}

	//정보수정 -> 값 입력 -> dao에서 수정처리
	@Override
	public int userUpdatingInput(String userRudSelect, String id) {
		//입력값의 수정내용을 받음
		String userInfoUp = view.updateObjectUI(userRudSelect);
		int result = 0;
		
		if(userRudSelect.equals("비밀번호")) {
			session.setPw(userInfoUp);
			result = dao.update(session, "PW", id);
		}else if(userRudSelect.equals("이름")) {
			session.setName(userInfoUp);
			result = dao.update(session, "NAME", id);
		}else if(userRudSelect.equals("전화번호")) {
			session.setPhone(userInfoUp);
			result = dao.update(session, "PHONE", id);
		}else if(userRudSelect.equals("이메일")) {
			session.setEmail(userInfoUp);
			result = dao.update(session, "EMAIL", id);
		}
		
		return result;
	}
	

	@Override
	public void chargeMoney(Member member) {
		int originCash = member.getCash(); //기존 금액
		int updatingCash = view.chargeMoneyUI(); //충전금액
		
		if(updatingCash > 0) {
			member.setCash(originCash + updatingCash);
			dao.updatingCash(member);
			System.out.println(updatingCash+"원을 충전 완료하였습니다. 총 금액 : " + member.getCash());
		}else {
			System.out.println("올바르지 않은 금액입니다. 다시 입력하세요.");
		}
		
	}
	
	
	/*관리자메뉴*/
	@Override
	public void adminMenu() {
		boolean exit = false;

		while (!exit) {
			String select = view.adminUI();
			switch (select) {
				case "물건관리": {
					itemController.itemMenu();
					break;
				}
				case "SPOT관리": {
					spotController.spotMenu();
					break;
				}
				case "회원로그": {
					break;
				}
				case "입출고내역": {
					momoInfoController.inOutHistory(session);
					break;
				}
				case "로그아웃": {
					exit = true;
					break;
				}
			}
		}
	}
}
