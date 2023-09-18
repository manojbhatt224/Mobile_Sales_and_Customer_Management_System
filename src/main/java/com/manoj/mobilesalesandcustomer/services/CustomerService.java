package com.manoj.mobilesalesandcustomer.services;

import com.manoj.mobilesalesandcustomer.model.Customer;

public interface CustomerService {
	void insertCustomer(Customer cust);
	void deleteCustomer(Integer custid);
	Customer searchCustomerById(Integer custid);
	Customer searchCustomerByPhone(String mob);

}
