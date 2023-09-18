package com.manoj.mobilesalesandcustomer.services;


import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Purchase;

public interface PurchaseService {
	void insertPurchase(Purchase purchase);
	void deletePurchase(Integer purchaseid);
	Purchase findPurchaseById(Integer purid);
	List<Purchase> listAllPurchases();
	List<Purchase> listPurchasewithdate (Date d1, Date d2);
	Page<Purchase> pagePurchases(Pageable page);

}
