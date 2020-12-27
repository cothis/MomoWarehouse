package core.common;

import java.sql.*;

public class CommonJdbc {
    private static Connection conn = null;
    private static PreparedStatement pstmt = null;
    private static ResultSet rs = null;

    private static final String driver = "oracle.jdbc.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String userId = "whadmin";
    private static final String userPw = "whadmin";

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        pstmt = conn.prepareStatement(sql);
        return pstmt;
    }

    public static ResultSet executeQuery() throws SQLException {
        rs = pstmt.executeQuery();
        return rs;
    }

    public static int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    public static Connection getConn() {
        return conn;
    }

    public static void execute() throws SQLException {
        pstmt.execute();
    }

    public static void connect() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        conn = DriverManager.getConnection(url, userId, userPw);
    }

    public static void close() {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
