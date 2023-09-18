package com.manoj.mobilesalesandcustomer.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;

public interface ManufacturerService {
void insertManufacturer(Manufacturer manu);
void deleteManufacturer(Integer manuid);
Manufacturer searchManufacturerById(Integer id);
List<Manufacturer> searchListManufacturer();
Page<Manufacturer> searchPageManufacturer(Pageable page);
Page<Manufacturer> searchbykeywordManufacturer(String keyword, Pageable page);
Manufacturer getManufacturerByDealerId(Integer did);
Manufacturer getManufacturerByProductName(String pname);
}
