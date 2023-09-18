package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Dealer;

public interface DealerRepository extends JpaRepository<Dealer, Integer> {
	public List<Dealer> findAll();
	public Page<Dealer> findAll(Pageable page);
	public Page<Dealer> findByDnameContainingOrDaddressContainingOrDemailContainingOrDcontactContaining(String dname, String daddress, String demail, String dcontact, Pageable page);
	List<Dealer> findByManufacturerMid(Integer mid);
	public Dealer findByProductsPid(Integer pid);
}
