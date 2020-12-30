package core.member;

import core.common.exception.ExitException;
import core.common.exception.PasswordFailException;
import core.member.option.InfoItem;
import core.spot.Spot;

import java.util.List;

public interface MemberView {
	
	String index();
	Member joinUI(List<Spot> list, MemberDao dao) throws Exception;
	LoginInfo loginUI() throws Exception; //배열에 id , password 받아서 출력
	
	String userUI(); //메뉴 출력하고  선택값 리턴
	
	//userUI - 1. 회원정보 수정
	String changeInfoMenu(); //회원정보 수정 메뉴 전체 화면
	InfoItem selectInformationToChange();
	String inputSelectedInformation(InfoItem select) throws ExitException; // 회원정보 수정사항 선택 후 수정내용 return
	void checkSingOutPassword(Member session) throws ExitException, PasswordFailException; // 비밀번호 입력 후 비밀번호 맞을 시, 회원 탈퇴됨

	int chargeMoneyUI() throws ExitException, IllegalStateException; //금액 충전 및 충전할 금액 리턴
	
	String adminUI(); //admin메뉴 출력하고 선택값 리턴

	void printAll(List<Member> list);

	void printMemberInfo(Member member);
	
}
