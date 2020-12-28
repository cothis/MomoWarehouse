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
		printSubject("Main Menu");
		return inputUserChoice("회원가입", "로그인");
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
			printSubject("Join Menu");
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
			System.out.print(list.toString() + " :");
			spot_id = Integer.parseInt(sc.next().trim());
			
			System.out.println("정상적으로 가입이 완료되었습니다.");
			break;
		}

		return new Member(id, pw, name, phone, email, spot_id);
	}

	@Override
	public LoginInfo loginUI() {
		printSubject("Login Menu");
		
		System.out.print("id : ");
		String id = sc.next().trim();

		System.out.print("password : ");
		String password = sc.next().trim();

		return new LoginInfo(id, password);
	}

	@Override
	public String userUI() {
		printSubject("User Menu");

		return inputUserChoice("회원정보수정", "나의정보", "입출고", "입출고내역", "충전", "로그아웃");
	}
	
	/*회원정보*/
	@Override
	public String userUpdateUI() {
		printSubject("User Information");
		return inputUserChoice("정보수정", "탈퇴");
	}

	@Override
	public String userRudUI() {
		printSubject("Change Information");
		return inputUserChoice("비밀번호", "이름", "전화번호", "이메일");
	}

	@Override
	public String updateObjectUI(String select) {
		System.out.print("수정하실 " + select + "의 내용을 입력 해 주세요 :");
		return sc.next().trim();
	}
	
	@Override
	public String userOutUI() {
		printSubject("Sign Out");
		
		System.out.print("확인을 위해 비밀번호를 입력하세요 : ");

		return sc.next().trim();
	}
	

	@Override
	public int chargeMoneyUI() {
		printSubject("Charge Cash");
		
		System.out.print("충전하실 금액을 입력해 주세요 : ");

		return sc.nextInt();
	}
	
	
	
	/*관리자 메뉴*/
	@Override
	public String adminUI() {
		printSubject("ADMIN Menu");

		return inputUserChoice("물건관리", "SPOT관리", "회원로그", "회원목록" , "입출고내역", "로그아웃");
	}

	@Override
	public void print(String str) {
		System.out.println(str);
	}
}
