package com.manoj.mobilesalesandcustomer.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Sale;

public interface SaleService {
	void insertSale(Sale sale);
	void deleteSale(Integer saleid);
	Sale findSaleById(Integer salid);
	List<Sale> listSalewithdate (Date d1, Date d2);
	Page<Sale> pageSale(Pageable page);


}
