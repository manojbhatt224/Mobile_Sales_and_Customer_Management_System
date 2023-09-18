package com.manoj.mobilesalesandcustomer.model;

public class Cart {
	 private String pname;
	 private int pquantity;
	 private double pprice;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public int getPquantity() {
		return pquantity;
	}
	public void setPquantity(int pquantity) {
		this.pquantity = pquantity;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	
}
