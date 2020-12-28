package core.common.exception;

public class ChargeMoneyException extends Exception {
    public ChargeMoneyException() {
        super("금액을 너무 많이 입력했습니다.");
    }
}
