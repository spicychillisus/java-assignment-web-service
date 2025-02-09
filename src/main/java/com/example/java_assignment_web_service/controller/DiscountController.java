package com.example.java_assignment_web_service.controller;

import java.sql.*;

import java.util.*;
import com.example.java_assignment_web_service.db.Discount;
import com.example.java_assignment_web_service.db.DiscountDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("discounts")


public class DiscountController {
	
	private DiscountDAO model = new DiscountDAO(); // so i dont have to keep on declaring this in the methods
	
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
	public ResponseEntity<String> createDiscount(@RequestBody Discount discount) {
		int rc = 0;
		int discountCheck = 0;
		String code = discount.getCode();
		Double discount_value = discount.getDiscountValue();
		String description = discount.getDescription();
		Timestamp startDate = discount.getStartDate();
		Timestamp endDate = discount.getEndDate();
		try {
			discountCheck = model.checkIfDiscountCodeExists(code);
			if (discountCheck == 0) {
				rc = model.createDiscount(code, discount_value, description, startDate, endDate);
				return ResponseEntity.status(HttpStatus.CREATED).body("Discount created successfully.");
			} else {
				rc = 0;
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create discount.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
		}

	}
	
	@RequestMapping(method = RequestMethod.PUT, path = "/updateDiscount/{discountId}")
	public int updateDiscount(@PathVariable int discountId, @RequestBody Discount discount) {
	    int rowsUpdated = 0;
	    try {
	        // Call the DAO to update the discount with the provided details
	        rowsUpdated = model.updateDiscount(discountId, discount.getCode(), 
	                                           discount.getDiscountValue(), discount.getDescription(), 
	                                           discount.getStartDate(), discount.getEndDate());
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        
	    }

	    return rowsUpdated;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/deleteDiscount/{discountCode}")
	public int deleteDiscount(@PathVariable String discountCode) {
	    int deletedDisc = 0;
	    try {
	        // Call the DAO to delete the discount by its code
	        deletedDisc = model.deleteDiscount(discountCode);
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return deletedDisc;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/checkDiscountExists")
	public Map<String, Object> checkDiscountCodeExists(@RequestBody Map<String, Object> requestBody) {
	    Map<String, Object> response = new HashMap<>();
	    String discountCodeInput = (String) requestBody.get("discountCode");
	    
	    try {
	        // Call the modified checkDiscountCodeExists method
	        Map<String, Integer> result = model.checkDiscountCodeExists(discountCodeInput);
	        
	        int discountExist = result.get("codeExist");
	        int discountId = result.get("discountId");
	        
	        // Print the discountExist and discountId to verify
	        System.out.println("Discount Exists: " + discountExist);
	        System.out.println("Discount ID: " + discountId);
	        
	        if (discountExist > 0) {
	            // If the discount exists, return discount information
	            response.put("discountCode", discountCodeInput);
	            response.put("discountId", discountId);
	            response.put("discountValue", model.getDiscountValue(discountCodeInput));  // Get the discount value
	            response.put("valid", true);  // Indicates that the discount code is valid
	        } else {
	            // If the discount doesn't exist, return null values
	            response.put("discountCode", null);
	            response.put("discountId", null);
	            response.put("discountValue", null);
	            response.put("valid", false);  // Indicates that the discount code is invalid
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.put("error", "An error occurred while checking the discount code.");
	    }
	    return response;
	}

	
	
	
	

	
	
}
