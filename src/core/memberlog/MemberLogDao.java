package core.memberlog;

import static core.common.CommonJdbc.close;
import static core.common.CommonJdbc.connect;
import static core.common.CommonJdbc.executeQuery;
import static core.common.CommonJdbc.executeUpdate;
import static core.common.CommonJdbc.getPreparedStatement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static core.common.CommonView.*;
import core.member.Member;

public class MemberLogDao {
	
	
	//Select - 전체, 가입, 수정, 탈퇴
	public List<MemberLog> findUser(String select) {
		List<MemberLog> list = new ArrayList<>();

		try {
			connect();
			String sql;
			
			if(select.equals("ALL")) {
				sql = "SELECT LOG_ID, MEMBER_ID, PW, NAME, PHONE, EMAIL, LOG_DATE, LOG_MODE FROM MEMBER_LOG "
						+ "  ORDER BY MEMBER_ID";
			} else {
				sql = "SELECT LOG_ID, MEMBER_ID, PW, NAME, PHONE, EMAIL, LOG_DATE, LOG_MODE FROM MEMBER_LOG "
								+ "  WHERE LOG_MODE = ?"
								+ "  ORDER BY MEMBER_ID";
			}
			
			PreparedStatement pstmt = getPreparedStatement(sql);
			
			switch(select) {
				case "ALL" :
					break;
				case "JOIN" :
					pstmt.setString(1, "회원가입");
					break;
				case "UPDATE" :
					pstmt.setString(1, "회원수정");
					break;
				case "LEAVE" :
					pstmt.setString(1, "회원탈퇴");
					break;
			}
			
			ResultSet rs = executeQuery();
			
			while(rs.next()) {
				MemberLog memberlog = new MemberLog();
				Member member = new Member();
				memberlog.setLogId(rs.getInt("LOG_ID"));
				
				member.setMemberId(rs.getString("MEMBER_ID"));
				member.setPw(rs.getString("PW"));
				member.setName(rs.getString("NAME"));
				member.setPhone(rs.getString("PHONE"));
				member.setEmail(rs.getString("EMAIL"));
				memberlog.setMember(member);
				
				memberlog.setLogDate(rs.getDate("LOG_DATE"));
				memberlog.setLogMode(rs.getString("LOG_MODE"));
				
				list.add(memberlog);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return list;
	}


	//Delete
	public void delete(String id) throws IllegalStateException {
		try {
			connect();

			String sql = "DELETE FROM MEMBER_LOG WHERE MEMBER_ID = ?";

			PreparedStatement pstmt = getPreparedStatement(sql);

			pstmt.setString(1, id);



			if (executeUpdate() == 0) {
				throw new IllegalStateException("대상 ID가 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
}
