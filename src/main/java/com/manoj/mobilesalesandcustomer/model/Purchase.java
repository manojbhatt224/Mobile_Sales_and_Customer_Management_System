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
@Table(name="purchase")
public class Purchase {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int purid;
	@Column(nullable=false)
	private Date date;
	private double vatedbill;
	private double vat;
	@OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Purchaselog> purchaselogs;
	@ManyToOne
	private Dealer dealer;
	public Purchase() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getPurid() {
		return purid;
	}
	public void setPurid(int purid) {
		this.purid = purid;
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
	public List<Purchaselog> getPurchaselogs() {
		return purchaselogs;
	}
	public void setPurchaselogs(List<Purchaselog> purchaselogs) {
		this.purchaselogs = purchaselogs;
	}
	public Dealer getDealer() {
		return dealer;
	}
	public void setDealer(Dealer dealer) {
		this.dealer = dealer;
	}
}
