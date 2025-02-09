package com.example.java_assignment_web_service.db;

import java.util.*;
import java.sql.*;
public class DiscountDAO {
	
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
	
	public String getDiscountCodeById(int discountid) throws SQLException {
		String discountCode = "";
		String sql = "";
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		try {
			sql = "SELECT code FROM discounts WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, discountid);
			ResultSet rs = ps.executeQuery();
			
			
			if (rs.next()) {
				discountCode = rs.getString("code");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discountCode;
	}
	
	public int createDiscount(String code, Double discountValue, String description, Timestamp startDate, Timestamp endDate) throws SQLException, ClassNotFoundException {
		int rc = 0;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		try {
			String sql = "INSERT INTO discounts"
					+ "(code, discount_value, description, start_date, end_date)"
					+ "VALUES (?, ?, ?, ?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, code);
			ps.setDouble(2, discountValue);
			ps.setString(3, description);
			ps.setTimestamp(4, startDate);
			ps.setTimestamp(5, endDate);
			rc = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	public Map<String, Integer> checkDiscountCodeExists(String discountCode) throws SQLException, ClassNotFoundException {
	    Map<String, Integer> result = new HashMap<>();
	    Connection conn = DriverManager.getConnection(url, username, dbPassword);
	    String sql = "";
	    
	    try {
	        sql = "SELECT COUNT(*), id FROM discounts WHERE code = ? GROUP BY code, id, start_date, end_date";
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, discountCode);
	        ResultSet rs = ps.executeQuery();
	        
	        if (rs.next()) {
	            // Returns both the count of the code and the discount ID
	            result.put("codeExist", rs.getInt("count"));
	            result.put("discountId", rs.getInt("id"));
	        } else {
	            // If no record found, return 0 for both
	            result.put("codeExist", 0);
	            result.put("discountId", 0);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return result;
	}
	
	public int checkIfDiscountCodeExists(String discountCode) throws SQLException, ClassNotFoundException {
		int codeExist = 0;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		String sql = "";
		try {
			sql = "SELECT COUNT(code) FROM discounts WHERE code = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, discountCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getInt("count") > 0) {
				codeExist = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codeExist;
	}
	
	public Double getDiscountValue(String discountCode) throws SQLException, ClassNotFoundException {
		Double discountValue = 0.0;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		String sql = "";
		try {
			sql = "SELECT discount_value FROM discounts WHERE code = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, discountCode);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
                discountValue = rs.getDouble("discount_value");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discountValue;
	}
	
	public int updateDiscount(int discountId, String code, Double discountValue, String description, Timestamp startDate, Timestamp endDate) throws SQLException, ClassNotFoundException {
	    int rowsUpdated = 0;
	    String sql = "UPDATE discounts SET code = ?, "
	    		+ "discount_value = ?, description = ?, start_date = ?, end_date = ? "
	    		+ "WHERE id = ?";
	    
	    try (Connection conn = DriverManager.getConnection(url, username, dbPassword)) {
	        PreparedStatement ps = conn.prepareStatement(sql);
	        ps.setString(1, code);
	        ps.setDouble(2, discountValue);
	        ps.setString(3, description);
	        ps.setTimestamp(4, startDate);
	        ps.setTimestamp(5, endDate);
	        ps.setInt(6, discountId);
	        
	        rowsUpdated = ps.executeUpdate();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    return rowsUpdated;
	}
	
	public int deleteDiscount(String discountCode) throws SQLException, ClassNotFoundException {
		int deletedDisc = 0;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		String sql = "";
		try {
			sql = "DELETE FROM discounts WHERE code = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, discountCode);
			deletedDisc = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deletedDisc;
		
	}
	
	
	public int getIdFromDiscountCode(String discountCode) throws SQLException, ClassNotFoundException {
		String sql = "SELECT id FROM discounts WHERE discount_code = ?";
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		int id = 0;
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public int insertDiscountUsage(int discountid, int userid) throws SQLException, ClassNotFoundException {
		int rc = 0;
		int times_used = 1;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		try {
			String sql = "INSERT INTO discount_usage (discountid, times_used, userid)"
					+ "VALUES (?, ?, ?)"; // user has already used it hence times_used = 1
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, discountid);
			ps.setInt(2, times_used);
			ps.setInt(3, userid);
			rc = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	public boolean checkIfDiscountUsedByUser(int discountid, int userid) throws SQLException, ClassNotFoundException {
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		boolean discountUsed = false;
		String sql = "SELECT COUNT(*) FROM discount_usage WHERE discountId = ? AND userId = ? AND times_used > 0";
		PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, discountid);
        ps.setInt(2, userid);
        ResultSet rs = ps.executeQuery();
        if (rs.next() && rs.getInt(1) > 0) {
            discountUsed = true;
        }
		return discountUsed;
	}
	
	
	
	


}
