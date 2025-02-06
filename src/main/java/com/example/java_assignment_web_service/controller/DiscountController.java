package com.example.java_assignment_web_service.controller;

import java.sql.*;
import java.util.*;
import com.example.java_assignment_web_service.db.Discount;
import com.example.java_assignment_web_service.db.DiscountModel;
import com.example.java_assignment_web_service.db.DiscountOwner;
import com.example.java_assignment_web_service.db.UserModel;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("discounts")


public class DiscountController {
	
	private UserModel userModel = new UserModel();
	private DiscountModel model = new DiscountModel(); // so i dont have to keep on declaring this in the methods
	
	@RequestMapping(method=RequestMethod.GET, path="/getDiscount/{discountId}")
	public Discount getDiscountCode(@PathVariable("code") String code) {
		Discount discount = null;
		try {
			discount = model.getDiscountCode(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discount;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/getAllDiscounts")
	public ArrayList<Discount> getAllDiscounts() throws SQLException {
		ArrayList<Discount> discounts = new ArrayList<>();
		try {
			discounts = model.getAllDiscounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discounts;
	}
	
	@RequestMapping(path="/createDiscount", consumes="application/json", method=RequestMethod.POST)
	public int createDiscount(@RequestBody Discount discount) {
		int rc = 0;
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/getDiscountOwner/{discountid}")
	public ArrayList<DiscountOwner> viewDiscountOwners(@PathVariable("discountid") int discountid) {
		ArrayList<DiscountOwner> discountOwners = new ArrayList<DiscountOwner>();
		try {
			discountOwners = model.viewDiscountOwners(discountid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discountOwners;
	}
	
	@RequestMapping(method=RequestMethod.GET, path="/getDiscountUser/{userid}")
	public ArrayList<DiscountOwner> getDiscountsByUser(@PathVariable("userid")int userid) {
		ArrayList<DiscountOwner> userDiscount = new ArrayList<DiscountOwner>();
		try {
			userDiscount = model.getDiscountsByUser(userid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userDiscount;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public int addNewDiscountToUser(int discountId, int userId) {
		int rc = 0;
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	
}
