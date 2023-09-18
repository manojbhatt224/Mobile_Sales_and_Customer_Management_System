package com.manoj.mobilesalesandcustomer.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.manoj.mobilesalesandcustomer.model.Purchaselog;

public interface PurchaselogRepository extends JpaRepository<Purchaselog, Integer>{
List<Purchaselog> findByPurchasePurid(Integer purid);
}
