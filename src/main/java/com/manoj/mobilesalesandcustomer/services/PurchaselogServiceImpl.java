package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Purchaselog;
import com.manoj.mobilesalesandcustomer.repository.PurchaselogRepository;
@Service
public class PurchaselogServiceImpl implements PurchaselogService {
@Autowired
PurchaselogRepository plRepo;
	@Override
	public void insertPurchaselog(Purchaselog pl) {
		this.plRepo.save(pl);
	}

	@Override
	public void deletePurchaselog(Integer plid) {
		this.plRepo.deleteById(plid);

	}

	@Override
	public List<Purchaselog> listAllPurchaseLog(Integer purchaseid) {
		// TODO Auto-generated method stub
		return this.plRepo.findByPurchasePurid(purchaseid);
	}

}
