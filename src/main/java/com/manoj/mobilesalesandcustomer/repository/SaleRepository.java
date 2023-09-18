package com.manoj.mobilesalesandcustomer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Integer> {
	public Page<Sale> findAll(Pageable page);
//	

		public List<Sale> findAllByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate);
}
