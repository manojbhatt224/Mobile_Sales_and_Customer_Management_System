package com.manoj.mobilesalesandcustomer.controller;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
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
import com.manoj.mobilesalesandcustomer.model.Customer;
import com.manoj.mobilesalesandcustomer.model.Dealer;
import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.model.Product;
import com.manoj.mobilesalesandcustomer.model.Sale;
import com.manoj.mobilesalesandcustomer.model.Salelog;
import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;
import com.manoj.mobilesalesandcustomer.services.CategoryService;
import com.manoj.mobilesalesandcustomer.services.CustomerService;
import com.manoj.mobilesalesandcustomer.services.DealerService;
import com.manoj.mobilesalesandcustomer.services.ManufacturerService;
import com.manoj.mobilesalesandcustomer.services.ProductService;
import com.manoj.mobilesalesandcustomer.services.PurchaseService;
import com.manoj.mobilesalesandcustomer.services.PurchaselogService;
import com.manoj.mobilesalesandcustomer.services.SaleService;
import com.manoj.mobilesalesandcustomer.services.SalelogService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class SaleController {
	@Autowired
	ManufacturerService manuService;
	@Autowired
	CategoryService catService;
	@Autowired
	DealerService dealService;
	@Autowired
	ProductService proService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	PurchaseService purService;
	@Autowired
	PurchaselogService plService;
	@Autowired
	SaleService salService;
	@Autowired
	SalelogService sallogService;
	@Autowired
	CustomerService custService;
	private List<Cart> cart = new ArrayList<>();

	private boolean cartProductIsAvailable(String cpname) {
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			if (c.getPname().equals(cpname)) {
				System.out.println("Product Detected in cart");
				return true;
			}
		}
		return false;
	}

	private void cartPrint() {
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			System.out.println(c.getPname() + "_" + c.getPquantity());
		}
	}

	private boolean updateCartWithNewStock(String cpname, Integer quan) {
		Integer sum = 0;
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			Product prod = proService.searchProductByPname(cpname);
			if (c.getPname().equals(cpname)) {
				sum = c.getPquantity() + quan;
				if (prod.isSaleStock(sum)) {
					c.setPquantity(sum);
					System.out.println("Product stock updated in cart");
					return true;
				}

			}

		}
		return false;

	}

	@RequestMapping("/sale/{page}")
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

		return "normal/usersales";
	}

	@GetMapping("/saleproductwrtmanufacturer")
	public String fetchProductWrtManu(Principal principal, @RequestParam("manufacturer") Integer manufacturerId,
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
		model.addAttribute("cart", cart);
		cartPrint();
		List<Product> products = proService.searchByManufacturer(manuService.searchManufacturerById(manufacturerId));
		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		model.addAttribute("products", products);
		model.addAttribute("manuchoice", manufacturerId);
		return "normal/usersales";
	}

	@PostMapping("/saleaddToCart")
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
		List<Product> products = proService.searchByManufacturer(m);
		model.addAttribute("products", products);
		model.addAttribute("manuchoice", m.getMid());

		if (!p.isStockEmpty() && p.isSaleStock(product.getPquantity())) {
			System.out.println("Input Stock Available");
			System.out.println("Product:" + product.getPname());
			if (cartProductIsAvailable(product.getPname())) {
				System.out.println("Product is found in cart");
				if (updateCartWithNewStock(product.getPname(), product.getPquantity())) {
					System.out.println("Cart update stock available");
					cartPrint();
					model.addAttribute("cart", cart);
				} else {
					System.out.println("Cart not update stock not available");
					cartPrint();
					model.addAttribute("cart", cart);
					model.addAttribute("errorMessage", "Product already and net stock not available.");
				}
			} else {
				System.out.println("Product is not found in cart");
				cart.add(product);
				cartPrint();
				model.addAttribute("cart", cart);
			}
		}

		else {
			cartPrint();
			model.addAttribute("cart", cart);
			model.addAttribute("errorMessage", "Stock not available!");
		}

		return "normal/usersales";
	}

	@GetMapping("/salremoveFromCart")
	public String removeFromCart(Principal principal, @RequestParam("cpname") String cpname,
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
		try {
			cpname = java.net.URLDecoder.decode(cpname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// Handle the exception appropriately
			e.printStackTrace();
		}

		List<Manufacturer> manufacturers = manuService.searchListManufacturer();
		model.addAttribute("manufacturers", manufacturers);
		Manufacturer m = manuService.getManufacturerByProductName(cpname);
		model.addAttribute("manuchoice", m.getMid());
		List<Product> products = proService.searchByManufacturer(m);

		model.addAttribute("products", products);
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			if (c.getPname().equals(cpname)) {
				iterator.remove();
			}
		}
		model.addAttribute("cart", cart);
		return "normal/usersales";
	}

	public static Date convertToDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	@GetMapping("/submitsale")
	public String purchase(Principal principal,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
			@RequestParam("cname") String cName, @RequestParam("cemail") String cEmail,
			@RequestParam("cphone") String cPhone, @RequestParam("cadd") String cAdd, Model model) {
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
		Customer cus = new Customer(cName, cPhone, cEmail, cAdd);
		System.out.println(cus.getName() + cus.getEmail() + cus.getPhone());
		try {
			custService.insertCustomer(cus);
		} catch (Exception ex) {
			List<Manufacturer> manufacturers = manuService.searchListManufacturer();
			model.addAttribute("manufacturers", manufacturers);
			model.addAttribute("dealers", new ArrayList<Dealer>());
			model.addAttribute("productnew", new Cart());
			model.addAttribute("cart", cart);
			model.addAttribute("title", "Purchase");
			model.addAttribute("formMode", "save");
			model.addAttribute("cart", cart);
			model.addAttribute("errorMessage", "Complete Required Fields for Customer");
			return "normal/usersales";
		}

		Sale salenew = new Sale();
		Salelog sl = new Salelog();
		Product prostock = new Product();
		double totalBill = 0.0;
		Iterator<Cart> iterator = cart.iterator();
		while (iterator.hasNext()) {
			Cart c = iterator.next();
			prostock = proService.searchProductByPname(c.getPname());
			if (prostock != null)
				prostock.setStock(prostock.getStock() - c.getPquantity());
			proService.insertProduct(prostock);

			System.out.println(
					c.getPname() + "		" + c.getPquantity() + "		" + (c.getPprice() * c.getPquantity()));
			totalBill += c.getPprice() * c.getPquantity();
		}

		double vat = 0.13 * totalBill;
		double vatedbill = totalBill + vat;
		vat = Math.round(vat * 100.0) / 100.0;
		vatedbill = Math.round(vatedbill * 100.0) / 100.0;

		salenew.setDate(date);
		salenew.setVat(vat);
		salenew.setVatedbill(vatedbill);

		Customer cuss = custService.searchCustomerByPhone(cus.getPhone());
		salenew.setCustomer(cuss);
		this.salService.insertSale(salenew);
		Sale currentSal = this.salService.findSaleById(salenew.getSalid());
		Iterator<Cart> iterator1 = cart.iterator();
		while (iterator1.hasNext()) {
			Cart c = iterator1.next();
			prostock = proService.searchProductByPname(c.getPname());
			if (prostock != null) {
				sl = new Salelog(c.getPname(), c.getPquantity(), c.getPprice());
				sl.setSale(currentSal);
				this.sallogService.insertSalelog(sl);
			}
		}

		return "redirect:/user/sale/0";
	}

}
