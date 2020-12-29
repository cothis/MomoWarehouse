package core.common.exception;

public class LessMoneyException extends Exception{
    public LessMoneyException(int payPrice, int userCash) {
        super(String.format("금액이 모자랍니다. 필요 금액 : %d, 보유 금액 : %d", payPrice, userCash));
    }
}
