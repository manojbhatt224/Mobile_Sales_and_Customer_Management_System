package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Salelog;
import com.manoj.mobilesalesandcustomer.repository.SalelogRepository;
@Service
public class SalelogServiceImpl implements SalelogService {
@Autowired
SalelogRepository slRepo;
	@Override
	public void insertSalelog(Salelog sl) {
		this.slRepo.save(sl);

	}

	@Override
	public void deleteSalelog(Integer slid) {
		this.slRepo.deleteById(slid);

	}

	@Override
	public List<Salelog> listAllSaleLog(Integer saleid) {
		return this.slRepo.findBySaleSalid(saleid);
	}

}
