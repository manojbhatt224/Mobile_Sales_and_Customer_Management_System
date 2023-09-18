package com.manoj.mobilesalesandcustomer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.mobilesalesandcustomer.model.Dealer;
import com.manoj.mobilesalesandcustomer.repository.DealerRepository;
@Service
public class DealerServiceImpl implements DealerService {
@Autowired
DealerRepository dealRepo;
	@Override
	public void insertDealer(Dealer deal) {
		deal.capsDealer();
		this.dealRepo.save(deal);
	}

	@Override
	public void deleteDealer(Integer did) {
		this.dealRepo.deleteById(did);

	}

	@Override
	public Dealer searchDealerById(Integer id) {
		Optional<Dealer> optional =dealRepo.findById(id);
		Dealer deal=null;
		if(optional.isPresent()) {
			deal=optional.get();
		}
		else
		{
			throw new RuntimeException("Manufacturer not found for id:"+id);
		}
		return deal;
	}

	@Override
	public Page<Dealer> searchPageDealer(Pageable page) {
		return this.dealRepo.findAll(page);
	}

	@Override
	public Page<Dealer> searchbykeywordDealer(String keyword, Pageable page) {
		return this.dealRepo.findByDnameContainingOrDaddressContainingOrDemailContainingOrDcontactContaining(keyword, keyword, keyword, keyword, page);
	}

	@Override
	public List<Dealer> searchListDealer() {

		return this.dealRepo.findAll();
	}

	@Override
	public List<Dealer> searchListByManufacturerId(Integer mid) {
		
		return this.dealRepo.findByManufacturerMid(mid);
	}

	@Override
	public Dealer searchDealerByProductId(Integer pid) {
		// TODO Auto-generated method stub
		return this.dealRepo.findByProductsPid(pid);
	}

}
