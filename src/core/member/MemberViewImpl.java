package core.member;

import java.util.List;
import java.util.Scanner;

import core.common.exception.ExitException;
import core.spot.Spot;

import static core.common.CommonView.*;
import static core.common.InputValidator.*;

public class MemberViewImpl implements MemberView {
	private final Scanner sc = new Scanner(System.in);

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

				System.out.println("원하시는 보관소 id 번호를 입력하세요.");
				for (Spot spot : list) {
					System.out.println(spot);
				}
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
		String[] commands = {"Edit Profile", "My Info", "In-Out", "History", "Charge", "Log Out"};
		return inputUserChoice("User Menu", commands);
	}
	
	/*회원정보*/
	@Override
	public String changeInfoMenu() {
		String[] commands = {"Change Info", "Leave"};
		return inputUserChoice("Edit Profile Menu", commands);
	}

	@Override
	public String selectInformationToChange() {
		String[] commands = {"Password", "Name", "Phone", "Email"};
		return inputUserChoice("Change Information", commands);
	}

	@Override
	public String inputSelectedInformation(String select) throws Exception {
		return inputString(select);
	}
	
	@Override
	public String userOutUI() throws ExitException {
		printHead("Sign Out");

		return inputString("password");
	}
	

	@Override
	public int chargeMoneyUI() throws Exception {
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
		String[] commands = {"Manage Item", "Manage Spot", "Member Log", "Member List", "In-Out History", "Log Out"};
		return inputUserChoice("Admin Menu", commands);
	}

	@Override
	public void print(String str) {
		System.out.println(str);
	}
}
