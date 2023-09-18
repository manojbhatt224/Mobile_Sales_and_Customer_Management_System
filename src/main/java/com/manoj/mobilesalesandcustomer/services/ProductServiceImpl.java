package com.manoj.mobilesalesandcustomer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;
import com.manoj.mobilesalesandcustomer.repository.ProductRepository;
@Service
public class ProductServiceImpl implements ProductService {
@Autowired
ProductRepository proRepo;
	@Override
	public void insertProduct(Product pro) {
		pro.capsProduct();
		this.proRepo.save(pro);
	}

	@Override
	public void deleteProduct(Integer pid) {
		this.proRepo.deleteById(pid);
		
	}

	@Override
	public Product searchProductById(Integer id) {
		
		Optional<Product> optional =proRepo.findById(id);
		Product pro=null;
		if(optional.isPresent()) {
			pro=optional.get();
		}
		else
		{
			throw new RuntimeException("Product not found for id:"+id);
		}
		return pro;
	}

	@Override
	public List<Product> searchListProduct() {
return this.proRepo.findAll();
	
	}

	@Override
	public Page<Product> searchPageProduct(Pageable page) {
		
		return this.proRepo.findAll(page);
	}

	@Override
	public Page<Product> searchbykeywordProduct(String keyword, Pageable page) {

		return this.proRepo.findByPnameContainingOrPdorContaining(keyword, keyword, page);
	}

	@Override
	public List<Product> searchListProductByDealId(Integer did) {
		// TODO Auto-generated method stub
		return this.proRepo.findByDealerDid(did);
	}

	@Override
	public Product searchProductByPname(String name) {
		return proRepo.findByPname(name);
	
	}

	@Override
	public List<Product> searchByManufacturer(Manufacturer manu) {
		
		return this.proRepo.findByDealer_Manufacturer(manu);
	}

}
