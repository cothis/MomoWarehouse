package core.momoinfo.statistcs;

import java.text.DecimalFormat;

public class TotalPayment {
    private String memberId;
    private int[] monthlyPayment = new int[12];
    private int totalPayment = 0;

    public TotalPayment() {
        this.memberId = "Total";
    }

    public TotalPayment(String memberId, int[] monthlyPayment, int totalPayment) {
        this.memberId = memberId;
        this.monthlyPayment = monthlyPayment;
        this.totalPayment = totalPayment;
    }

    public TotalPayment(String memberId, int totalPayment) {
        this.memberId = memberId;
        this.totalPayment = totalPayment;
    }

    public static String getHeader() {
        return String.format("    %-10s    |    %-20s", "Member ID", "Total Payment");
    }

    @Override
    public String toString() {
        DecimalFormat formatter = new DecimalFormat("###,###");
        return String.format("    %-10s    |    %-20s", memberId, formatter.format(totalPayment));
    }

    public void sum(TotalPayment userPayment) {
        for (int i = 0; i < monthlyPayment.length; i++) {
            this.monthlyPayment[i] += userPayment.monthlyPayment[i];
        }
        this.totalPayment += userPayment.totalPayment;
    }

    public static String getMonthlyHeader() {
        StringBuilder monthly = new StringBuilder();
        for (int i = 0 ; i < 12 ; i ++) {
            monthly.append(String.format("%-10s    ", (i + 1) + "Mon"));
        }

        return String.format("    %-10s    |    %s    |    %-20s", "Member ID", monthly, "Total Payment");
    }

    public String getMonthlyDataString() {
        DecimalFormat formatter = new DecimalFormat("###,###");

        StringBuilder monthly = new StringBuilder();
        for (int i = 0 ; i < 12 ; i ++) {
            monthly.append(String.format("%-10s    ", formatter.format(monthlyPayment[i])));
        }

        return String.format("    %-10s    |    %s    |    %-20s", memberId, monthly, formatter.format(totalPayment));
    }

}
