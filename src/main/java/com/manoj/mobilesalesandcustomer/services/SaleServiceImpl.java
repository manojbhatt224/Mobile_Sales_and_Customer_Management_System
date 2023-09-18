package com.manoj.mobilesalesandcustomer.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Sale;
import com.manoj.mobilesalesandcustomer.repository.SaleRepository;

@Service
public class SaleServiceImpl implements SaleService{
@Autowired
SaleRepository salRepo;
	@Override
	public void insertSale(Sale sale) {
		this.salRepo.save(sale);	
	}

	@Override
	public void deleteSale(Integer saleid) {
		this.salRepo.deleteById(saleid);
	}

	@Override
	public Sale findSaleById(Integer salid) {
		Optional<Sale> optional =salRepo.findById(salid);
		Sale sal=null;
		if(optional.isPresent()) {
			sal=optional.get();
		}
		else
		{
			throw new RuntimeException("Sale not found for id:"+salid);
		}
		return sal;
	}

	@Override
	public List<Sale> listSalewithdate(Date d1, Date d2) {
		return this.salRepo.findAllByDateGreaterThanEqualAndDateLessThanEqual(d1, d2);
	}

	@Override
	public Page<Sale> pageSale(Pageable page) {
		return this.salRepo.findAll(page);	}

}
