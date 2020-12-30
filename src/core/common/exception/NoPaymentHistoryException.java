package core.common.exception;

public class NoPaymentHistoryException extends Exception {
    public NoPaymentHistoryException() {
        super("결제 이력이 없습니다.");
    }
}
