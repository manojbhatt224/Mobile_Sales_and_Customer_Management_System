package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Dealer;


public interface DealerService {
	void insertDealer(Dealer deal);
	void deleteDealer(Integer did);
	Dealer searchDealerById(Integer id);
	List<Dealer> searchListDealer();
	List<Dealer> searchListByManufacturerId(Integer mid);
	Page<Dealer> searchPageDealer(Pageable page);
	Page<Dealer> searchbykeywordDealer(String keyword, Pageable page);
	Dealer searchDealerByProductId(Integer pid);
}
