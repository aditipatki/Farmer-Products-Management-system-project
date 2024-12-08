package com.marketplace.dao;

import java.util.List;
import com.marketplace.pojos.Farmer;
import com.marketplace.pojos.StockDetails;

public interface IFarmersDao {
	
	List<StockDetails> getAllProduct();
	List<Farmer> getAllFarmers();
	Farmer getFarmerDetails(int id);
	List<StockDetails> getFarmerStock(int farmerid);
	StockDetails getProductDetails(int farmerid, int productid);

}
