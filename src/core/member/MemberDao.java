package core.member;

import static core.common.CommonJdbc.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import core.common.exception.ChargeMoneyException;
import core.common.exception.ExitException;
import core.common.exception.PasswordLengthException;
import core.member.option.InfoItem;

public class MemberDao {
	//멤버다오의 회원정보를 멤버db에 저장

	//Insert
	public void insert(Member member) throws SQLException {
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

			executeUpdate();
		} catch (SQLException ignored) {
			throw new SQLException("Insert Fail");
		} finally {
			close();
		}
	}

	//Select - 전체 출력
	public List<Member> findAll() {
		List<Member> list = new ArrayList<>();

		try {
			connect();

			String sql = "SELECT * FROM MEMBER "
							+ "  ORDER BY MEMBER_ID";
			
			getPreparedStatement(sql);
			
			ResultSet rs = executeQuery();
			
			while(rs.next()) {
				Member member = parseMember(rs);
				list.add(member);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}

		return list;
	}

	private Member parseMember(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setMemberId(rs.getString("MEMBER_ID"));
		member.setPw(rs.getString("PW"));
		member.setName(rs.getString("NAME"));
		member.setPhone(rs.getString("PHONE"));
		member.setEmail(rs.getString("EMAIL"));
		member.setSpot_id(rs.getInt("SPOT_ID"));
		member.setGrade(rs.getString("GRADE"));
		member.setCash(rs.getInt("CASH"));

		return member;
	}
	
	//Select - ID, PW
	public Member select(LoginInfo loginInfo) throws IllegalStateException {
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
				member = parseMember(rs);
			} else if(!rs.next()) {
				throw new IllegalStateException("아이디가 존재하지 않거나 비밀번호가 틀렸습니다. 다시 입력 해 주세요.");
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
	public void update(Member member, InfoItem infoItem, String infoItemValue) throws PasswordLengthException, ExitException, IllegalStateException {
		try {
			connect();

			String sql = "UPDATE MEMBER " +
					"  SET " + infoItem.name() + " = ? " +
					"  WHERE MEMBER_ID = ? ";

			PreparedStatement pstmt = getPreparedStatement(sql);

			switch (infoItem) {
				case PASSWORD: {
					if(member.getPw().length() < 4) {
						throw new PasswordLengthException();
					} else {
						member.setPw(infoItemValue);
						pstmt.setString(1, member.getPw());
					}
					break;
				}
				case NAME: {
					member.setName(infoItemValue);
					pstmt.setString(1, member.getName());
					break;
				}
				case PHONE: {
					member.setPhone(infoItemValue);
					pstmt.setString(1, member.getPhone());
					break;
				}
				case EMAIL: {
					member.setEmail(infoItemValue);
					pstmt.setString(1, member.getEmail());
					break;
				}
				case EXIT: {
					throw new ExitException();
				}
			}

			pstmt.setString(2, member.getMemberId());

			int result = executeUpdate();
			if (result <= 0) {
				throw new IllegalStateException("업데이트 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}

	//Update - 충전금액 변경
	public void updatingCash(Member session, int newCash) throws ChargeMoneyException {
		try {
			connect();

			String sql = "UPDATE MEMBER " +
					"  SET CASH = ? " +
					"  WHERE MEMBER_ID = ? ";
			PreparedStatement pstmt = getPreparedStatement(sql);

			pstmt.setInt(1, newCash);
			pstmt.setString(2, session.getMemberId());

			execute();
			session.setCash(newCash);
		} catch (SQLException e) {
			throw new ChargeMoneyException();
		} finally {
			close();
		}
	}

	//Delete
	public void delete(Member member) {
		// 1. 입고된 물건이 있는지 확인한다.
		// 2. 물건이 없으면 삭제한다.
		try {
			connect();

			String sql = "DELETE FROM MEMBER WHERE MEMBER_ID = ?";

			PreparedStatement pstmt = getPreparedStatement(sql);

			pstmt.setString(1, member.getMemberId());

			executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close();
		}
	}
}
