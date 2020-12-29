package core.momoinfo;

import core.common.CommonView;

import java.sql.Date;
import java.text.DecimalFormat;

public class MomoInfo {
	private int momoId;
	private int spotId;
	private int itemId;
	private String memberId;
	private Date inTime;
	private Date outTime;
	private int priceByHour;
	private int payment;
	private String status;
	
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

	public void setMomoId(int momoId) {
		this.momoId = momoId;
	}

	public int getSpotId() {
		return spotId;
	}

	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Date getInTime() {
		return inTime;
	}

	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}

	public Date getOutTime() {
		return outTime;
	}

	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}

	public int getPriceByHour() {
		return priceByHour;
	}

	public void setPriceByHour(int priceByHour) {
		this.priceByHour = priceByHour;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
		this.payment = payment;
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
