package core.memberlog;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

import java.util.List;
import java.util.Scanner;

import core.common.InputValidator;

public class MemberLogViewImple implements MemberLogView {
	private final Scanner sc = new Scanner(System.in);
	
	@Override
	public String logIndex() {
		printSubject("Log Menu");
		String[] commands = {"회원기록삭제" , "회원출력", "종료"};
		return inputUserChoice(commands);
	}

	@Override
	public String deleteUI() {
		printSubject("Deleting User Record");
		printMessage("삭제를 원하시는 ID를 입력하세요.");
		return sc.next();
	}

	@Override
	public String printUI() {
		printSubject("Printing User Record");
		return inputUserChoice("전체" , "가입", "수정", "탈퇴");
	}

	@Override
	public void printAll(List<MemberLog> list) {
		for (MemberLog memberLog : list) {
			System.out.println(memberLog);
		}
	}
}
