package com.manoj.mobilesalesandcustomer.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Customer;
import com.manoj.mobilesalesandcustomer.repository.CustomerRepository;
@Service
public class CustomerServiceImpl implements CustomerService {
@Autowired
CustomerRepository custRepo;
	@Override
	public void insertCustomer(Customer cust) {
		this.custRepo.save(cust);
	}

	@Override
	public void deleteCustomer(Integer custid) {
		this.custRepo.deleteById(custid);
	}

	@Override
	public Customer searchCustomerById(Integer custid) {
		Optional<Customer> optional =custRepo.findById(custid);
		Customer cust=null;
		if(optional.isPresent()) {
			cust=optional.get();
		}
		else
		{
			throw new RuntimeException("Manufacturer not found for id:"+custid);
		}
		return cust;
	}

	@Override
	public Customer searchCustomerByPhone(String mob) {
		Customer cust=null;
		if(custRepo.findByPhone(mob)!=null) {
			cust=custRepo.findByPhone(mob);
		}
		else
		{
			throw new RuntimeException("Manufacturer not found for mobile:"+mob);
		}
		return cust;
	}

}
