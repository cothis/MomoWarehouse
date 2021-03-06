package core.momoinfo;

import core.common.exception.EmptyListException;
import core.common.exception.ExitException;
import core.common.exception.HasIncomingException;
import core.member.Member;

public interface MomoInfoController {
	void inOutMenu(Member session) throws Exception; // 유저: 입고, 출고
	void inOutDetails(Member session) throws ExitException; // 유저: 유저내역, 관리자: 전체내역
	void statistics(Member session) throws EmptyListException, ExitException;

    void checkHasIncomingByUser(Member session) throws HasIncomingException;
}
