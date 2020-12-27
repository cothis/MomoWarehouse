package core.member;

import static core.common.CommonJdbc.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDao {
	//멤버다오의 회원정보를 멤버db에 저장

	//Insert
	public int insert(Member member) {
		int result = 0;

		try {
			connect();

			String sql = "INSERT INTO MEMBER(MEMBER_ID, PW, NAME, PHONE, EMAIL, SPOT_ID) " +
					" VALUES (?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = getPreparedStatement(sql);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPw());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getEmail());
			pstmt.setInt(6, member.getSpot_id());

			result = executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}

		return result;
	}


	//Select - ID, PW
	public Member select(LoginInfo loginInfo) {
		Member member = null;

		try {
			connect();

			String sql = "SELECT MEMBER_ID, PW, NAME, PHONE, EMAIL, SPOT_ID, GRADE, CASH "
						+"  FROM MEMBER "
						+"  WHERE MEMBER_ID = ? AND PW = ?";
			PreparedStatement pstmt = getPreparedStatement(sql);
			pstmt.setString(1, loginInfo.getMemberId());
			pstmt.setString(2, loginInfo.getPw());
			ResultSet rs = executeQuery();

			if(rs.next()) {
				member = new Member();
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setPw(rs.getString("PW"));
				member.setName(rs.getString("NAME"));
				member.setPhone(rs.getString("PHONE"));
				member.setEmail(rs.getString("EMAIL"));
				member.setSpot_id(rs.getInt("SPOT_ID"));
				member.setGrade(rs.getString("GRADE"));
				member.setCash(rs.getInt("CASH"));
			}else if(!rs.next()) {
				System.out.println("아이디가 존재하지 않거나 비밀번호가 틀렸습니다. 다시 입력 해 주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return member;
	}

	//Select - 중복 ID 체크
	public boolean hasId(String id) {
		String resultId = null;

		try {
			connect();

			String sql = "SELECT MEMBER_ID  "
						+" FROM MEMBER "
						+" WHERE MEMBER_ID = ? ";
			getPreparedStatement(sql).setString(1, id);
			ResultSet rs = executeQuery();

			if(rs.next()) {
				resultId = rs.getString("MEMBER_ID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return id.equals(resultId);
	}

	//Update - 회원정보수정(CRUD)
	public int update(Member member, String userInfoUpTxt) {
		int result = 0;

		try {
			connect();

			String sql = "UPDATE MEMBER " +
					"  SET " + userInfoUpTxt + " = ? " +
					"  WHERE MEMBER_ID = ? ";

			PreparedStatement pstmt = getPreparedStatement(sql);

			if(userInfoUpTxt.trim().equals("PW")) {
				pstmt.setString(1, member.getPw());
			}else if(userInfoUpTxt.trim().equals("NAME")) {
				pstmt.setString(1, member.getName());
			}else if(userInfoUpTxt.trim().equals("PHONE")) {
				pstmt.setString(1, member.getPhone());
			}else if(userInfoUpTxt.trim().equals("EMAIL")) {
				pstmt.setString(1, member.getEmail());
			}

			pstmt.setString(2, member.getMemberId());

			result = executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}

		return result;
	}

	//Update - 충전금액 변경
	public int updatingCash(Member member) {
		int result = 0;

		try {
			connect();

			String sql = "UPDATE MEMBER " +
					"  SET CASH = ? " +
					"  WHERE MEMBER_ID = ? ";
			PreparedStatement pstmt = getPreparedStatement(sql);

			pstmt.setInt(1, member.getCash());
			pstmt.setString(2, member.getMemberId());

			result = executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}

		return result;
	}

	//Delete
	public int delete(String id) {
		int result = 0;

		try {
			connect();

			String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";

			PreparedStatement pstmt = getPreparedStatement(sql);

			pstmt.setString(1, id);

			result = executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			close();
		}

		return result;
	}


	//클로즈 메서드
	/*
	public void close(Connection conn, PreparedStatement pstmt) {
		try {
			//null이 아닐때 닫아주는게 더 좋은 방법 (조건문 달아서..)
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	 */

	/*
	public void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			//null이 아닐때 닫아주는게 더 좋은 방법 (조건문 달아서..)
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			if(conn != null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	*/
}
