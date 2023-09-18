package com.manoj.mobilesalesandcustomer.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.manoj.mobilesalesandcustomer.model.Purchase;
import com.manoj.mobilesalesandcustomer.model.Purchaselog;
import com.manoj.mobilesalesandcustomer.model.Sale;
import com.manoj.mobilesalesandcustomer.model.Salelog;
import com.manoj.mobilesalesandcustomer.model.User;
import com.manoj.mobilesalesandcustomer.repository.UserRepository;
import com.manoj.mobilesalesandcustomer.services.ManufacturerService;
import com.manoj.mobilesalesandcustomer.services.PdfReportService;
import com.manoj.mobilesalesandcustomer.services.ProductService;
import com.manoj.mobilesalesandcustomer.services.PurchaseService;
import com.manoj.mobilesalesandcustomer.services.PurchaselogService;
import com.manoj.mobilesalesandcustomer.services.SaleService;
import com.manoj.mobilesalesandcustomer.services.SalelogService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/user")
public class ReportController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	ManufacturerService ms;
	@Autowired
	PurchaseService ps;
	@Autowired
	PurchaselogService pls;
	@Autowired
	SaleService ss;
	@Autowired
	SalelogService sls;
	@Autowired
	ProductService proService;
	@Autowired
	PdfReportService pdfReportService;

	@RequestMapping("/reports/{page}")
	public String userReport(Principal principal,@PathVariable("page") Integer page, Model model) {
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
		return "normal/userreports";
	}

	@RequestMapping("/purchasereport/{page}")
	public String userpurchaseReport(Principal principal,@PathVariable("page") Integer page, Model model) {
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
		Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "date"));
		Page<Purchase> listpur = this.ps.pagePurchases(pageable);
		if (page != 0 && page >= listpur.getTotalPages()) {
			page = listpur.getTotalPages() - 1;
			Pageable pageablenew = PageRequest.of(page, 8);
			Page<Purchase> listpurnew = this.ps.pagePurchases(pageablenew);

			model.addAttribute("listPurchase", listpurnew);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listpurnew.getTotalPages());
		} else {
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listpur.getTotalPages());
			model.addAttribute("listPurchase", listpur);
			model.addAttribute("Dated", "False");
		}
		model.addAttribute("title", "Purchase Reports");
		return "normal/purchasereport";
	}

	@RequestMapping("/viewpurchasedetail/{purid}")
	public String userpurchasedetail(@PathVariable("purid") Integer purid, Model model) {
		Double totalBill = (double) 0;
//		Product prostock = new Product();
		Purchase pur = this.ps.findPurchaseById(purid);
		List<Purchaselog> purloglist = pls.listAllPurchaseLog(purid);
		Iterator<Purchaselog> iterator = purloglist.iterator();
		while (iterator.hasNext()) {
			Purchaselog c = iterator.next();
			//
			totalBill += c.getQuantity() * c.getPrice();
		}

		double vat = 0.13 * totalBill;
		double vatedbill = totalBill + vat;
		vat = Math.round(vat * 100.0) / 100.0;
		vatedbill = Math.round(vatedbill * 100.0) / 100.0;
		SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdfNew.format(pur.getDate());
		model.addAttribute("purchaseId", pur.getPurid());
		model.addAttribute("purchaseDate", formattedDate);
		model.addAttribute("dealerName", pur.getDealer().getDname().toUpperCase());
		model.addAttribute("dealerAddress", pur.getDealer().getDaddress().toUpperCase());
		model.addAttribute("listPurchaseD", purloglist);
		model.addAttribute("Gross_Total", totalBill);
		model.addAttribute("vatAmount", vat);
		model.addAttribute("netTotal", vatedbill);
		return "normal/purchasedetail";
	}

	@RequestMapping("/purchasedetailpdf")
	public String userpurchasedetaildownload(Principal principal,@RequestParam("purchaseId") Integer purid, HttpServletResponse response,
			Model model) {
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
			pdfReportService.generatepurchasedetailPdfReport(purid, response);
			System.out.println("pdf made");

		} catch (IOException e) {
			e.printStackTrace();

		}

		return "redirect:/user/viewpurchasedetail/{purid}";
	}

	public static Date convertToDate(LocalDate localDate) {
		Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

	@RequestMapping("/generate-purchase-report")
	public String generatedatePurchaseReport(Principal principal,@RequestParam("date1") String d1, @RequestParam("date2") String d2,
			Model model, HttpServletResponse response) {
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
		LocalDate parsedDate1 = LocalDate.parse(d1);
		LocalDate parsedDate2 = LocalDate.parse(d2);
		Date date1 = convertToDate(parsedDate1);
		Date date2 = convertToDate(parsedDate2);

		System.out.println("Purchase Data: 1" + date1 + " 2" + date2);

		List<Purchase> lpurs = ps.listPurchasewithdate(date1, date2);
		Collections.sort(lpurs, new Comparator<Purchase>() {
			@Override
			public int compare(Purchase purchase1, Purchase purchase2) {
				return purchase2.getDate().compareTo(purchase1.getDate());
			}
		});
		model.addAttribute("currentPage", null);
		model.addAttribute("totalPages", null);
		model.addAttribute("listPurchase", lpurs);
		model.addAttribute("Dated", "False");
		model.addAttribute("title", "Purchase Reports");

		return "normal/purchasereport";
	}

	@RequestMapping("/print-purchasepdf-report")
	public String generatePdfReport(Principal principal,@RequestParam("date1") String d1, @RequestParam("date2") String d2, Model model,
			HttpServletResponse response) {
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
			pdfReportService.generatepurchasePdfReport(d1, d2, response);

		} catch (IOException e) {
			e.printStackTrace();

		}

		return "normal/userreports";
	}

	@RequestMapping("/salesreport/{page}")
	public String usersaleReport(Principal principal,@PathVariable("page") Integer page, Model model) {
		Pageable pageable = PageRequest.of(page, 8, Sort.by(Sort.Direction.DESC, "date"));
		Page<Sale> listsal = this.ss.pageSale(pageable);
		if (page != 0 && page >= listsal.getTotalPages()) {
			page = listsal.getTotalPages() - 1;
			Pageable pageablenew = PageRequest.of(page, 8);
			Page<Sale> listsalnew = this.ss.pageSale(pageablenew);

			model.addAttribute("listSale", listsalnew);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listsalnew.getTotalPages());
		} else {
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", listsal.getTotalPages());
			model.addAttribute("listSale", listsal);
			model.addAttribute("Dated", "False");
		}
		model.addAttribute("title", "Sale Reports");
		return "normal/salereport";
	}

	@RequestMapping("/generate-sale-report")
	public String generatedateSaleReport(Principal principal,@RequestParam("date1") String d1, @RequestParam("date2") String d2,
			Model model, HttpServletResponse response) {
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
		LocalDate parsedDate1 = LocalDate.parse(d1);
		LocalDate parsedDate2 = LocalDate.parse(d2);
		Date date1 = convertToDate(parsedDate1);
		Date date2 = convertToDate(parsedDate2);

		System.out.println("Sales Data: 1" + date1 + " 2" + date2);

		List<Sale> lsals = ss.listSalewithdate(date1, date2);
		System.out.println(lsals.get(0).getVatedbill());
		Collections.sort(lsals, new Comparator<Sale>() {
			@Override
			public int compare(Sale sale1, Sale sale2) {
				return sale2.getDate().compareTo(sale1.getDate());
			}
		});

		model.addAttribute("currentPage", null);
		model.addAttribute("totalPages", null);
		model.addAttribute("listSale", lsals);
		model.addAttribute("Dated", "False");
		model.addAttribute("title", "Purchase Reports");

		return "normal/salereport";
	}
	@RequestMapping("/viewsaledetail/{salid}")
	public String usersaledetail(Principal principal, @PathVariable("salid") Integer salid, Model model) {
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
		Double totalBill = (double) 0;
//		Product prostock = new Product();
		Sale sal = this.ss.findSaleById(salid);
		List<Salelog> saleloglist = sls.listAllSaleLog(salid);
		Iterator<Salelog> iterator = saleloglist.iterator();
		while (iterator.hasNext()) {
			Salelog c = iterator.next();
			//
			totalBill += c.getQuantity() * c.getPrice();
		}

		double vat = 0.13 * totalBill;
		double vatedbill = totalBill + vat;
		vat = Math.round(vat * 100.0) / 100.0;
		vatedbill = Math.round(vatedbill * 100.0) / 100.0;
		SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = sdfNew.format(sal.getDate());
		model.addAttribute("saleId", sal.getSalid());
		model.addAttribute("saleDate", formattedDate);
		model.addAttribute("customerName", sal.getCustomer().getName().toUpperCase());
		model.addAttribute("customerPhone",sal.getCustomer().getPhone().toUpperCase());
		model.addAttribute("listSaleD", saleloglist);
		model.addAttribute("Gross_Total", totalBill);
		model.addAttribute("vatAmount", vat);
		model.addAttribute("netTotal", vatedbill);
		return "normal/saledetail";
	}
	@RequestMapping("/print-salepdf-report")
	public String generatedateSalePDFReport(Principal principal,@RequestParam("date1") String d1, @RequestParam("date2") String d2,
			Model model, HttpServletResponse response) {
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
			pdfReportService.generatesalePdfReport(d1, d2, response);
		} catch (IOException e) {
			e.printStackTrace();

		}

		return "normal/userreports";
	}
	@RequestMapping("/saledetailpdf")
	public String usersaledetaildownload(Principal principal,@RequestParam("saleId") Integer purid, HttpServletResponse response,
			Model model) {
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
			pdfReportService.generatesaledetailPdfReport(purid, response);
			System.out.println("pdf made");

		} catch (IOException e) {
			e.printStackTrace();

		}

		return "redirect:/user/viewpurchasedetail/{purid}";
	}
	

}
