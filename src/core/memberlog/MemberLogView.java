package core.memberlog;

import java.util.List;

public interface MemberLogView {
	String logIndex(); //1.회원기록삭제 2.회원출력 - 전체, 수정, 가입, 탈퇴 
	String deleteUI();
	String printUI();
	void printAll(List<MemberLog> list);
}
