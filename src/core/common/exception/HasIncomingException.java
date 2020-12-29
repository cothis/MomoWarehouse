package core.common.exception;

public class HasIncomingException extends Exception{
    public HasIncomingException() {
        super("입고된 아이템이 있습니다.");
    }
}
