package core.memberlog;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

import java.util.List;
import core.common.exception.ExitException;

public class MemberLogViewImpl implements MemberLogView {

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
		setTempLength(160);
		printSubList("Member Log");
		printContent(MemberLog.getHeader(), 8);
		printDivider();
		for (MemberLog memberLog : list) {
			printContent(memberLog, 8);
		}
		printBottom();
	}
}
