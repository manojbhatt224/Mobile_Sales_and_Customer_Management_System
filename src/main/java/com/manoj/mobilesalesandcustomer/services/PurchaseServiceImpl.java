package com.manoj.mobilesalesandcustomer.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Purchase;
import com.manoj.mobilesalesandcustomer.repository.PurchaseRepository;
@Service
public class PurchaseServiceImpl implements PurchaseService {
@Autowired
PurchaseRepository purRepo;
	@Override
	public void insertPurchase(Purchase purchase) {
		this.purRepo.save(purchase);
	}

	@Override
	public void deletePurchase(Integer purchaseid) {
		this.purRepo.deleteById(purchaseid);

	}

	@Override
	public Purchase findPurchaseById(Integer purid) {
		// TODO Auto-generated method stub
		Optional<Purchase> optional =purRepo.findById(purid);
		Purchase pur=null;
		if(optional.isPresent()) {
			pur=optional.get();
		}
		else
		{
			throw new RuntimeException("Purchase not found for id:"+purid);
		}
		return pur;
	}

	@Override
	public List<Purchase> listAllPurchases() {
		
		return this.purRepo.findAll();
	}

	@Override
	public List<Purchase> listPurchasewithdate(Date d1, Date d2) {

		return this.purRepo.findAllByDateGreaterThanEqualAndDateLessThanEqual(d1, d2);
	}

	@Override
	public Page<Purchase> pagePurchases(Pageable page) {
		return this.purRepo.findAll(page);
	}

}
