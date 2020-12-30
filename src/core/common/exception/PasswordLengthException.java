package core.common.exception;

public class PasswordLengthException extends Exception {
    public PasswordLengthException() {
        super("패스워드 길이가 4글자 이상이어야 합니다.");
    }
}
