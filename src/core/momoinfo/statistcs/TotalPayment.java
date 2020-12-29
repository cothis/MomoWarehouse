package core.momoinfo.statistcs;

import java.text.DecimalFormat;

public class TotalPayment {
    private String memberId;
    private int payment;

    public TotalPayment(String memberId, int payment) {
        this.memberId = memberId;
        this.payment = payment;
    }

    public static String getHeader() {
        return String.format("    %-10s    |    %-20s", "Member ID", "Total Payment");
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return String.format("    %-10s    |    %-20s", memberId, formatter.format(payment));
    }

}
