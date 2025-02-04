package com.example.java_assignment_web_service.db;

import java.util.*;
import java.sql.*;
public class DiscountModel {
	
	private Config neon = new Config();
	private String url = neon.getConnectionUrl();
	private String username = neon.getUser();
	private String dbPassword = neon.getPassword();
	
	
	public ArrayList<Discount> getAllDiscounts() throws SQLException {
		
		Connection conn = null;
		String sql = "";
		Discount discount = null;
		
		ArrayList<Discount> discounts = new ArrayList<Discount>();
		
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(username);
			conn = DriverManager.getConnection(url, username, dbPassword);
			sql = "SELECT * FROM discounts";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				discount = new Discount();
				discount.setId(rs.getInt("id"));
				discount.setCode(rs.getString("code"));
				discount.setDiscountValue(rs.getDouble("discount_value"));
				discount.setDescription(rs.getString("description"));
				discount.setStartDate(rs.getTimestamp("start_date")	);
				discount.setEndDate(rs.getTimestamp("end_date"));
				discount.setCreatedAt(rs.getTimestamp("created_at"));
				discounts.add(discount);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return discounts;
	}
	
	public Discount getDiscountCode(String code) {
		Discount discount = null;
		Connection conn = null;
		String sql = "";
		
		try {
			sql = "SELECT * FROM discounts WHERE code = ?";
			conn = DriverManager.getConnection(sql);
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(2, "code");
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				discount = new Discount();
				discount.setId(rs.getInt("id"));
				discount.setCode(rs.getString("code"));
				discount.setDiscountValue(rs.getDouble("discount_value"));
				discount.setDescription(rs.getString("description"));
				discount.setStartDate(rs.getTimestamp("start_date"));
				discount.setEndDate(rs.getTimestamp("end_date"));
				discount.setCreatedAt(rs.getTimestamp("created_at"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return discount;
	}
	
	public int insertDiscount(
			String code, 
			Double discount_value, String description, 
			Timestamp start_date, Timestamp end_date, 
			Timestamp created_at
			) throws SQLException, ClassNotFoundException {
		Connection conn = null;
		String sql = "";
		int rc = 0;
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, dbPassword);
			sql = "INSERT INTO discounts (code, discount_value, description, start_date, end_date, created_at)"
					+ "VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ps.setDouble(2, discount_value);
			ps.setString(3, description);
		} catch (Exception e) {
			
		}
		return rc;
	}
	
	public int addNewDiscountToUser(int discountId, int userId)
	        throws SQLException, ClassNotFoundException {
	    int rc = 0;
	    Connection conn = null;
	    PreparedStatement ps = null;
	    ResultSet rs = null;
	    
	    try {
	        // Obtain a database connection (adjust this to match your connection logic)
	    	conn = DriverManager.getConnection(url, username, dbPassword);

	        // 1. Check if the user exists in the users table
	        String checkUserSQL = "SELECT COUNT(id) AS count FROM users WHERE id = ?";
	        ps = conn.prepareStatement(checkUserSQL);
	        ps.setInt(1, userId);
	        rs = ps.executeQuery();
	        
	        // If the user exists, proceed to add the discount
	        if (rs.next() && rs.getInt("count") > 0) {
	            // Clean up previous statement and result set resources
	            rs.close();
	            ps.close();

	            // 2. Insert the discount into the discount_owner table
	            String insertSQL = "INSERT INTO discount_owner (discount_id, user_id) VALUES (?, ?)";
	            ps = conn.prepareStatement(insertSQL);
	            ps.setInt(1, discountId);
	            ps.setInt(2, userId);
	            
	            // Execute the insert and get the number of affected rows
	            rc = ps.executeUpdate();
	        } else {
	            // Optionally handle the case where the user does not exist.
	            System.out.println("User with id " + userId + " does not exist.");
	        }
	    } catch (SQLException e) {
	        // Log the exception and rethrow it if necessary
	        e.printStackTrace();
	        throw e;
	    } finally {
	        // Always clean up resources in a finally block.
	        if (rs != null) {
	            try {
	                rs.close();
	            } catch (SQLException ignore) {}
	        }
	        if (ps != null) {
	            try {
	                ps.close();
	            } catch (SQLException ignore) {}
	        }
	        if (conn != null) {
	            try {
	                conn.close();
	            } catch (SQLException ignore) {}
	        }
	    }
	    return rc;
	}

}
