package com.marketplace.service;

import java.util.List;
import com.marketplace.pojos.Farmer;
import com.marketplace.pojos.StockDetails;

public interface IFarmersService {
	
	List<Farmer> getFarmersList();
	Farmer getFarmerDetails(int id);
	List<StockDetails> getFarmerStock(int farmerid);
	StockDetails getProductDetails(int farmerid, int productid);
	List<StockDetails> getAllProduct();
	
}
