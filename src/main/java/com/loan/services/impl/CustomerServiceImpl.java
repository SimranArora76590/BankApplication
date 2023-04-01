package com.loan.services.impl;

import java.util.*;

import com.loan.dao.LoanRepository;
import com.loan.models.Loan;
import com.loan.models.Transaction;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.loan.dao.CustomerRepository;
import com.loan.exceptions.CustomerAlreadyRegisteredException;
import com.loan.exceptions.CustomerNotFoundException;
import com.loan.models.Customer;
import com.loan.services.iCustomerService;

@Service
@Primary
public class CustomerServiceImpl implements iCustomerService {

	@Autowired
	private CustomerRepository customerDao;

	@Autowired
	private LoanRepository loanRepository;

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public Customer addCustomer(Customer c) {
		Customer customer = customerDao.checkCustomer(c.getEmail(), c.getAdhaar(), c.getPan(), c.getPhone());
		if (customer != null) {
			throw new CustomerAlreadyRegisteredException("Customer Already Registered: " + customer.getId());
		}



//		for(Loan loan : c.getLoans()){
//			if(loan.getCustomer()==null){
//				loan.setCustomer(c);
//				loanRepository.save(loan);
//			}
//
//		}




		return customerDao.save(c);
	}

	@Override
	public String doLogin(String email, String password) {
		Integer customerId = null;
		try {
			customerId = customerDao.findCustomerByEmailAndPassword(email, password);
			if(customerId==null)
				return "Customer Not Found";
			logger.info("Customer: " + customerId + " Logged In Successfully");
			Customer customer = customerDao.findCustomerById(customerId);
			return "Customer " + customer.getFname()+" "+customer.getLname() + " Logged In Successfully";
		} catch (Exception e) {
			throw new CustomerNotFoundException("Customer Not Found: " + customerId);
		}
	}

	public Customer updateCustomer(Customer c) {
		Customer customer = customerDao.findById(c.getId())
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + c.getId()));
		BeanUtils.copyProperties(c, customer);
		return customerDao.save(customer);
	}

	@Override
	public List<Customer> getCustomers(int pageNumber, int pageSize) {
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		return customerDao.findAll(pageable).toList();
	}

	@Override
	public Customer getCustomerById(int customerId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new CustomerNotFoundException("Customer Not Found: " + customerId));
		logger.info("Customer Found: " + customerId);
		return customer;
	}

}
