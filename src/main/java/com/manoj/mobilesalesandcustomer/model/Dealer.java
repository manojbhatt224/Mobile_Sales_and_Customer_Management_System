package com.manoj.mobilesalesandcustomer.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="dealer")
public class Dealer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int did;
	@Column(unique=true)
	private String dname;
	private String daddress;
	@Column(unique=true)
	private String demail;
	@Column(unique=true)
	private String dcontact;
	@ManyToOne
	private Manufacturer manufacturer;
	@OneToMany(mappedBy = "dealer")
	private List<Product> products;
	@OneToMany(mappedBy="dealer")
	private List<Purchase> purchases;
	public List<Purchase> getPurchases() {
		return purchases;
	}
	public void setPurchases(List<Purchase> purchases) {
		this.purchases = purchases;
	}
	public Dealer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getDid() {
		return did;
	}
	public void setDid(int did) {
		this.did = did;
	}
	public String getDname() {
		return dname;
	}
	public void setDname(String dname) {
		this.dname = dname;
	}
	public String getDaddress() {
		return daddress;
	}
	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	public String getDemail() {
		return demail;
	}
	public void setDemail(String demail) {
		this.demail = demail;
	}
	public String getDcontact() {
		return dcontact;
	}
	public void setDcontact(String dcontact) {
		this.dcontact = dcontact;
	}
	public Manufacturer getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public void capsDealer(){
		dname=dname.toUpperCase();
	}
}
