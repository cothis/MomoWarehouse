package core.member;

import core.common.exception.*;

public interface MemberController {
		
	//1.회원가입 2.로그인 3.종료
	void indexMenu(); 
	
	//가입 : 회원정보 입력받고 Member생성해서 Dao addMember에 넣기
	void join();
	
	//로그인 : view에서 id/password 입력받고 dao에서 셀렉트해서 return된 결과로 menu 호출
	// view.loginUI -> dao.select(id, pw), 인스턴스변수에 값 넣기 -> userMenu / adminMenu 호출 -> 끝나면 index
	void login();
	
	//1.회원정보 수정 2.입출고 3.입출고내역 4.충전 5.통계 6.로그아웃
	void userMenu(); //회원정보 출력하고 메뉴 보여주기
	
	//userMenu - 1.회원정보수정 - 정보수정, 탈퇴
	void changeUserInfoMenu() throws Exception;

	// view(chargeMoneyUI : int) -> dao/memvo 적용
	void chargeMoney() throws ExitException, ChargeMoneyException;
	
	//1.물건관리 2.SPOT관리 3.회원로그 4.입출고내역 5.로그아웃
	//1. 아이템컨트롤러 메뉴 호출
	//2. SPOT컨트롤러 메뉴 호출
	//3. 회원로그 메소드 호출
	//4. 모모인포컨트롤러 메뉴호출
	//5. 종료
	void adminMenu();

	//가격을 입력받고 결제 처리 후에 성공여부 리턴
	void updateCash(int newMoney) throws Exception;
}
