package core.member;

public class LoginInfo {
    private String memberId;
    private String pw;

    public LoginInfo(String memberId, String pw) {
        this.memberId = memberId;
        this.pw = pw;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getPw() {
        return pw;
    }

	public void setPw(String pw) {
		this.pw = pw;
	}
    
    
}
