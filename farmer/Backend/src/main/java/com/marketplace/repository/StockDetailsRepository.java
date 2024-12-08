package com.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.marketplace.pojos.StockDetails;

@Repository
public interface StockDetailsRepository extends JpaRepository<StockDetails, String>{
	public StockDetails findByStockItem(String name);	
}
