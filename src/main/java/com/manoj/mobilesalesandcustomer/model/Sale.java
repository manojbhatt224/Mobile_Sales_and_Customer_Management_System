package com.manoj.mobilesalesandcustomer.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="sale")
public class Sale {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int salid;
	@Column(nullable=false)
	private Date date;
	private double vatedbill;
	private double vat;
	@OneToMany(mappedBy = "sale", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Salelog> salelogs;
	@ManyToOne
	private Customer customer;
	public Sale() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getSalid() {
		return salid;
	}
	public void setSalid(int salid) {
		this.salid = salid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getVatedbill() {
		return vatedbill;
	}
	public void setVatedbill(double vatedbill) {
		this.vatedbill = vatedbill;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public List<Salelog> getSalelogs() {
		return salelogs;
	}
	public void setSalelogs(List<Salelog> salelogs) {
		this.salelogs = salelogs;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
