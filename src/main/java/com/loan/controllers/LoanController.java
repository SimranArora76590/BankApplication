package com.loan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.loan.models.Loan;
import com.loan.services.iLoanService;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*")
public class LoanController {

	@Autowired(required = true)
	private iLoanService loanService;

	@RequestMapping(value = "/{customerId}/loan", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Loan> applyLoan(@PathVariable(value = "customerId") int customerId, @RequestBody Loan loan) {
		return new ResponseEntity<Loan>(loanService.applyLoan(customerId ,loan), HttpStatus.OK);
	}

	@GetMapping("/customer/{id}")
	public ResponseEntity<List<Loan>> getLoansByCustomerId(@PathVariable int id) {
		return new ResponseEntity<List<Loan>>(loanService.getLoansByCustomerId(id), HttpStatus.OK);
	}

//	@DeleteMapping("/foreclose/{loanId}")
//	public ResponseEntity<String> forecloseLoan(@PathVariable int loanId) {
//		// here we'll take 2 params , one will be amount and another will be id , if amount < total amount we say partial forclose else full forclose
//		loanService.foreCloseLoan(loanId);
//		return new ResponseEntity<String>("Loan Foreclosed with Loan Id: " + loanId, HttpStatus.OK);
//	}


	@PostMapping("/foreclose")
	public ResponseEntity<String> forecloseLoan(@RequestParam int loanId, @RequestParam Double amount) {
		// here we'll take 2 params , one will be amount and another will be id , if amount < total amount we say partial forclose else full forclose
		String loanStringResponse=loanService.foreCloseLoan(loanId,amount);
		return new ResponseEntity<String>(loanStringResponse + loanId, HttpStatus.OK);
	}
}
