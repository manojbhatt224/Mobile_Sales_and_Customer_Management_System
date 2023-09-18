package com.manoj.mobilesalesandcustomer.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.manoj.mobilesalesandcustomer.model.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Integer> {
		public Page<Purchase> findAll(Pageable page);
//	

		public List<Purchase> findAllByDateGreaterThanEqualAndDateLessThanEqual(Date startDate, Date endDate);
}
