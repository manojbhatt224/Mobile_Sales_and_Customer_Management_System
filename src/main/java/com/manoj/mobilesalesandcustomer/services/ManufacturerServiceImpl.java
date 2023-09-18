package com.manoj.mobilesalesandcustomer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Manufacturer;
import com.manoj.mobilesalesandcustomer.repository.ManufacturerRepository;
@Service
public class ManufacturerServiceImpl implements ManufacturerService {
@Autowired
ManufacturerRepository manuRepo;
	@Override
	public void insertManufacturer(Manufacturer manu) {
		manu.capsManu();
		this.manuRepo.save(manu);
	}

	@Override
	public void deleteManufacturer(Integer manuid) {
		this.manuRepo.deleteById(manuid);
	}

	@Override
	public Manufacturer searchManufacturerById(Integer id) {
		Optional<Manufacturer> optional =manuRepo.findById(id);
		Manufacturer manufac=null;
		if(optional.isPresent()) {
			manufac=optional.get();
		}
		else
		{
			throw new RuntimeException("Manufacturer not found for id:"+id);
		}
		return manufac;
	}

	@Override
	public Page<Manufacturer> searchPageManufacturer(Pageable page) {
		return this.manuRepo.findAll(page);
	}

	@Override
	public Page<Manufacturer> searchbykeywordManufacturer(String keyword, Pageable page) {
		
		return this.manuRepo.findByMnameContaining(keyword, page);
	}

	@Override
	public List<Manufacturer> searchListManufacturer() {
		
		return this.manuRepo.findAll();
	}

	@Override
	public Manufacturer getManufacturerByDealerId(Integer did) {
		
		return this.manuRepo.findByDealersDid(did);
	}

	@Override
	public Manufacturer getManufacturerByProductName(String pname) {
	
		return this.manuRepo.findByDealersProductsPname(pname);
	}
	

}
