package core.memberlog;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

import java.util.List;
import java.util.Scanner;

import core.common.InputValidator;

public class MemberLogViewImpl implements MemberLogView {
	private final Scanner sc = new Scanner(System.in);
	
	@Override
	public String logIndex() {
		String[] commands = {"Delete Log" , "Member List"};
		return inputUserChoice("Log Menu", commands);
	}

	@Override
	public String deleteUI() {
		printHead("Deleting User Record");
		printMessage("삭제를 원하시는 ID를 입력하세요.");
		return sc.next();
	}

	@Override
	public String printUI() {
		String[] commands = {"All", "Join", "Update", "Leave"};
		return inputUserChoice("Printing User Record",commands);
	}

	@Override
	public void printAll(List<MemberLog> list) {
		for (MemberLog memberLog : list) {
			System.out.println(memberLog);
		}
	}
}
