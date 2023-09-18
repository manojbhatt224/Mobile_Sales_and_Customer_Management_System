package com.manoj.mobilesalesandcustomer.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="manufacturer")
public class Manufacturer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int mid;
	@Column(unique=true, nullable=false)
	private String mname;
	@OneToMany(mappedBy = "manufacturer")
	private List<Dealer> dealers;
	public Manufacturer() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public String getMname() {
		return mname;
	}
	public void setMname(String mname) {
		this.mname = mname;
	}
	public void capsManu() {
		mname=mname.toUpperCase();
	}
	

}
