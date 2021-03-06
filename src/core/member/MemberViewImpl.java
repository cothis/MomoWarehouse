package core.member;

import java.util.List;

import core.common.exception.ExitException;
import core.common.exception.PasswordFailException;
import core.member.option.InfoItem;
import core.spot.Spot;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;
import static core.member.option.InfoItem.*;

public class MemberViewImpl implements MemberView {
	@Override
	public String index() {
		String[] commands = {"Join", "Log In"};
		return inputUserChoice("Main Menu", commands);
	}

	@Override
	public Member joinUI(List<Spot> list, MemberDao dao) throws Exception {
		String id = null;
		String pw;
		String name;
		String phone;
		String email;
		int spot_id;

		while(true) {
			try {
				printHead("Join Menu");
				if (id == null) {
					id = inputString("ID");
				}
				//아이디 중복조건
				if(dao.hasId(id)) {
					id = null;
					throw new IllegalStateException("중복된 아이디가 존재합니다. 다시 입력해 주세요.");
				}

				System.out.println("비밀번호 4자리 이상 입력해 주세요.");
				pw = inputString("PASSWORD");
				//비밀번호 4자리 이상 조건
				if(pw.length() < 4) {
					throw new IllegalStateException("조건에 부합하지 않으니 다시 입력해 주세요.");
				}
				name = inputString("NAME");
				phone = inputString("PHONE");
				email = inputString("EMAIL");

				printSubList("SELECT SPOT");
				printContent(Spot.getHeader(), 0);
				printDivider();
				for (Spot spot : list) {
					printContent(spot,6);
				}
				printBottom();
				spot_id = Integer.parseInt(inputString("보관소 id"));
				break;
			} catch (IllegalStateException e) {
				printMessage(e.getMessage());
			}
		}
		return new Member(id, pw, name, phone, email, spot_id);
	}

	@Override
	public LoginInfo loginUI() throws Exception {
		printHead("Login Menu");

		String id = inputString("id");
		String password = inputString("password");

		return new LoginInfo(id, password);
	}

	@Override
	public String userUI() {
		String[] commands = {"Edit Profile", "My Info", "Charge", "In-Out", "Details", "Statistics", "Log Out"};
		return inputUserChoice("User Menu", commands);
	}
	
	/*회원정보*/
	@Override
	public String changeInfoMenu() {
		String[] commands = {"Change Info", "Leave"};
		return inputUserChoice("Edit Profile Menu", commands);
	}

	@Override
	public InfoItem selectInformationToChange() {
		String[] commands = {PW.name(), NAME.name(), PHONE.name(), EMAIL.name(), EXIT.name()};
		String selectedItem = inputUserChoice("Change Information", commands);
		return InfoItem.valueOf(selectedItem);
	}

	@Override
	public String inputSelectedInformation(InfoItem infoItem) throws ExitException {
		return inputString(infoItem.name());
	}
	
	@Override
	public void checkSingOutPassword(Member session) throws ExitException, PasswordFailException {
		printHead("Sign Out");
		String pass = inputString("password");

		if (!pass.equals(session.getPw())) {
			throw new PasswordFailException();
		}
	}
	

	@Override
	public int chargeMoneyUI() throws ExitException, IllegalStateException {
		printHead("Charge Cash");

		int updateMoney = Integer.parseInt(inputString("충전하실 금액"));
		if (updateMoney <= 0) {
			throw new IllegalStateException("올바르지 않은 금액입니다.");
		}
		return updateMoney;
	}
	
	
	
	/*관리자 메뉴*/
	@Override
	public String adminUI() {
		setTempLength(105);
		String[] commands = {"Manage Item", "Manage Spot", "Member Log", "Member List", "Details", "Statistics", "Log Out"};
		return inputUserChoice("Admin Menu", commands);
	}

	@Override
	public void printAll(List<Member> list) {

		setTempLength(110);
		printSubList("Member List");
		printContent(Member.getHeader(), 2);
		printDivider();
		for (Member member : list) {
			printContent(member, 2);
		}
		printBottom();
	}

	@Override
	public void printMemberInfo(Member member) {
		printMessage(member.getUserInfo());
	}
}
