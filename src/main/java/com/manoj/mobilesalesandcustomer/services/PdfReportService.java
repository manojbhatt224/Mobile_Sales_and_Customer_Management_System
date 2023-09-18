package com.manoj.mobilesalesandcustomer.services;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Product;
import com.manoj.mobilesalesandcustomer.model.Purchase;
import com.manoj.mobilesalesandcustomer.model.Purchaselog;
import com.manoj.mobilesalesandcustomer.model.Sale;
import com.manoj.mobilesalesandcustomer.model.Salelog;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class PdfReportService {
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
	  public static Date convertToDate(LocalDate localDate) {
	        Instant instant = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
	        return Date.from(instant);
	    }
    public void generatepurchasePdfReport(String d1, String d2,HttpServletResponse response) throws IOException {
    	System.out.println("Service Section");
    	System.out.println(d1);
		  LocalDate parsedDate1 = LocalDate.parse(d1);
		  System.out.println(parsedDate1);
	        LocalDate parsedDate2 = LocalDate.parse(d2);
	        Date date1=convertToDate(parsedDate1);
	        Date date2=convertToDate(parsedDate2);
	        System.out.println(date1);
    	List<Purchase> purs=ps.listPurchasewithdate(date1,date2);
    	 SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
    	System.out.println("Hello I am here");
    	 PDDocument document = new PDDocument();
    	PDRectangle myPageSize= new PDRectangle(600,900);
    	// Add a new page to the document
    	PDPage myPage=new PDPage(myPageSize);
    	        document.addPage(myPage);
    	        
    	        int pageWidth=(int) myPage.getTrimBox().getWidth();
    	        int pageHeight=(int) myPage.getTrimBox().getHeight();
    	        
    	        // Create a new content stream to write on the page
    	        PDPageContentStream contentStream = new PDPageContentStream(document, myPage);

    	        LocalDate currentDate = LocalDate.now();
    	        System.out.println(currentDate);
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float dateWidth = PDType1Font.TIMES_ROMAN.getStringWidth(currentDate.toString()) / 1000 * 12; // Assuming font size 12
    	        float dateX = pageWidth - 100 - dateWidth; // 100 is the right margin, adjust as needed
    	        contentStream.newLineAtOffset(dateX, pageHeight-60);
    	        contentStream.showText("Print Date: " + currentDate);
    	        contentStream.endText();
    	        
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float dnameWidth = PDType1Font.TIMES_ROMAN.getStringWidth("City Mobile And Care Center") / 1000 * 12; // Assuming font size 12
    	        contentStream.newLineAtOffset((pageWidth-dnameWidth)/2, pageHeight-90);
    	        contentStream.showText("CITY MOBILE AND CARE CENTER");
    	        contentStream.endText();
    	       
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float daddWidth = PDType1Font.TIMES_ROMAN.getStringWidth("Mahendranagar,Kanchanpur") / 1000 * 12; // Assuming font size 12
    	        contentStream.newLineAtOffset((pageWidth-daddWidth+20)/2, pageHeight-110);
    	        contentStream.showText("Mahendranagar,Kanchanpur");
    	        contentStream.endText();

    	        int i=0;
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        contentStream.newLineAtOffset(60, pageHeight-130);
    	        String header = String.format("%-15s %-30s %-35s %-50s %-60s", "SN", "Dealer", "Date", "Net Bill", "Vat");
    	        contentStream.showText(header);
    	        contentStream.endText();
double totalPurchase=0.0;
double totalVat=0.0;
        // Add manufacturer data to the table
        for (Purchase pur : purs) {
        	  contentStream.beginText();
  	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 9);
  	        contentStream.newLineAtOffset(60, pageHeight-130-20*(i+1));
  	      String pdate = sdfNew.format(pur.getDate());
            String purData = String.format("%-15s %-30s %-40s %-65s %-70s ",
                    i+1, pur.getDealer().getDname(), pdate,pur.getVatedbill(),pur.getVat());
            contentStream.showText(purData);
            totalVat=totalVat+pur.getVat();
            totalPurchase=totalPurchase+pur.getVatedbill();
            contentStream.endText();
            i=i+1;
        }
       
     int next=130+20*(i+1);
     contentStream.beginText();
     contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
     contentStream.newLineAtOffset(433, pageHeight-next-20);
     contentStream.showText("Total Vat:"+totalVat);
     contentStream.endText();
     contentStream.beginText();
     contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
     contentStream.newLineAtOffset(459, pageHeight-next-40);
     contentStream.showText("Total Purchase :"+totalPurchase);
     contentStream.endText();
       
       

        // Close the content stream
        contentStream.close();
        String fileName = "purchase_report_" + currentDate + ".pdf";
        // Set the response headers to make the PDF downloadable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        // Get the response OutputStream and save the document
        OutputStream outputStream = response.getOutputStream();
        document.save(outputStream);
        document.close();

        // Flush and close the response OutputStream
        outputStream.flush();
        outputStream.close();
    }
    
    
    
    
    public void generatepurchasedetailPdfReport(Integer purid,HttpServletResponse response) throws IOException {
    	Double totalBill=(double) 0;
		Product prostock=new Product();
		Purchase pur=this.ps.findPurchaseById(purid);
	 	List<Purchaselog> purloglist=pls.listAllPurchaseLog(purid);
	 	Iterator<Purchaselog> iterator = purloglist.iterator();
	    while (iterator.hasNext()) {
	        Purchaselog c = iterator.next();
	     totalBill += c.getQuantity()*c.getPrice();
	    }
	  
	    double vat=0.13 * totalBill;
		   double vatedbill=totalBill+vat;
		   vat = Math.round(vat * 100.0) / 100.0;
	        vatedbill = Math.round(vatedbill * 100.0) / 100.0;
//		   SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
//	        String formattedDate = sdfNew.format(pur.getDate());
		  
	       
    	PDDocument document = new PDDocument();
PDRectangle myPageSize= new PDRectangle(600,900);
// Add a new page to the document
PDPage myPage=new PDPage(myPageSize);
        document.addPage(myPage);
        
        int pageWidth=(int) myPage.getTrimBox().getWidth();
        int pageHeight=(int) myPage.getTrimBox().getHeight();
        
        // Create a new content stream to write on the page
        PDPageContentStream contentStream = new PDPageContentStream(document, myPage);

        // Define the content to be written on the page (you can customize this as needed)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String purDate = dateFormat.format(pur.getDate());
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float dateWidth = PDType1Font.TIMES_ROMAN.getStringWidth(purDate) / 1000 * 12; // Assuming font size 12
        float dateX = pageWidth - 100 - dateWidth; // 100 is the right margin, adjust as needed
        contentStream.newLineAtOffset(dateX, pageHeight-60);
        contentStream.showText("Purchase Date: " + purDate);
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float dnameWidth = PDType1Font.TIMES_ROMAN.getStringWidth(pur.getDealer().getDname()) / 1000 * 12; // Assuming font size 12
        contentStream.newLineAtOffset((pageWidth-dnameWidth)/2, pageHeight-90);
        contentStream.showText(pur.getDealer().getDname());
        contentStream.endText();
       
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float daddWidth = PDType1Font.TIMES_ROMAN.getStringWidth(pur.getDealer().getDname()) / 1000 * 12; // Assuming font size 12
        contentStream.newLineAtOffset((pageWidth-daddWidth+20)/2, pageHeight-110);
        contentStream.showText(pur.getDealer().getDaddress());
        contentStream.endText();

        int i=0;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(60, pageHeight-140);
        String header = String.format("%-5s %-30s %-40s %-50s %-60s", "SN", "Particulars", "Quantity", "Price", "Total");
        contentStream.showText(header);
        contentStream.endText();
        for (Purchaselog purl : purloglist) {
        	contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            String purData = String.format("%-5s %-26s %-35s %-45s %-55s ",
                    i+1, purl.getParticulars(), purl.getQuantity(),purl.getPrice(),Math.round((purl.getPrice()*purl.getQuantity())*100.0)/100.0);
           
            contentStream.newLineAtOffset(60, pageHeight-140-20*(i+1));
            contentStream.showText(purData);
            i=i+1;
            contentStream.endText();
        }
        int next=140+20*(i+1);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(433, pageHeight-next-10);
        contentStream.showText("Gross Total:"+totalBill);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(459, pageHeight-next-30);
        contentStream.showText("VAT :"+vat);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(439, pageHeight-next-40);
        contentStream.showText("Net Total :"+vatedbill);
        contentStream.endText();
        

        contentStream.close();
        String fileName = "pd"+pur.getDealer().getDname() +"_"+ purDate + ".pdf";
        // Set the response headers to make the PDF downloadable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        // Get the response OutputStream and save the document
        OutputStream outputStream = response.getOutputStream();
        document.save(outputStream);
        document.close();

        // Flush and close the response OutputStream
        outputStream.flush();
        outputStream.close();
    }
    public void generatesalePdfReport(String d1, String d2,HttpServletResponse response) throws IOException {
		  LocalDate parsedDate1 = LocalDate.parse(d1);
		      LocalDate parsedDate2 = LocalDate.parse(d2);
	        Date date1=convertToDate(parsedDate1);
	        Date date2=convertToDate(parsedDate2);
    	List<Sale> purs=ss.listSalewithdate(date1,date2);
    	 SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
    	 PDDocument document = new PDDocument();
    	PDRectangle myPageSize= new PDRectangle(600,900);
    	// Add a new page to the document
    	PDPage myPage=new PDPage(myPageSize);
    	        document.addPage(myPage);
    	        
    	        int pageWidth=(int) myPage.getTrimBox().getWidth();
    	        int pageHeight=(int) myPage.getTrimBox().getHeight();
    	        
    	        // Create a new content stream to write on the page
    	        PDPageContentStream contentStream = new PDPageContentStream(document, myPage);

    	        LocalDate currentDate = LocalDate.now();
    	     
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float dateWidth = PDType1Font.TIMES_ROMAN.getStringWidth(currentDate.toString()) / 1000 * 12; // Assuming font size 12
    	        float dateX = pageWidth - 100 - dateWidth; // 100 is the right margin, adjust as needed
    	        contentStream.newLineAtOffset(dateX, pageHeight-60);
    	        contentStream.showText("Date created: " + currentDate);
    	        contentStream.endText();
    	        
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float dnameWidth = PDType1Font.TIMES_ROMAN.getStringWidth("City Mobile And Care Center") / 1000 * 12; // Assuming font size 12
    	        contentStream.newLineAtOffset((pageWidth-dnameWidth)/2, pageHeight-90);
    	        contentStream.showText("CITY MOBILE AND CARE CENTER");
    	        contentStream.endText();
    	       
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        float daddWidth = PDType1Font.TIMES_ROMAN.getStringWidth("Mahendranagar,Kanchanpur") / 1000 * 12; // Assuming font size 12
    	        contentStream.newLineAtOffset((pageWidth-daddWidth+20)/2, pageHeight-110);
    	        contentStream.showText("Mahendranagar,Kanchanpur");
    	        contentStream.endText();

    	        int i=0;
    	        contentStream.beginText();
    	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
    	        contentStream.newLineAtOffset(60, pageHeight-130);
    	        String header = String.format("%-15s %-30s %-35s %-50s %-60s", "SN", "Customer", "Date", "Net Bill", "Vat");
    	        contentStream.showText(header);
    	        contentStream.endText();
double totalPurchase=0.0;
double totalVat=0.0;
        // Add manufacturer data to the table
        for (Sale pur : purs) {
        	  contentStream.beginText();
  	        contentStream.setFont(PDType1Font.TIMES_ROMAN, 11);
  	        contentStream.newLineAtOffset(60, pageHeight-130-20*(i+1));
  	      String pdate = sdfNew.format(pur.getDate());
            String purData = String.format("%-10s %-20s %-30s %-40s %-50s",
                    i+1, pur.getCustomer().getName(), pdate,pur.getVatedbill(),pur.getVat());
            contentStream.showText(purData);
            totalVat=totalVat+pur.getVat();
            totalPurchase=totalPurchase+pur.getVatedbill();
            contentStream.endText();
            i=i+1;
        }
       
     int next=130+20*(i+1);
     contentStream.beginText();
     contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
     contentStream.newLineAtOffset(433, pageHeight-next-20);
     contentStream.showText("Total Vat:"+totalVat);
     contentStream.endText();
     contentStream.beginText();
     contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
     contentStream.newLineAtOffset(459, pageHeight-next-40);
     contentStream.showText("Total Sales :"+totalPurchase);
     contentStream.endText();
       
       

        // Close the content stream
        contentStream.close();
        String fileName = "sale_report_" + currentDate + ".pdf";
        // Set the response headers to make the PDF downloadable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        // Get the response OutputStream and save the document
        OutputStream outputStream = response.getOutputStream();
        document.save(outputStream);
        document.close();

        // Flush and close the response OutputStream
        outputStream.flush();
        outputStream.close();
    }
    public void generatesaledetailPdfReport(Integer purid,HttpServletResponse response) throws IOException {
    	Double totalBill=(double) 0;
		Sale prostock=new Sale();
		Sale pur=this.ss.findSaleById(purid);
	 	List<Salelog> purloglist=sls.listAllSaleLog(purid);
	 	Iterator<Salelog> iterator = purloglist.iterator();
	    while (iterator.hasNext()) {
	        Salelog c = iterator.next();
	     totalBill += c.getQuantity()*c.getPrice();
	    }
	  
	    double vat=0.13 * totalBill;
		   double vatedbill=totalBill+vat;
		   vat = Math.round(vat * 100.0) / 100.0;
	        vatedbill = Math.round(vatedbill * 100.0) / 100.0;
//		   SimpleDateFormat sdfNew = new SimpleDateFormat("yyyy-MM-dd");
//	        String formattedDate = sdfNew.format(pur.getDate());
		  
	       
    	PDDocument document = new PDDocument();
PDRectangle myPageSize= new PDRectangle(600,900);
// Add a new page to the document
PDPage myPage=new PDPage(myPageSize);
        document.addPage(myPage);
        
        int pageWidth=(int) myPage.getTrimBox().getWidth();
        int pageHeight=(int) myPage.getTrimBox().getHeight();
        
        // Create a new content stream to write on the page
        PDPageContentStream contentStream = new PDPageContentStream(document, myPage);

        // Define the content to be written on the page (you can customize this as needed)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String purDate = dateFormat.format(pur.getDate());
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float dateWidth = PDType1Font.TIMES_ROMAN.getStringWidth(purDate) / 1000 * 12; // Assuming font size 12
        float dateX = pageWidth - 100 - dateWidth; // 100 is the right margin, adjust as needed
        contentStream.newLineAtOffset(dateX, pageHeight-60);
        contentStream.showText("Sale Date: " + purDate);
        contentStream.endText();
        
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float dnameWidth = PDType1Font.TIMES_ROMAN.getStringWidth(pur.getCustomer().getName()) / 1000 * 12; // Assuming font size 12
        contentStream.newLineAtOffset((pageWidth-dnameWidth)/2, pageHeight-90);
        contentStream.showText(pur.getCustomer().getName());
        contentStream.endText();
       
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        float daddWidth = PDType1Font.TIMES_ROMAN.getStringWidth(pur.getCustomer().getPhone()) / 1000 * 12; // Assuming font size 12
        contentStream.newLineAtOffset((pageWidth-daddWidth+20)/2, pageHeight-110);
        contentStream.showText(pur.getCustomer().getPhone());
        contentStream.endText();

        int i=0;
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(60, pageHeight-140);
        String header = String.format("%-5s %-30s %-40s %-50s %-60s", "SN", "Particulars", "Quantity", "Price", "Total");
        contentStream.showText(header);
        contentStream.endText();
        for (Salelog purl : purloglist) {
        	contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            String purData = String.format("%-5s %-26s %-35s %-45s %-55s ",
                    i+1, purl.getParticulars(), purl.getQuantity(),purl.getPrice(),Math.round((purl.getPrice()*purl.getQuantity())*100.0)/100.0);
           
            contentStream.newLineAtOffset(60, pageHeight-140-20*(i+1));
            contentStream.showText(purData);
            i=i+1;
            contentStream.endText();
        }
        int next=140+20*(i+1);
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(433, pageHeight-next-10);
        contentStream.showText("Gross Total:"+totalBill);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(459, pageHeight-next-30);
        contentStream.showText("VAT :"+vat);
        contentStream.endText();
        contentStream.beginText();
        contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
        contentStream.newLineAtOffset(439, pageHeight-next-40);
        contentStream.showText("Net Total :"+vatedbill);
        contentStream.endText();
        

        contentStream.close();
        String fileName = "sd"+pur.getCustomer().getName()+"_"+ purDate + ".pdf";
        // Set the response headers to make the PDF downloadable
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename="+fileName);

        // Get the response OutputStream and save the document
        OutputStream outputStream = response.getOutputStream();
        document.save(outputStream);
        document.close();

        // Flush and close the response OutputStream
        outputStream.flush();
        outputStream.close();
    }


}

