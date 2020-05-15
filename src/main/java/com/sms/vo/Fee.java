package com.sms.vo;

public class Fee {
	
	private Integer feeId;
	private Integer studentId;
	private Integer amount;
	private String monthOfFee;
	private Integer port;

	public Integer getFeeId() {
		return feeId;
	}

	public void setFeeId(Integer feeId) {
		this.feeId = feeId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getMonthOfFee() {
		return monthOfFee;
	}

	public void setMonthOfFee(String monthOfFee) {
		this.monthOfFee = monthOfFee;
	}
	
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}
