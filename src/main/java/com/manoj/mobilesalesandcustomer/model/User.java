package com.manoj.mobilesalesandcustomer.model;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class User {
@Id
@GeneratedValue(strategy=GenerationType.AUTO)
private int id;
public User(int id, String name, String nameOfRetailer, String addressOfRetailer, String contact, String email,
		String password, String logoimage, String role, boolean enabled) {
	super();
	this.id = id;
	this.name = name;
	this.nameOfRetailer = nameOfRetailer;
	this.addressOfRetailer = addressOfRetailer;
	this.contact = contact;
	this.email = email;
	this.password = password;
	this.logoimage = logoimage;
	this.role = role;
	this.enabled = enabled;
}
public User() {
	super();
	// TODO Auto-generated constructor stub
}
private String name;
private String nameOfRetailer;
private String addressOfRetailer;
@Column(unique=true)
private String contact;
@Column(unique=true)
private String email;
private String password;
private String logoimage;
private String role;
private boolean enabled;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getNameOfRetailer() {
	return nameOfRetailer;
}
public void setNameOfRetailer(String nameOfRetailer) {
	this.nameOfRetailer = nameOfRetailer;
}
public String getAddressOfRetailer() {
	return addressOfRetailer;
}
public void setAddressOfRetailer(String addressOfRetailer) {
	this.addressOfRetailer = addressOfRetailer;
}
public String getContact() {
	return contact;
}
public void setContact(String contact) {
	this.contact = contact;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getLogoimage() {
	return logoimage;
}
public void setLogoimage(String logoimage) {
	this.logoimage = logoimage;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public boolean isEnabled() {
	return enabled;
}
public void setEnabled(boolean enabled) {
	this.enabled = enabled;
}
}
