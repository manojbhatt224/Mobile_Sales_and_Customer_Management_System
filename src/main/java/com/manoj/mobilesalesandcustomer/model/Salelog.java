package com.manoj.mobilesalesandcustomer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="salelog")
public class Salelog {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int slid;
	private String particulars;
	private int quantity;
	private double price;
	@ManyToOne
	private Sale sale;
	public Salelog() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Salelog(String particulars, int quantity, double price) {
		super();
		this.particulars = particulars;
		this.quantity = quantity;
		this.price = price;
	}

	public int getSlid() {
		return slid;
	}
	public void setSlid(int slid) {
		this.slid = slid;
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
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	
}
