package com.manoj.mobilesalesandcustomer.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manoj.mobilesalesandcustomer.model.Cart;
import com.manoj.mobilesalesandcustomer.model.Dealer;
import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;
import com.manoj.mobilesalesandcustomer.model.Purchase;
import com.manoj.mobilesalesandcustomer.model.Purchaselog;
import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;
import com.manoj.mobilesalesandcustomer.services.CategoryService;
import com.manoj.mobilesalesandcustomer.services.DealerService;
import com.manoj.mobilesalesandcustomer.services.ManufacturerService;
import com.manoj.mobilesalesandcustomer.services.ProductService;
import com.manoj.mobilesalesandcustomer.services.PurchaseService;
import com.manoj.mobilesalesandcustomer.services.PurchaselogService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class PurchaseController {
	@Autowired
	ManufacturerService manuService;
	@Autowired
	CategoryService catService;
	@Autowired
	DealerService dealService;
	@Autowired
	ProductService proService;
	@Autowired
	PurchaseService purService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PurchaselogService plService;
	private List<Cart> cart = new ArrayList<>();

	@RequestMapping("/purchase/{page}")
	public String userPurchase(Principal principal, @PathVariable("page") Integer page, Model model) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		cart.clear();
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		model.addAttribute("dealers", new ArrayList<Dealer>());
		model.addAttribute("productnew", new Cart());
		model.addAttribute("cart", cart);
		model.addAttribute("title", "Purchase");
		model.addAttribute("formMode", "save");

		return "normal/userpurchase";
	}

	@GetMapping("/dealerwrtmanufacturer")
	public String fetchDealerWrtManu(Principal principal, @RequestParam("manufacturer") Integer manufacturerId,
			Model model, HttpSession session) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		cart.clear();
		model.addAttribute("cart", cart);
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		model.addAttribute("manuchoice", manufacturerId);
		List<Dealer> dealers = dealService.searchListByManufacturerId(manufacturerId);
		session.setAttribute("manuId", manufacturerId);
		model.addAttribute("dealers", dealers);
		return "normal/userpurchase";
	}

	@GetMapping("/productwrtdealer")
	public String fetchProductWrtDealer(Principal principal, @RequestParam("dealer") Integer dealerId, Model model,
			HttpSession session) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		cart.clear();
		model.addAttribute("cart", cart);
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		Manufacturer man = manuService.getManufacturerByDealerId(dealerId);
		List<Dealer> dealers = dealService.searchListByManufacturerId(man.getMid());
		model.addAttribute("dealers", dealers);
		List<Product> products = proService.searchListProductByDealId(dealerId);
		model.addAttribute("manuchoice", man.getMid());
		model.addAttribute("dealchoice", dealerId);
		model.addAttribute("products", products);

		return "normal/userpurchase";

	}

	@PostMapping("/addToCart")
	public String addToCart(Principal principal, @ModelAttribute("productnew") Cart product, Model model,
			HttpSession session) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		Product p = proService.searchProductByPname(product.getPname());
		Dealer d = dealService.searchDealerByProductId(p.getPid());
		Manufacturer m = manuService.getManufacturerByDealerId(d.getDid());
		List<Dealer> dl = dealService.searchListByManufacturerId(m.getMid());
		model.addAttribute("dealers", dl);
		model.addAttribute("manuchoice", m.getMid());
		model.addAttribute("dealchoice", d.getDid());
		List<Product> products = proService.searchListProductByDealId(d.getDid());

		model.addAttribute("products", products);
		cart.add(product);
		model.addAttribute("cart", cart);
		return "normal/userpurchase";
	}

	@GetMapping("/removeFromCart/{cpname}")
	public String removeFromCart(Principal principal, @PathVariable(value = "cpname") String cpname,
			@ModelAttribute("product") Cart product, Model model, HttpSession session) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		Product p = proService.searchProductByPname(cpname);
		Dealer d = dealService.searchDealerByProductId(p.getPid());
		Manufacturer m = manuService.getManufacturerByDealerId(d.getDid());
		List<Dealer> dl = dealService.searchListByManufacturerId(m.getMid());
		model.addAttribute("dealers", dl);
		model.addAttribute("manuchoice", m.getMid());
		model.addAttribute("dealchoice", d.getDid());
		List<Product> products = proService.searchListProductByDealId(d.getDid());

//		  List<Manufacturer> manufacturers=manuService.searchListManufacturer(); 
//		    model.addAttribute("manufacturers", manufacturers); 
//		 Integer dealId=(Integer)session.getAttribute("dealId");
//	       List<Product> products=proService.searchListProductByDealId(dealId);
		model.addAttribute("products", products);
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			if (c.getPname().equals(cpname)) {
				iterator.remove();
			}
		}
		model.addAttribute("cart", cart);
		return "normal/userpurchase";
	}

	@GetMapping("/submitpurchase")
	public String purchase(Principal principal,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
			@RequestParam("dealer") Integer did, Model model) {
		String userName = principal.getName();

		Optional<User> user = userRepository.findByEmail(userName);
		if (user.isPresent()) {
			User usar = user.get();

			model.addAttribute("myname", usar.getName().toUpperCase());
			model.addAttribute("retailer", usar.getNameOfRetailer().toUpperCase());
			model.addAttribute("myid", usar.getId());
			model.addAttribute("addretailer", usar.getAddressOfRetailer().toUpperCase());
		} else {
			model.addAttribute("myname", "Mr. Surendra Saud".toUpperCase());
			model.addAttribute("retailer", "City Mobile Care Center".toUpperCase());
			model.addAttribute("myid", 0);
			model.addAttribute("addretailer", "Mahendrangar, Kanchanpur");
		}
		Purchase purchasenew = new Purchase();
		Purchaselog pl = new Purchaselog();

		Product prostock = new Product();
		double totalBill = 0.0;

		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			prostock = proService.searchProductByPname(c.getPname());
			if (prostock != null)
				prostock.setStock(prostock.getStock() + c.getPquantity());
			proService.insertProduct(prostock);
			totalBill += c.getPprice() * c.getPquantity();
		}

		double vat = 0.13 * totalBill;
		double vatedbill = totalBill + vat;
		vat = Math.round(vat * 100.0) / 100.0;
		vatedbill = Math.round(vatedbill * 100.0) / 100.0;
		purchasenew.setDate(date);
		purchasenew.setDealer(dealService.searchDealerById(did));
		purchasenew.setVat(vat);
		purchasenew.setVatedbill(vatedbill);
		this.purService.insertPurchase(purchasenew);
		Purchase currentPur = this.purService.findPurchaseById(purchasenew.getPurid());
		Iterator<Cart> iterator1 = cart.iterator();
		while (iterator1.hasNext()) {
			Cart c = iterator1.next();
			prostock = proService.searchProductByPname(c.getPname());
			if (prostock != null)
				pl = new Purchaselog(c.getPname(), c.getPquantity(), c.getPprice());
			pl.setPurchase(currentPur);
			this.plService.insertPurchaselog(pl);

			totalBill += c.getPprice() * c.getPquantity();
		}

		return "redirect:/user/purchase/0";
	}
}