package com.loan.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "loan")
public class Loan implements Serializable {

	@Id
	@SequenceGenerator(name = "loan_id", sequenceName = "loan_id", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loan_id")
	private int loanId;
	private double loanAmt;
	private String loanType;
	private int duration;
	private double monthlyEMI;

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	private String loanStatus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private Customer customer;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="loan_id")
	private List<Transaction> transactions;

	public Loan() {

	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public double getLoanAmt() {
		return loanAmt;
	}

	public void setLoanAmt(double loanAmt) {
		this.loanAmt = loanAmt;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public double getMonthlyEMI() {
		return monthlyEMI;
	}

	public void setMonthlyEMI(double monthlyEMI) {
		this.monthlyEMI = monthlyEMI;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {

//		for (Transaction transaction : transactions) {
//			transaction.setLoan(this);
//		}
		this.transactions = transactions;
	}



	public void addTransaction(Transaction transation) {
		transation.setLoan(this);
		this.getTransactions().add(transation);
	}
}
