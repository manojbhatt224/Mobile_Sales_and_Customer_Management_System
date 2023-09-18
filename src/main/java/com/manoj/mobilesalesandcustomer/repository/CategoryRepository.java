package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Category;
public interface CategoryRepository  extends JpaRepository <Category,Integer>{
	
	public List<Category> findAll();
	public Page<Category> findAll(Pageable page);
	public Page<Category> findByCnameContaining(String keyword, Pageable page);
	public Category findByProductsPid(Integer pid);
 }
