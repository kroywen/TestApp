package com.fourpay.testapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Transaction {

	@SerializedName("TransactionID")
	private int id;

	private double amount;

	@SerializedName("Debit")
	private String debit;

	@SerializedName("Credit")
	private String credit;

	@SerializedName("Date")
	private String date;

	public Transaction() {}

	public Transaction(int id, double amount, String debit, String credit, String date) {
		this.id = id;
		this.amount = amount;
		this.debit = debit;
		this.credit = credit;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDebit() {
		return debit;
	}

	public void setDebit(String debit) {
		this.debit = debit;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}