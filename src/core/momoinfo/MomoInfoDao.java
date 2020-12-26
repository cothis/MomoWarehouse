package core.momoinfo;

import core.common.CommonJdbc;
import core.member.Member;

import java.sql.SQLException;
import java.util.List;

import static core.common.CommonJdbc.*;

public class MomoInfoDao {

    private Member selectedUser;

    public void setSelectedUser(Member selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<MomoInfo> find(HistoryOption in) {

        try {
            connect();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return null;
    }

    public List<Member> findUser() {
        //예외적으로 여기서만 Member 테이블의 유저 리스트를 가져온다.
        //메인 컨트롤러가 없어서 memberController 에 접근이 불가능하기 때문이다.
        //user grade "USER" 인 항목들만 조회한다.
        return null;
    }
}
