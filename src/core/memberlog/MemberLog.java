package core.memberlog;

import java.sql.Date;

import core.member.Member;

public class MemberLog {
	private int logId;
	private Member member;
	private Date logDate;
	private String logMode;
	
	public MemberLog() {
	}
	
	public MemberLog(int logId, Member member, Date logDate, String logMode) {
		this.logId = logId;
		this.member = member;
		this.logDate = logDate;
		this.logMode = logMode;
	}

	public int getLogId() {
		return logId;
	}

	public Member getMember() {
		return member;
	}

	public Date getLogDate() {
		return logDate;
	}

	public String getLogMode() {
		return logMode;
	}
	
	
	public void setLogId(int logId) {
		this.logId = logId;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setLogDate(Date date) {
		this.logDate = date;
	}

	public void setLogMode(String logMode) {
		this.logMode = logMode;
	}

	@Override
	public String toString() {
		return String.format("\t%-8s | %-10s %-10s %-15s %-20s %-20s %-6s\t", logId, member.getMemberId(),
				member.getName(), member.getPhone(), member.getEmail(),
				logDate.toString(), logMode);
	}

	public static String getHeader() {
		return String.format("\t%-8s | %-10s %-10s %-15s %-20s %-20s %-6s\t", "Log ID", "User ID", "Name",
				"Phone", "Email", "Log Date", "Mode");
	}
	
	
}
