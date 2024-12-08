package com.marketplace.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;
import com.marketplace.pojos.Authentication;
import com.marketplace.pojos.Cart;
import com.marketplace.pojos.CartItem;
import com.marketplace.pojos.OrderDetails;
import com.marketplace.pojos.User;
import com.marketplace.service.EmailSenderService;
import com.marketplace.service.IUserService;
import com.marketplace.service.PdfExportService;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService u_service;
	
	@Autowired
	private PdfExportService pdfService;

	@Autowired
	private EmailSenderService mailService;

	List<CartItem> items = null;
	Cart mycart = new Cart();
	User user = new User();

	@PostMapping("/register")
	public ResponseEntity<?> RegisterNewUser(@RequestBody User user) {
		u_service.Register(user);
		String subject = "Congratutions " + user.getFirstname();
		String body = "Congratutions  "+ user.getFirstname() + " "+ user.getLastname() + "." + " \n "+ "You have Successfully Registered with FarmerMarketPlace";
		mailService.sendSimpleEmail(user.getEmail(),body,subject);
		return new ResponseEntity<String>("Registration Successful..!!", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> LoginUser(@RequestBody Authentication userID) {
		String email = userID.getEmail();
		String password = userID.getPassword();
		System.out.println(email + "   " + password);
		User u = null;
		try {
			u = u_service.Authenticate(email, password);
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

		user = u;
		String subject = "Login Attempt Alert ";
		String body = "Login Attempt at Farmers Market Place. " + " \n If not done by you. Contact Farmer Market Place at farmersm7@gmail.com";
		
		mailService.sendSimpleEmail(user.getEmail(),body,subject);
		items = new ArrayList<CartItem>();
		return new ResponseEntity<User>(u, HttpStatus.OK);

	}

	@PostMapping("/addtocart/{productid}")
	public ResponseEntity<?> AddToCart(@PathVariable int productid, @RequestParam int qty) {
		System.out.println("in AddToCart");
		CartItem product = u_service.AddToCart(productid, qty);
		items.add(product);
		System.out.println("item added to cart");
		System.out.println(items);
		return new ResponseEntity<List<CartItem>>(items, HttpStatus.OK);
	}

	@GetMapping("/checkout")
	public ResponseEntity<?> CheckOut() {
		System.out.println("checkout");
		double grandtotal = 0.0;

		for (CartItem item : items) {
			grandtotal += item.getAmount();
		}
		mycart.setItems(items);
		mycart.setGrandTotal(grandtotal);
		return new ResponseEntity<List<CartItem>>(items, HttpStatus.OK);
	}

	@PostMapping("/removefromcart/{productid}") // "productid" here is index of list
	public ResponseEntity<?> removeItem(@PathVariable int productid) {
		System.out.println("Removing item");
		items.remove(productid);
		return new ResponseEntity<List<CartItem>>(items, HttpStatus.OK);
	}

	@PostMapping("/placeorder")
	public ResponseEntity<?> PlaceOrder()
			throws DocumentException, MessagingException, MalformedURLException, URISyntaxException, IOException {

		u_service.PlaceOrder(mycart, user);

		pdfService.export(items);

		mailService.sendEmailWithAttachment(user.getEmail(),
				"Please check below attached pdf for details. Have a good day!", "Your order is placed.",
				"receipt.pdf");

		items.clear();
		return new ResponseEntity<List<CartItem>>(items, HttpStatus.OK);
	}

	@PostMapping("/orders") // "productid" here is index of list
	public ResponseEntity<?> Orders(@RequestParam int userId) {
		System.out.println("inside orders" + userId);
		List<OrderDetails> orders = u_service.getOrder(userId);
		return new ResponseEntity<List<OrderDetails>>(orders, HttpStatus.OK);
	}
}
