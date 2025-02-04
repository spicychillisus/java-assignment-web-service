package com.example.java_assignment_web_service.controller;

import java.sql.Timestamp;
import java.util.*;
import com.example.java_assignment_web_service.db.Discount;
import com.example.java_assignment_web_service.db.DiscountModel;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("discounts")


public class DiscountController {
	
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
	public ArrayList<Discount> getAllDiscounts() {
		ArrayList<Discount> discounts = new ArrayList<>();
		try {
			discounts = model.getAllDiscounts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discounts;
	}
	
	@RequestMapping(path="/createDiscount", consumes="application/json", method=RequestMethod.POST)
	public int insertDiscount(
			String code, 
			Double discount_value, String description, 
			Timestamp start_date, Timestamp end_date, 
			Timestamp created_at
			) {
		int rc = 0;
		try {
			
		} catch (Exception e) {
			
		}
		return 0;
	}
}
