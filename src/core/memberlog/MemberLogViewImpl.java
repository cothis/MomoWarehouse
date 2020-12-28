package core.memberlog;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

import java.util.List;
import java.util.Scanner;

import core.common.CommonView;
import core.common.InputValidator;
import core.common.exception.ExitException;

public class MemberLogViewImpl implements MemberLogView {
	private final Scanner sc = new Scanner(System.in);
	
	@Override
	public String logIndex() {
		String[] commands = {"Delete Log" , "Member List"};
		return inputUserChoice("Log Menu", commands);
	}

	@Override
	public String deleteUI() throws ExitException {
		printHead("Deleting User Record");
		return inputString("삭제 대상 Member ID");
	}

	@Override
	public String printUI() {
		String[] commands = {"All", "Join", "Update", "Leave"};
		return inputUserChoice("Printing User Record",commands);
	}

	@Override
	public void printAll(List<MemberLog> list) {
		setTempLength(110);
		printSubList("Member Log");
		printContent(MemberLog.getHeader(), 4);
		printDivider();
		for (MemberLog memberLog : list) {
			printContent(memberLog, 8);
		}
		printBottom();
	}
}
