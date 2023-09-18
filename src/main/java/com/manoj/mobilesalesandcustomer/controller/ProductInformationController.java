package com.manoj.mobilesalesandcustomer.controller;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manoj.mobilesalesandcustomer.FileUploadUtil;
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
public class ProductInformationController {
	@Autowired
	ManufacturerService manuService;
	@Autowired
	CategoryService catService;
	@Autowired
	DealerService dealService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductService proService;
		@RequestMapping("/manu_register")
		public String insertManufacter(@RequestParam("currentPage") Integer currentPage,@RequestParam("formMode") String formMode, @ModelAttribute("manu") Manufacturer manu, Model model, HttpSession session, Principal principal)
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
			
			try {
				manuService.insertManufacturer(manu);
			}
			catch(DataIntegrityViolationException e) {
				String errorMessage = "Manufacturerer already exists or inserted null value";
		        model.addAttribute("errorMessage", errorMessage);
			}
			model.addAttribute("manu",new Manufacturer());
			if (formMode=="save") {
				currentPage=0;
				
			}
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"mid"));
			Page<Manufacturer> listManus=this.manuService.searchPageManufacturer(pageable);
				    model.addAttribute("listManus", listManus);
					   model.addAttribute("currentPage",currentPage);
					   model.addAttribute("formMode", "save");
					   model.addAttribute("totalPages",listManus.getTotalPages());
			 return "normal/usermanufacturer";

		       
		
		}
		@RequestMapping("/updatemanu/{mid}")
		public String updateManufacterForm(@PathVariable(value="mid") Integer mid,@RequestParam("currentPage") Integer currentPage, Model model, HttpSession session, Principal principal)
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
			
			Manufacturer manuedit=manuService.searchManufacturerById(mid);
			model.addAttribute("manu",manuedit);
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"mid"));
			Page<Manufacturer> listManus=this.manuService.searchPageManufacturer(pageable);
			model.addAttribute("formMode", "edit");
			model.addAttribute("title", "Update Manufacturer");
			model.addAttribute("listManus", listManus);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalPages",listManus.getTotalPages());
				
			
			return "normal/usermanufacturer";
		}
		@RequestMapping("/deletemanu/{mid}")
		public String deleteManufacter(@RequestParam("currentPage") Integer currentPage,@PathVariable(value="mid") Integer mid, Model model, HttpSession session,Principal principal)
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
			manuService.deleteManufacturer(mid);
			model.addAttribute("manu",new Manufacturer());
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"mid"));
			Page<Manufacturer> listManus=this.manuService.searchPageManufacturer(pageable);
		
			model.addAttribute("title", "Update Manufacturer");
			model.addAttribute("listManus", listManus);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("formMode","save");
			model.addAttribute("totalPages",listManus.getTotalPages());
				
			
			return "normal/usermanufacturer";
		}
		@GetMapping("/searchmanu/")
		public String searchManufac(@RequestParam(value="txtSearch") String keyword, Model model, HttpSession session, Principal principal) {
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
				Pageable pageable= PageRequest.of(0, 30);
				Page<Manufacturer> listManus=this.manuService.searchbykeywordManufacturer(keyword, pageable);
					model.addAttribute("title", "Search Results");
				model.addAttribute("listManus", listManus);
				model.addAttribute("formMode","save");
				
				model.addAttribute("currentPage",0);
				model.addAttribute("totalPages",listManus.getTotalPages());
				model.addAttribute("manu",new Manufacturer());
				return "normal/usermanufacturer";
			
		}
//Category Operations
		@RequestMapping("/cat_register")
		public String insertCategory(@RequestParam("currentPage") Integer currentPage,@RequestParam("formMode") String formMode, @ModelAttribute("cat") Category cat, Model model, HttpSession session, Principal principal)
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
			
			try {
				catService.insertCategory(cat);
			}
			catch(DataIntegrityViolationException e) {
				String errorMessage = "Category already exists or inserted null value";
		        model.addAttribute("errorMessage", errorMessage);
			}
			model.addAttribute("cat",new Category());
			if (formMode=="save") {
				currentPage=0;
				
			}
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"cid"));
			Page<Category> listCats=this.catService.searchPageCategory(pageable);
				    model.addAttribute("listCats", listCats);
					   model.addAttribute("currentPage",currentPage);
					   model.addAttribute("formMode", "save");
					   					   model.addAttribute("totalPages",listCats.getTotalPages());
			 model.addAttribute("title","Categories");
					   					   return "normal/usercategories";

		       
		
		}
		@RequestMapping("/updatecat/{cid}")
		public String updateCategoryForm(@PathVariable(value="cid") Integer cid,@RequestParam("currentPage") Integer currentPage, Model model, HttpSession session, Principal principal)
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
			
			Category catedit=catService.searchCategoryById(cid);
			model.addAttribute("cat",catedit);
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"cid"));
			Page<Category> listCats=this.catService.searchPageCategory(pageable);
			model.addAttribute("formMode", "edit");
			model.addAttribute("title", "Update Category");
			model.addAttribute("listCats", listCats);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalPages",listCats.getTotalPages());
				
			
			return "normal/usercategories";
		}
		@RequestMapping("/deletecat/{cid}")
		public String deleteCategory(@RequestParam("currentPage") Integer currentPage,@PathVariable(value="cid") Integer cid, Model model, HttpSession session, Principal principal)
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
			catService.deleteCategory(cid);
			model.addAttribute("cat",new Category());
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"cid"));
			Page<Category> listCats=this.catService.searchPageCategory(pageable);
		
			model.addAttribute("title", "Categories");
			model.addAttribute("listCats", listCats);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("formMode","save");
			model.addAttribute("totalPages",listCats.getTotalPages());
				
			
			return "normal/usercategories";
		}
		@GetMapping("/searchcat/")
		public String searchCategory(@RequestParam(value="txtSearch") String keyword, Model model, HttpSession session, Principal principal) 
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
				
			Pageable pageable= PageRequest.of(0, 30);
				Page<Category> listCats=this.catService.searchbykeywordCategory(keyword, pageable);
					model.addAttribute("title", "Search Results");
				model.addAttribute("listCats", listCats);
				model.addAttribute("formMode","save");
				model.addAttribute("currentPage",0);
				model.addAttribute("totalPages",listCats.getTotalPages());
				model.addAttribute("cat",new Category());
				return "normal/usercategories";
			
		}
//Dealer operations
		
		@RequestMapping(value = "/dealer_register", method = RequestMethod.POST)
		public String registerDealer(@ModelAttribute("dealer") Dealer dealer,
				@RequestParam("manufacturerId") Integer manufacturerId,@RequestParam("currentPage") Integer currentPage, @RequestParam("formMode") String formMode,Model model, HttpSession session, Principal principal) {
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
			List<Manufacturer> manus=manuService.searchListManufacturer();
			try {
				Manufacturer manufacturer = manuService.searchManufacturerById(manufacturerId);
				dealer.setManufacturer(manufacturer);
				this.dealService.insertDealer(dealer);
			}
			catch(DataIntegrityViolationException e) {
				String errorMessage = "Dealer already exists or inserted null value";
		        model.addAttribute("errorMessage", errorMessage);
			}
			model.addAttribute("cat",new Category());
			if (formMode=="save") {
				currentPage=0;
				
			}
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"did"));
			Page<Dealer> listDeal=this.dealService.searchPageDealer(pageable);
				    model.addAttribute("listDeal", listDeal);
					   model.addAttribute("currentPage",currentPage);
					   model.addAttribute("formMode", "save");
					   model.addAttribute("manus",manus);
					   model.addAttribute("dealer", new Dealer());
			model.addAttribute("totalPages",listDeal.getTotalPages());
			 model.addAttribute("title","Dealers");
			 return "normal/userdealers";
	    
		}
		@RequestMapping("/updatedeal/{did}")
		public String updateDealerForm(@PathVariable(value="did") Integer did,@RequestParam("currentPage") Integer currentPage, Model model, HttpSession session, Principal principal)
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
			Dealer dealedit=dealService.searchDealerById(did);
			Manufacturer man=manuService.getManufacturerByDealerId(did);
			List<Manufacturer> manus=manuService.searchListManufacturer();
			model.addAttribute("dealer",dealedit);
			model.addAttribute("choice",man.getMid());
			model.addAttribute("manus", manus);
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"did"));
			Page<Dealer> listDeal=this.dealService.searchPageDealer(pageable);
			model.addAttribute("formMode", "edit");
			
			model.addAttribute("title", "Update Dealer");
			model.addAttribute("selectedManufacturer",dealedit.getManufacturer());
			model.addAttribute("listDeal", listDeal);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalPages",listDeal.getTotalPages());				
			
			return "normal/userdealers";
		}
		@RequestMapping("/deletedeal/{did}")
		public String deleteDealer(@RequestParam("currentPage") Integer currentPage,@PathVariable(value="did") Integer did, Model model, HttpSession session, Principal principal)
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
			
			dealService.deleteDealer(did);
			model.addAttribute("dealer",new Dealer());
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"did"));
			Page<Dealer> listDeal=this.dealService.searchPageDealer(pageable);
		
			model.addAttribute("title", "Dealers");
			model.addAttribute("listDeal", listDeal);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("formMode","save");
			model.addAttribute("totalPages",listDeal.getTotalPages());
				
			
			return "normal/userdealers";
		}
		@GetMapping("/searchdealer/")
		public String searchDealer(@RequestParam(value="txtSearch") String keyword, Model model, HttpSession session) {
				Pageable pageable= PageRequest.of(0, 30);
				Page<Dealer> listDeal=this.dealService.searchbykeywordDealer(keyword, pageable);
				model.addAttribute("title", "Search Results");
				model.addAttribute("listDeal", listDeal);
				model.addAttribute("formMode","save");
				model.addAttribute("currentPage",0);
				model.addAttribute("totalPages",listDeal.getTotalPages());
				model.addAttribute("dealer",new Dealer());
				return "normal/userdealers";
			
		}
//Product Operations
		@RequestMapping(value = "/product_register", method = RequestMethod.POST)
		public String registerProduct(Principal principal,@ModelAttribute("pro") Product pro,
				@RequestParam("dealerId") Integer dealerId,@RequestParam("categoryId") Integer categoryId,@RequestParam("currentPage") Integer currentPage, @RequestParam("formMode") String formMode,Model model, @RequestParam("image") MultipartFile multipartFile) throws IOException {
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
			try {		
				Dealer d = dealService.searchDealerById(dealerId);
				Category c= catService.searchCategoryById(categoryId);
				pro.setCategory(c);
				pro.setDealer(d);
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				  pro.setPhotourl(fileName);
				  this.proService.insertProduct(pro);
				  String uploadDir = "product-photos/"+pro.getPid();
				  FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
				
			}
			catch(DataIntegrityViolationException e) {
				String errorMessage = "Product already exists or inserted null value";
		        model.addAttribute("errorMessage", errorMessage);
			}
			
		
			 List<Dealer> dealers= dealService.searchListDealer();
		        List<Category> cats=catService.searchListCategory(); 
			    model.addAttribute("dealers", dealers);
		        model.addAttribute("categories",cats);
			model.addAttribute("pro",new Product());
			if (formMode=="save") {
				currentPage=0;
				
			}
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"pid"));
			Page<Product> listPro=this.proService.searchPageProduct(pageable);
				    model.addAttribute("listProduct", listPro);
					   model.addAttribute("currentPage",currentPage);
					   model.addAttribute("formMode", "save");
					   
					 
			model.addAttribute("totalPages",listPro.getTotalPages());
			 model.addAttribute("title","Products");
			 return "normal/userproducts";
	    
		}
		@RequestMapping("/updateproduct/{pid}")
		public String updateProductForm(Principal principal,@PathVariable(value="pid") Integer pid,@RequestParam("currentPage") Integer currentPage, Model model, HttpSession session)
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
			Dealer deal=dealService.searchDealerByProductId(pid);
			Category cat=catService.searchCategoryByProductId(pid);
			Product productedit=proService.searchProductById(pid);
			model.addAttribute("pro",productedit);
			List<Dealer> dealers= dealService.searchListDealer();
	        List<Category> cats=catService.searchListCategory(); 
		    model.addAttribute("dealers", dealers);
	        model.addAttribute("categories",cats);
	        model.addAttribute("dealchoice",deal.getDid());
	        model.addAttribute("catchoice",cat.getCid());
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"pid"));
			Page<Product> listPro=this.proService.searchPageProduct(pageable);
			model.addAttribute("formMode", "edit");
			model.addAttribute("title", "Update Product");
			model.addAttribute("listProduct", listPro);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalPages",listPro.getTotalPages());				
			
			return "normal/userproducts";
		}
		@RequestMapping("/deleteproduct/{pid}")
		public String deleteProduct(Principal principal, @RequestParam("currentPage") Integer currentPage,@PathVariable(value="pid") Integer pid, Model model, HttpSession session)
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
			proService.deleteProduct(pid);
			model.addAttribute("pro",new Product());
			
			Pageable pageable= PageRequest.of(currentPage, 3, Sort.by(Sort.Direction.DESC,"pid"));
			Page<Product> listPro=this.proService.searchPageProduct(pageable);
			model.addAttribute("formMode", "save");
			model.addAttribute("title", "Products");
			model.addAttribute("listProduct", listPro);
			model.addAttribute("currentPage",currentPage);
			model.addAttribute("totalPages",listPro.getTotalPages());
				
			
			return "normal/userproducts";
		}
		@GetMapping("/searchproduct/")
		public String searchProduct(Principal principal,@RequestParam(value="txtSearch") String keyword, Model model, HttpSession session) {
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
				Pageable pageable= PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC,"pid"));
				Page<Product> listPro=this.proService.searchbykeywordProduct(keyword, pageable);
				model.addAttribute("formMode", "save");
				model.addAttribute("title", "Search Results");
				model.addAttribute("listProduct", listPro);
				model.addAttribute("currentPage",0);
				model.addAttribute("totalPages",listPro.getTotalPages());
								
				model.addAttribute("currentPage",0);
				model.addAttribute("totalPages",listPro.getTotalPages());
				model.addAttribute("pro",new Product());
				return "normal/userproducts";
			
		}
}
