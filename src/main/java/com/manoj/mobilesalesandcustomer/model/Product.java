package com.manoj.mobilesalesandcustomer.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="product")
public class Product {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pid;
	@Column(unique=true)
	private String pname;
	private String pdor;
	@Column(nullable=true,length=64)
	private String photourl;
	 @Column(columnDefinition = "INT DEFAULT 0")
	    private int stock;
	@ManyToOne
	private Category category;
	@ManyToOne
	private Dealer dealer;
	@Transient
	public String getPhotosImagePath() {
	if(photourl == null) return null;
	
	return "/product-photos/"+pid+"/"+photourl;
}
	@Transient
	public void capsProduct() {
	pname=pname.toUpperCase();
}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public String getPdor() {
		return pdor;
	}
	public void setPdor(String pdor) {
		this.pdor = pdor;
	}
	public String getPhotourl() {
		return photourl;
	}
	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public Dealer getDealer() {
		return dealer;
	}
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public boolean isStockEmpty() {
		if (stock==0)
			return true;
			else return false;
	}
	public boolean isSaleStock(Integer n) {
		if (!(stock==0|| stock<n))
			return true;
			else return false;
	}
}
