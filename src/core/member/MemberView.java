package core.member;

public interface MemberView {
	
	String index();
	Member joinUI();
	String[] loginUI(); //배열에 id , password 받아서 출력
	String userUI(Member member); //회원정보,메뉴 출력하고  선택값 리턴
	String adminUI(); //admin메뉴 출력하고 선택값 리턴
	Member changeInfoUI(); //유저정보 변경하고 변경된 값 리턴
	int chargeMoneyUI();//금액 충전 및 충전할 금액 리턴 
}
