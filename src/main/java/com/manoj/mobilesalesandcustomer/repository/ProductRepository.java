package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	public Product findByPname(String name);
	public List<Product> findAll();
	public List<Product> findByDealerDid(Integer did);
	public Page<Product> findAll(Pageable page);
	public Page<Product> findByPnameContainingOrPdorContaining(String pname, String pdor, Pageable page);
	List<Product> findByDealer_Manufacturer(Manufacturer manu);
}
