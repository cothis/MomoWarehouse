package core.memberlog;

import core.common.exception.ExitException;

import java.util.List;

public interface MemberLogView {
	String logIndex(); //1.회원기록삭제 2.회원출력 - 전체, 수정, 가입, 탈퇴 
	String deleteUI() throws ExitException;
	String printUI();
	void printAll(List<MemberLog> list);
}
