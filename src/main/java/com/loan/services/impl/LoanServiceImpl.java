package com.loan.services.impl;

import java.util.List;

import com.loan.models.Transaction;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.loan.dao.CustomerRepository;
import com.loan.dao.LoanRepository;
import com.loan.exceptions.CustomerNotFoundException;
import com.loan.exceptions.LoanNotFoundException;
import com.loan.models.Customer;
import com.loan.models.Loan;
import com.loan.services.iLoanService;

import java.sql.Timestamp;

@Service
@Primary
public class LoanServiceImpl implements iLoanService {

	@Autowired
	private LoanRepository loanDao;

	@Autowired
	private CustomerRepository customerDao;

	private Logger logger = Logger.getLogger(getClass());

	public Loan applyLoan(int custId , Loan loan) {
		Customer customer = customerDao.findById(custId)
				.orElseThrow(() -> new CustomerNotFoundException("Cusotmer Not Found: " + custId));
		customer.addLoan(loan);
//		customerDao.save(customer);
		return loanDao.save(loan);
	}

	@Override
	public List<Loan> getLoansByCustomerId(int customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
		return customer.getLoans();
	}

	@Override
	public void foreCloseLoan(int loanId) {
		Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));
		loanDao.delete(loan);
	}

	@Override
	public String foreCloseLoan(int loanId, Double amount) {
		Loan loan = loanDao.findById(loanId).orElseThrow(() -> new LoanNotFoundException("Loan Not Found: " + loanId));

		if(!loan.getLoanStatus().equalsIgnoreCase("Closed") && loan.getLoanAmt()>amount){
			Transaction transaction  = new Transaction(new Timestamp(System.currentTimeMillis()),"Partial Payment",amount);
			loan.addTransaction(transaction);
			loan.setLoanAmt(loan.getLoanAmt()-amount);
			loan.setLoanStatus("Partially Closed");
		}else{
			Transaction transaction  = new Transaction(new Timestamp(System.currentTimeMillis()),"Full Payment",amount);
			loan.addTransaction(transaction);
			loan.setLoanAmt(loan.getLoanAmt()-amount);
			loan.setLoanStatus("Closed");
		}
        loanDao.save(loan);
		return "Loan with loanId"+loanId+"updated with status"+loan.getLoanStatus();
	}

}
