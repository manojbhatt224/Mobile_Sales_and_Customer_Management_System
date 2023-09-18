package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Integer> {
	public Page<Manufacturer> findAll(Pageable page);
	public List<Manufacturer> findAll();
	public Page<Manufacturer> findByMnameContaining(String keyword, Pageable page);
	public Manufacturer findByDealersDid(Integer did) ;
	public Manufacturer findByDealersProductsPname(String pname);
}
