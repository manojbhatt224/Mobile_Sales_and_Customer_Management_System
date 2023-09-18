package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import com.manoj.mobilesalesandcustomer.model.Purchaselog;

public interface PurchaselogService {
	void insertPurchaselog(Purchaselog purchase);
	void deletePurchaselog(Integer purchaseid);
	List<Purchaselog> listAllPurchaseLog(Integer purchaseid);

}
