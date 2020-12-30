package core.momoinfo;

import core.common.CommonView;

import java.sql.Date;
import java.text.DecimalFormat;

public class MomoInfo {
	private final int momoId;
	private final int spotId;
	private final int itemId;
	private final String memberId;
	private final Date inTime;
	private final Date outTime;
	private final int priceByHour;
	private final int payment;
	private final String status;
	
	public MomoInfo(int momoId, int spotId, int itemId, String memberId, Date inTime, Date outTime, int priceByHour,
			int payment, String status) {
		super();
		this.momoId = momoId;
		this.spotId = spotId;
		this.itemId = itemId;
		this.memberId = memberId;
		this.inTime = inTime;
		this.outTime = outTime;
		this.priceByHour = priceByHour;
		this.payment = payment;
		this.status = status;
	}

	public int getMomoId() {
		return momoId;
	}

	public Date getInTime() {
		return inTime;
	}

	public int getPriceByHour() {
		return priceByHour;
	}

	@Override
	public String toString() {
		String outTimeString;
		if (outTime == null) {
			outTimeString = "   -   ";
		} else {
			outTimeString = outTime.toString();
		}

		DecimalFormat formatter = new DecimalFormat("###,###");

		return String.format("     %-8s    |    %-8s    %-8s    %-10s    %-15s    %-15s    %-15s    %-15s    %-5s\t",
				momoId + "", spotId + "", itemId + "", CommonView.checkLength(memberId),
				inTime.toString(), outTimeString, formatter.format(priceByHour), formatter.format(payment), status);
	}

	public static String getHeader() {
		return String.format("     %-8s    |    %-8s    %-8s    %-10s    %-15s    %-15s    %-15s    %-15s    %-5s\t",
				"Momo ID", "Spot ID", "Item ID", "User ID", "In Time", "Out Time", "Price by hour", "Payment", "Status");
	}


}
