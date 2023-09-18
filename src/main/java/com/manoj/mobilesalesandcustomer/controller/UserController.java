package com.manoj.mobilesalesandcustomer.controller;




import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.manoj.mobilesalesandcustomer.model.Category;
import com.manoj.mobilesalesandcustomer.model.Dealer;
import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;
import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;
import com.manoj.mobilesalesandcustomer.services.CategoryService;
import com.manoj.mobilesalesandcustomer.services.DealerService;
import com.manoj.mobilesalesandcustomer.services.ManufacturerService;
import com.manoj.mobilesalesandcustomer.services.ProductService;

import jakarta.servlet.http.HttpSession;
@Controller
@RequestMapping("/user")
public class UserController
{
	@Autowired
	DealerService dealService;
	@Autowired
	ProductService proService;
	@Autowired
	CategoryService catService;
	@Autowired
	private UserRepository userRepository;
@Autowired
private ManufacturerService manuService;

	@RequestMapping("/index")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userHome(Model model, Principal principal)
	{
		String userName=principal.getName();
		System.out.println("Username:"+userName);
		Optional<User> user=userRepository.findByEmail(userName);
		if (user.isPresent()) {
		    User usar = user.get();
		    List<Product> listPro=this.proService.searchListProduct();
 	    	model.addAttribute("listCardProduct", listPro);
   model.addAttribute("title","Products");

		    model.addAttribute("myname",usar.getName().toUpperCase());
		    model.addAttribute("retailer",usar.getNameOfRetailer().toUpperCase());
		    model.addAttribute("myid",usar.getId());
		    model.addAttribute("addretailer",usar.getAddressOfRetailer().toUpperCase());
		    model.addAttribute("title", "My Dashboard");
			return "normal/userhome";
		} else
		{
			model.addAttribute("myname","User");
		    model.addAttribute("title", "My Dashboard");
			return "normal/userhome";
		}
		
	
		
	}
	@RequestMapping("/manufacturers/{page}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userManufacturer(@PathVariable("page") Integer page, Model model, Principal principal, HttpSession sesssion)
	{
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
		    User usar = user.get();
			   
		    model.addAttribute("myname",usar.getName().toUpperCase());
		    model.addAttribute("retailer",usar.getNameOfRetailer().toUpperCase());
		    model.addAttribute("myid",usar.getId());
		    model.addAttribute("addretailer",usar.getAddressOfRetailer().toUpperCase());
		    model.addAttribute("title", "My Dashboard");
			model.addAttribute("manu", new Manufacturer());
			Pageable pageable = PageRequest.of(page, 3, Sort.by(Sort.Direction.DESC, "mid"));
			Page<Manufacturer> listManus = this.manuService.searchPageManufacturer(pageable);
			if (page != 0 && page >= listManus.getTotalPages()) {
				page = listManus.getTotalPages() - 1;
				Pageable pageablenew = PageRequest.of(page, 3);
				Page<Manufacturer> listManusnew = this.manuService.searchPageManufacturer(pageablenew);

				model.addAttribute("listManus", listManusnew);
				model.addAttribute("currentPage", page);
				model.addAttribute("totalPages", listManusnew.getTotalPages());
			} else {
				model.addAttribute("currentPage", page);
				model.addAttribute("totalPages", listManus.getTotalPages());
				model.addAttribute("listManus", listManus);
			}
			model.addAttribute("title", "Manufacturers");
			model.addAttribute("formMode", "save");
			return "normal/usermanufacturer";
		} else {
			model.addAttribute("myname", "User");
			model.addAttribute("title", "Manufacturers");
			return "normal/usermanufacturer";
		}
				 	
		
	}
	@RequestMapping("/categories/{page}")
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public String userCategory(@PathVariable("page") Integer page, Model model,HttpSession sesssion,Principal principal)
	{
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
		    User usar = user.get();
			   
		    model.addAttribute("myname",usar.getName().toUpperCase());
		    model.addAttribute("retailer",usar.getNameOfRetailer().toUpperCase());
		    model.addAttribute("myid",usar.getId());
		    model.addAttribute("addretailer",usar.getAddressOfRetailer().toUpperCase());
		}
		else
		{
			 model.addAttribute("myname","Mr. Surendra Saud".toUpperCase());
			    model.addAttribute("retailer","City Mobile Care Center".toUpperCase());
			    model.addAttribute("myid",0);
			    model.addAttribute("addretailer","Mahendrangar, Kanchanpur");
		}
			model.addAttribute("cat", new Category());
			    Pageable pageable= PageRequest.of(page, 3,Sort.by(Sort.Direction.DESC,"cid"));
		    Page<Category> listCats=this.catService.searchPageCategory(pageable);
		 if (page!=0 && page >= listCats.getTotalPages()) {
	            page = listCats.getTotalPages() - 1;
	            Pageable pageablenew= PageRequest.of(page, 3);
			    Page<Manufacturer> listCatsnew=this.manuService.searchPageManufacturer(pageablenew);
			    
			    model.addAttribute("listCats", listCatsnew);
				   model.addAttribute("currentPage",page);
				   model.addAttribute("totalPages",listCatsnew.getTotalPages());
		    }	    
		    else {
		    	model.addAttribute("currentPage",page);
				   model.addAttribute("totalPages",listCats.getTotalPages());
		   model.addAttribute("listCats", listCats);
		    }
		   model.addAttribute("title","Categories");
		   model.addAttribute("formMode", "save");
		return "normal/usercategories";
		
	}

	@RequestMapping("/dealers/{page}")
public String userDealers(@PathVariable("page") Integer page,Model model,Principal principal)
	{
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
		    User usar = user.get();
			   
		    model.addAttribute("myname",usar.getName().toUpperCase());
		    model.addAttribute("retailer",usar.getNameOfRetailer().toUpperCase());
		    model.addAttribute("myid",usar.getId());
		    model.addAttribute("addretailer",usar.getAddressOfRetailer().toUpperCase());
		}
		else
		{
			 model.addAttribute("myname","Mr. Surendra Saud".toUpperCase());
			    model.addAttribute("retailer","City Mobile Care Center".toUpperCase());
			    model.addAttribute("myid",0);
			    model.addAttribute("addretailer","Mahendrangar, Kanchanpur");
		}
		
		    List<Manufacturer> manus= manuService.searchListManufacturer();
	        model.addAttribute("manus", manus);

	        model.addAttribute("dealer", new Dealer());
		    Pageable pageable= PageRequest.of(page, 3,Sort.by(Sort.Direction.DESC,"did"));
	    Page<Dealer> listDealer=this.dealService.searchPageDealer(pageable);
	 if (page!=0 && page >= listDealer.getTotalPages()) {
            page = listDealer.getTotalPages() - 1;
            Pageable pageablenew= PageRequest.of(page, 3);
		    Page<Dealer> listDealernew=this.dealService.searchPageDealer(pageablenew);
		    
		    model.addAttribute("listDeal", listDealernew);
			   model.addAttribute("currentPage",page);
			   model.addAttribute("totalPages",listDealernew.getTotalPages());
	    }	    
	    else {
	    	model.addAttribute("currentPage",page);
			   model.addAttribute("totalPages",listDealer.getTotalPages());
	   model.addAttribute("listDeal", listDealer);
	    }
	   model.addAttribute("title","Dealers");
	   model.addAttribute("formMode", "save");
   
	   return "normal/userdealers";
	}
	
	@RequestMapping("/products/{page}")
	public String userProducts(@PathVariable("page") Integer page,Model model,Principal principal)
		{
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
		    User usar = user.get();
			   
		    model.addAttribute("myname",usar.getName().toUpperCase());
		    model.addAttribute("retailer",usar.getNameOfRetailer().toUpperCase());
		    model.addAttribute("myid",usar.getId());
		    model.addAttribute("addretailer",usar.getAddressOfRetailer().toUpperCase());
		}
		else
		{
			 model.addAttribute("myname","Mr. Surendra Saud".toUpperCase());
			    model.addAttribute("retailer","City Mobile Care Center".toUpperCase());
			    model.addAttribute("myid",0);
			    model.addAttribute("addretailer","Mahendrangar, Kanchanpur");
		}
			    List<Dealer> dealers= dealService.searchListDealer();
		        List<Category> cats=catService.searchListCategory(); 
			    model.addAttribute("dealers", dealers);
		        model.addAttribute("categories",cats);
		        model.addAttribute("pro", new Product());
			    Pageable pageable= PageRequest.of(page, 3,Sort.by(Sort.Direction.DESC,"pid"));
			    Page<Product> listPro=this.proService.searchPageProduct(pageable);
		 if (page!=0 && page >= listPro.getTotalPages()) {
	            page = listPro.getTotalPages() - 1;
	            Pageable pageablenew= PageRequest.of(page, 3);
			    Page<Product> listPronew=this.proService.searchPageProduct(pageablenew);
	    
			    model.addAttribute("listProduct", listPronew);
				   model.addAttribute("currentPage",page);
				   model.addAttribute("totalPages",listPronew.getTotalPages());
		    }	    
		    else {
		    	model.addAttribute("listProduct", listPro);
		    	model.addAttribute("currentPage",page);
		    	model.addAttribute("totalPages",listPro.getTotalPages());

		    }
		   model.addAttribute("title","Products");
		   model.addAttribute("formMode", "save");
	   
		   return "normal/userproducts";
		}



	
}






	
	