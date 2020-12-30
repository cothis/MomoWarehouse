package core.momoinfo;

import core.common.CommonJdbc;
import core.common.exception.EmptyListException;
import core.common.exception.HasIncomingException;
import core.common.exception.NoPaymentHistoryException;
import core.item.Item;
import core.member.Member;
import core.momoinfo.option.DetailsOption;
import core.momoinfo.statistcs.TotalPayment;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.common.CommonJdbc.*;
import static core.momoinfo.option.DetailsOption.*;

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

    public List<MomoInfo> find(DetailsOption option) {
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
            if(option == IN_DETAILS) {
                sql = sql + " and STATUS = '입고'";
            } else if (option == OUT_DETAILS) {
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
                         condition +
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

    public List<TotalPayment> findMonthlyPaymentByUser(String year) throws EmptyListException {

        List<TotalPayment> list = new ArrayList<>();

        String condition = "";

        boolean isUser = selectedUser.getGrade().equalsIgnoreCase("USER");
        if (isUser) {
            condition = " where MEMBER_ID = ?";
        }

        String sql = "SELECT\n" +
                "    MEMBER_ID, SUM(\"01\") AS \"1M\", SUM(\"02\") AS \"2M\", SUM(\"03\") AS \"3M\", SUM(\"04\") AS \"4M\", SUM(\"05\") AS \"5M\", SUM(\"06\") AS \"6M\",\n" +
                "    SUM(\"07\") AS \"7M\", SUM(\"08\") AS \"8M\", SUM(\"09\") AS \"9M\", SUM(\"10\") AS \"10M\", SUM(\"11\") AS \"11M\", SUM(\"12\") AS \"12M\",\n" +
                "    SUM(PAYMENT) AS \"USER_TOTAL_PAYMENT\"\n" +
                "FROM(\n" +
                "        SELECT MEMBER_ID, TO_CHAR(IN_TIME,'YYYY') AS YYYY, TO_CHAR(IN_TIME,'MM') AS MM, TO_CHAR(IN_TIME,'DD') AS DD, PAYMENT\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '01', PAYMENT, 0) AS \"01\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '02', PAYMENT, 0) AS \"02\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '03', PAYMENT, 0) AS \"03\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '04', PAYMENT, 0) AS \"04\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '05', PAYMENT, 0) AS \"05\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '06', PAYMENT, 0) AS \"06\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '07', PAYMENT, 0) AS \"07\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '08', PAYMENT, 0) AS \"08\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '09', PAYMENT, 0) AS \"09\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '10', PAYMENT, 0) AS \"10\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '11', PAYMENT, 0) AS \"11\"\n" +
                "        , DECODE(TO_CHAR(IN_TIME,'MM'), '12', PAYMENT, 0) AS \"12\"\n" +
                "        FROM MOMOINFO\n" +
                "        WHERE TO_CHAR(IN_TIME,'YYYY') = ?\n" +
                ")" +
                condition +
                " GROUP BY MEMBER_ID, YYYY\n" +
                "ORDER BY USER_TOTAL_PAYMENT DESC";
        try {
            connect();

            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setString(1, year);
            if (isUser) {
                pstmt.setString(2, selectedUser.getMemberId());
            }

            ResultSet rs = executeQuery();

            TotalPayment sumPayment = new TotalPayment();

            while (rs.next()) {
                TotalPayment userPayment = parseTotalPayment(rs);
                list.add(userPayment);
                sumPayment.sum(userPayment);
            }
            list.add(sumPayment);

            if (list.isEmpty()) {
                throw new EmptyListException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

        return list;
    }

    private TotalPayment parseTotalPayment(ResultSet rs) throws SQLException {
        int[] monthly = new int[12];

        String member_id = rs.getString("MEMBER_ID");
        for(int i = 0; i < monthly.length; i++) {
            monthly[i] = rs.getInt(String.format("%dM", i+1));
        }
        int userTotalPayment = rs.getInt("USER_TOTAL_PAYMENT");

        return new TotalPayment(member_id, monthly, userTotalPayment);
    }

    public List<String> findPaymentYearsByUser() throws NoPaymentHistoryException {
        List<String> list = new ArrayList<>();

        String condition = "";
        boolean isUser = selectedUser.getGrade().equalsIgnoreCase("USER");
        if (isUser) {
            condition = " and MEMBER_ID = ?";
        }

        try {
            connect();

            String sql = "select TO_CHAR(OUT_TIME, 'YYYY') as YEAR from MOMOINFO where status = '출고'\n" +
                    condition +
                    " group by TO_CHAR(OUT_TIME, 'YYYY')";

            PreparedStatement pstmt = getPreparedStatement(sql);
            if (isUser) {
                pstmt.setString(1, selectedUser.getMemberId());
            }

            ResultSet rs = executeQuery();

            while(rs.next()) {
                String year = rs.getString("YEAR");
                list.add(year);
            }
            if (list.isEmpty()) {
                throw new NoPaymentHistoryException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }


        return list;
    }
}
