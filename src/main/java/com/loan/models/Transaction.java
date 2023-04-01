package com.loan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {

	@Id
	@SequenceGenerator(name = "trans_id", sequenceName = "trans_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trans_id")
	private int transId;
	private Timestamp transTime;
	private String mssg;

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	// this amount is +ve for credit and -ve for debit
	private Double amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Loan loan;

	public Transaction(Timestamp transTime, String mssg, Double amount) {
		this.transTime = transTime;
		this.mssg = mssg;
		this.amount = amount;
	}

	public Transaction() {

	}

	public int getTransId() {
		return transId;
	}

	public void setTransId(int transId) {
		this.transId = transId;
	}

	public Timestamp getTransTime() {
		return transTime;
	}

	public void setTransTime(Timestamp transTime) {
		this.transTime = transTime;
	}

	public String getMssg() {
		return mssg;
	}

	public void setMssg(String mssg) {
		this.mssg = mssg;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}
}
