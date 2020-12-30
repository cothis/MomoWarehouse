package core.common.exception;

public class PasswordFailException extends Exception{
    public PasswordFailException() {
        super("비밀번호가 일치하지 않습니다.");
    }
}
