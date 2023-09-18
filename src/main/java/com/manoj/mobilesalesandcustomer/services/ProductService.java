package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;

public interface ProductService {
	void insertProduct(Product pro);
	void deleteProduct(Integer pid);
	Product searchProductById(Integer id);
	Product searchProductByPname(String name);
	List<Product> searchListProduct();
	List<Product> searchListProductByDealId(Integer did);
	Page<Product> searchPageProduct(Pageable page);
	Page<Product> searchbykeywordProduct(String keyword, Pageable page);
	List<Product> searchByManufacturer(Manufacturer manu);
}


