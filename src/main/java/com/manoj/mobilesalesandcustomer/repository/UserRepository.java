package com.manoj.mobilesalesandcustomer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	public Optional<User> findByEmail(String email);
//public User findByEmail(String email);
	public User findByName(String name);
}



