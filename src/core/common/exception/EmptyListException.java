package core.common.exception;

public class EmptyListException extends Exception {
    public EmptyListException() {
        super("리스트가 비었습니다.");
    }
}
