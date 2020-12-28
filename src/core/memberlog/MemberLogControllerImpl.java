package core.memberlog;

import java.util.List;

import core.member.Member;

public class MemberLogControllerImpl implements MemberLogController {
	MemberLogDao dao;
	MemberLogView view;
	Member session;
	
	public MemberLogControllerImpl(MemberLogDao dao, MemberLogView view) {
		this.dao = dao;
		this.view = view;
	}
	
	@Override
	public void logMenu() {
		boolean exit = false;
		while(!exit) {
			String select = view.logIndex();
			switch(select) {
				case "회원기록삭제" :
					delete();
					break;
				case "회원출력" : 
					read();
					break;
				case "종료" : 
					exit = true;
					break;
			}
		}
	}

	@Override
	public void delete() {
		String id = view.deleteUI();
		dao.delete(id);
	}

	@Override
	public void read() {
		String select = view.printUI();
		if(select.equals("exit")) {
			return;
		}
		List<MemberLog> list = dao.findUser(select);
		view.printAll(list);
	}
}
