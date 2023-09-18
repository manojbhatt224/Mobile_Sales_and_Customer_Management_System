package com.manoj.mobilesalesandcustomer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="purchaselog")
public class Purchaselog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int plid;
	private String particulars;
	private int quantity;
	private double price;
	@ManyToOne
	private Purchase purchase;
	
	public Purchaselog(String particulars, int quantity, double price) {
		super();
		this.particulars = particulars;
		this.quantity = quantity;
		this.price = price;
	}
	public Purchaselog() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPlid() {
		return plid;
	}
	public void setPlid(int plid) {
		this.plid = plid;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Purchase getPurchase() {
		return purchase;
	}
	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
}
