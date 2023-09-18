package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import com.manoj.mobilesalesandcustomer.model.Salelog;

public interface SalelogService {
	void insertSalelog(Salelog sl);
	void deleteSalelog(Integer slid);
	List<Salelog> listAllSaleLog(Integer saleid);
}
