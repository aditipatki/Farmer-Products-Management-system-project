package com.marketplace.service;

import java.util.List;

import com.marketplace.pojos.Cart;
import com.marketplace.pojos.CartItem;
import com.marketplace.pojos.OrderDetails;
import com.marketplace.pojos.User;

public interface IUserService {
	
	public User Authenticate(String email, String password);
	public boolean Register(User user);
	public CartItem AddToCart(int productid, int qty);
	public boolean PlaceOrder(Cart cart, User user);
	public User getUserDetails(int userId);
	public List<OrderDetails> getOrder(int userId);
	
}
