package com.manoj.mobilesalesandcustomer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Category;
import com.manoj.mobilesalesandcustomer.repository.CategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService {
@Autowired
CategoryRepository catRepo;
	@Override
	public void insertCategory(Category cat) {
		cat.capsCategory();
		catRepo.save(cat);
		
	}

	@Override
	public void deleteCategory(Integer catid) {
		catRepo.deleteById(catid);
		
	}

	@Override
	public Category searchCategoryById(Integer id) {
		Optional<Category> optional =catRepo.findById(id);
		Category cat=null;
		if(optional.isPresent()) {
			cat=optional.get();
		}
		else
		{
			throw new RuntimeException("Manufacturer not found for id:"+id);
		}
		return cat;
	}

	@Override
	public Page<Category> searchPageCategory(Pageable page) {
		return catRepo.findAll(page);
	}

	@Override
	public Page<Category> searchbykeywordCategory(String keyword, Pageable page) {
		return catRepo.findByCnameContaining(keyword, page);
	}

	@Override
	public List<Category> searchListCategory() {

		return this.catRepo.findAll();
	}

	@Override
	public Category searchCategoryByProductId(Integer pid) {
		// TODO Auto-generated method stub
		return this.catRepo.findByProductsPid(pid);
	}
	

}
