package com.marketplace.dao;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.marketplace.pojos.Category;
import com.marketplace.pojos.Farmer;
import com.marketplace.pojos.OrderDetails;
import com.marketplace.pojos.StockDetails;
import com.marketplace.pojos.User;

public interface IAdminDao {
	
	public boolean AddFarmer(Farmer farmer);
	public boolean AddProduct(int farmerid, StockDetails product);
	public boolean RemoveFarmer(int farmerid);
	public boolean RemoveProduct(int productid);
	public boolean UpdateFarmer(Farmer farmer);
	public boolean UpdateProduct(StockDetails product);
	public StockDetails GetProductDetails(int productid);
	public Farmer GetFarmerDetails(int farmerid);
	public Category GetCategory(int catid);
	public boolean SetCategory(String category);
	public boolean RemoveCategory(int catid);
	String saveImage(int productId, MultipartFile imgFile) throws IOException;
	byte[] restoreImage(int empId) throws IOException;
	public List<Category> getAllCategory();
	public List<OrderDetails> getAllOrders();
	public List<User> getAllUser();
	boolean UpdateUser(User user);
}
