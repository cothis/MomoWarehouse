package core.spot;

import core.common.CommonView;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.common.CommonJdbc.*;
import static core.common.CommonJdbc.close;

public class SpotDao {

    public void addSpot(Spot spot) {
        String sql = "insert into SPOT(SPOT_ID, SPOT_NAME, SPOT_LOCATION)" +
                " values(SPOT_ID_SEQ.nextval, ?, ?)";
        try {
            connect();
            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setString(1, spot.getName());
            pstmt.setString(2, spot.getLocation());
            execute();
        } catch (SQLException e) {
            CommonView.noticeError(new Exception("생성에 실패했습니다."));
        } finally {
            close();
        }
    }

    private List<Spot> parseSpotList(ResultSet rs) throws SQLException {
        List<Spot> list = new ArrayList<>();

        while (rs.next()) {
            int id = rs.getInt("SPOT_ID");
            String name = rs.getString("SPOT_NAME");
            String location = rs.getString("SPOT_LOCATION");
            Spot spot = new Spot(id, name, location);
            list.add(spot);
        }
        return list;
    }

    public Spot selectById(int id) {
        Spot result = null;
        String sql = "select SPOT_ID, SPOT_NAME, SPOT_LOCATION" +
                " from SPOT" +
                " where SPOT_ID = ?";

        try {
            connect();
            getPreparedStatement(sql).setInt(1, id);

            ResultSet rs = executeQuery();

            List<Spot> spots = parseSpotList(rs);
            if (spots.size() > 0) {
                result = spots.get(0);
            }

        } catch (SQLException e) {
            CommonView.noticeError(new Exception("검색에 실패했습니다."));
        } finally {
            close();
        }
        return result;
    }

    public List<Spot> selectAll() {
        List<Spot> result = null;
        String sql = "select SPOT_ID, SPOT_NAME, SPOT_LOCATION" +
                " from SPOT";

        try {
            connect();

            getPreparedStatement(sql);

            ResultSet rs = executeQuery();

            result = parseSpotList(rs);

        } catch (SQLException e) {
            CommonView.noticeError(new Exception("검색에 실패했습니다."));
        } finally {
            close();
        }

        return result;
    }

    public void delete(Spot spot) {
        if(spot == null) return;

        try {
            connect();
            String sql = "delete from SPOT" +
                    " where SPOT_ID = ?";
            getPreparedStatement(sql).setInt(1, spot.getId());

            execute();
        } catch (SQLException e) {
            CommonView.noticeError(new Exception("삭제에 실패했습니다."));
        } finally {
            close();
        }
    }

    public void update(Spot spot) {
        try {
            connect();
            String sql = "update SPOT" +
                    " set SPOT_NAME = ?, SPOT_LOCATION = ?" +
                    " where SPOT_ID = ?";

            PreparedStatement pstmt = getPreparedStatement(sql);
            pstmt.setString(1, spot.getName());
            pstmt.setString(2, spot.getLocation());
            pstmt.setInt(3, spot.getId());

            execute();
        } catch (SQLException e) {
            CommonView.noticeError(new Exception("변경에 실패했습니다."));
        } finally {
            close();
        }
    }
}
