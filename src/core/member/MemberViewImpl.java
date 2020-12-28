package core.member;

import java.util.List;
import java.util.Scanner;

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
	public Member joinUI(List<Spot> list, MemberDao dao) {
		String id;
		String pw;
		String name;
		String phone;
		String email;
		int spot_id;

		while(true) {
			printHead("Join Menu");
			System.out.print("ID : ");
			id = sc.next().trim();
			
			//아이디 중복조건
			if(dao.hasId(id)) {
				System.out.println("중복된 아이디가 존재합니다. 다시 입력해 주세요.");
				continue;
			}
			
			System.out.println("비밀번호 4자리 이상 입력해 주세요.");
			System.out.print("PASSWORD : ");
			pw = sc.next().trim();
			
			//비밀번호 4자리 이상 조건
			if(pw.length() < 4) {
				System.out.println("조건에 부합하지 않으니 다시 입력해 주세요.");
				continue;
			}
			
			System.out.print("NAME : ");
			name = sc.next().trim();
			
			
			System.out.print("PHONE : ");
			phone = sc.next().trim();
			
			System.out.print("EMAIL : ");
			email = sc.next().trim();
			
			System.out.println("원하시는 보관소 id 번호를 입력하세요.");
			for (Spot spot : list) {
				System.out.println(spot);
			}
			System.out.print(" >> ");

			spot_id = Integer.parseInt(sc.next().trim());
			break;
		}

		return new Member(id, pw, name, phone, email, spot_id);
	}

	@Override
	public LoginInfo loginUI() {
		printHead("Login Menu");
		
		System.out.print("id : ");
		String id = sc.next().trim();

		System.out.print("password : ");
		String password = sc.next().trim();

		return new LoginInfo(id, password);
	}

	@Override
	public String userUI() {
		String[] commands = {"Change Info", "My Info", "In-Out", "History", "Charge", "Log Out"};
		return inputUserChoice("User Menu", commands);
	}
	
	/*회원정보*/
	@Override
	public String userUpdateUI() {
		String[] commands = {"Change Info", "Leave"};
		return inputUserChoice("User Information", commands);
	}

	@Override
	public String userRudUI() {
		String[] commands = {"Password", "Name", "Phone", "Email"};
		return inputUserChoice("Change Information", commands);
	}

	@Override
	public String updateObjectUI(String select) {
		System.out.print("수정하실 " + select + "의 내용을 입력 해 주세요 :");
		return sc.next().trim();
	}
	
	@Override
	public String userOutUI() {
		printHead("Sign Out");
		
		System.out.print("확인을 위해 비밀번호를 입력하세요 : ");

		return sc.next().trim();
	}
	

	@Override
	public int chargeMoneyUI() {
		printHead("Charge Cash");

		System.out.print("충전하실 금액을 입력해 주세요 : ");

		return sc.nextInt();
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
