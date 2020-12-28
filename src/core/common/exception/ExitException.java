package core.common.exception;

public class ExitException extends Exception {
    public ExitException() {
        super("취소되었습니다.");
    }
}
