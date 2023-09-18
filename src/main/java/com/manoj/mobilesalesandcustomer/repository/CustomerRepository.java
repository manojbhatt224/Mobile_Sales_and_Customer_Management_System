package com.manoj.mobilesalesandcustomer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
public Customer findByPhone(String cname);
}
