package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Category;

public interface CategoryService {
	void insertCategory(Category cat);
	void deleteCategory(Integer catid);
	Category searchCategoryById(Integer id);
	List<Category> searchListCategory();
	Page<Category> searchPageCategory(Pageable page);
	Page<Category> searchbykeywordCategory(String keyword, Pageable page);
	Category searchCategoryByProductId(Integer pid);
}
