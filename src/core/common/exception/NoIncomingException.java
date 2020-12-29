package core.common.exception;

public class NoIncomingException extends Exception{
    public NoIncomingException() {
        super("입고된 물품이 없습니다.");
    }
}
