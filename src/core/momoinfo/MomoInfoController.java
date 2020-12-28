package core.momoinfo;

import core.member.Member;

public interface MomoInfoController {
	void inOutMenu(Member member) throws Exception; // 유저: 입고, 출고
	void inOutHistory(Member member); // 유저: 유저내역, 관리자: 전체내역
}
