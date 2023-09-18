package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Salelog;

public interface SalelogRepository extends JpaRepository<Salelog, Integer>{

	List<Salelog> findBySaleSalid(Integer saleid);

}
