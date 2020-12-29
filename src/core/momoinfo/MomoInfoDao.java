package core.momoinfo;

import core.common.exception.EmptyListException;
import core.common.exception.HasIncomingException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.HistoryOption;
import core.momoinfo.statistcs.TotalPayment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.common.CommonJdbc.*;
import static core.momoinfo.option.HistoryOption.*;

public class MomoInfoDao {

    private Member selectedUser;

    public void setSelectedUser(Member selectedUser) {
        this.selectedUser = selectedUser;
    }

    private MomoInfo parseMomoInfo(ResultSet rs) throws SQLException {

        int momoId = rs.getInt("MOMO_ID");
        int spotId = rs.getInt("SPOT_ID");
        int itemId = rs.getInt("ITEM_ID");
        String memberId = rs.getString("MEMBER_ID");
        Date inTime = rs.getDate("IN_TIME");
        Date outTime = rs.getDate("OUT_TIME");
        int priceByHour = rs.getInt("PRICE_BY_HOUR");
        int payment = rs.getInt("PAYMENT");
        String status = rs.getString("STATUS");

        return new MomoInfo(momoId, spotId, itemId, memberId, inTime, outTime, priceByHour, payment, status);
    }

    public List<MomoInfo> find(HistoryOption option) {
        List<MomoInfo> list = new ArrayList<>();

        try {
            connect();
            String sql = "select MOMO_ID, SPOT_ID, ITEM_ID, MEMBER_ID, IN_TIME, OUT_TIME, PRICE_BY_HOUR, PAYMENT, STATUS" +
                    " from MOMOINFO";
            if (selectedUser.getGrade().equals("ADMIN")) {
                sql = sql + " where 1 = 1";
            } else {
                sql = sql + " where MEMBER_ID = '" + selectedUser.getMemberId() + "'";
            }
            if(option == IN_HISTORY) {
                sql = sql + " and STATUS = '입고'";
            } else if (option == OUT_HISTORY) {
                sql = sql + " and STATUS = '출고'";
            }
            sql = sql + " order by IN_TIME";

            getPreparedStatement(sql);

            ResultSet rs = executeQuery();

            while (rs.next()) {
                list.add(parseMomoInfo(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }

    public List<Member> findUser() {
        //예외적으로 여기서만 Member 테이블의 유저 리스트를 가져온다.
        //메인 컨트롤러가 없어서 memberController 에 접근이 불가능하기 때문이다.
        //user grade "USER" 인 항목들만 조회한다.
        List<Member> list = new ArrayList<>();

        try {
            connect();

            String sql = "select MEMBER_ID, NAME" +
                    " from MEMBER" +
                    " where GRADE = 'USER'";

            getPreparedStatement(sql);

            ResultSet rs = executeQuery();
            while (rs.next()) {
                String memberId = rs.getString("MEMBER_ID");
                String name = rs.getString("NAME");
                list.add(new Member(memberId, name, "USER"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }

    public void create(Item item) {
        try {
            connect();

            String sql = "insert into MOMOINFO(MOMO_ID, SPOT_ID, ITEM_ID, MEMBER_ID, PRICE_BY_HOUR)" +
                    " values(MOMO_ID_SEQ.nextval, ?, ?, ?, ?)";

            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setInt(1, selectedUser.getSpot_id());
            pstmt.setInt(2, item.getItemId());
            pstmt.setString(3, selectedUser.getMemberId());
            pstmt.setInt(4, item.getPriceByHour());

            execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void update(MomoInfo momoInfo, int payPrice) {
        try {
            connect();

            String sql = "update MOMOINFO" +
                    " set OUT_TIME = ?, STATUS = '출고', PAYMENT = ?" +
                    " where MOMO_ID = ?";
            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setDate(1, new Date(System.currentTimeMillis()));
            pstmt.setInt(2, payPrice);
            pstmt.setInt(3, momoInfo.getMomoId());

            execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void checkHasIncomingByUser(Member session) throws HasIncomingException {
        try {
            connect();

            String sql = "select 1 from MOMOINFO" +
                    " where MEMBER_ID = ? and STATUS = '입고'";
            getPreparedStatement(sql).setString(1, session.getMemberId());

            if (executeQuery().next()) {
                throw new HasIncomingException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public List<TotalPayment> findTotalPaymentByUser() throws EmptyListException {
        List<TotalPayment> list = new ArrayList<>();

        String condition = "";

        boolean isUser = selectedUser.getGrade().equalsIgnoreCase("USER");
        if (isUser) {
            condition = " where MEMBER_ID = ?";
        }

        try {
            connect();

            String sql = "SELECT MEMBER_ID, SUM(PAYMENT) as TOTAL_PAYMENT FROM MOMOINFO " +
                         " GROUP BY MEMBER_ID " +
                         " ORDER BY TOTAL_PAYMENT DESC";

            PreparedStatement pstmt = getPreparedStatement(sql);
            if (isUser) {
                pstmt.setString(1, selectedUser.getMemberId());
            }

            ResultSet rs = executeQuery();

            while (rs.next()) {
                String memberId = rs.getString("MEMBER_ID");
                int totalPayment = rs.getInt("TOTAL_PAYMENT");
                list.add(new TotalPayment(memberId, totalPayment));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (list.isEmpty()) {
            throw new EmptyListException();
        }

        return list;
    }
}
