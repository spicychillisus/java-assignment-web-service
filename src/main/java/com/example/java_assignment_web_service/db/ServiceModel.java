package com.example.java_assignment_web_service.db;
import java.util.*;
import java.sql.*;
public class ServiceModel {
	
	private Config neon = new Config();
	private String url = neon.getConnectionUrl();
	private String username = neon.getUser();
	private String dbPassword = neon.getPassword();
	
	public ArrayList<Service> allServices(String search) throws SQLException {
	    ArrayList<Service> allServices = new ArrayList<>();
	    Connection conn = null;
	    String sql;
	    Service service;
	    
	    try {
	        Class.forName("org.postgresql.Driver");
	        conn = DriverManager.getConnection(url, username, dbPassword);

	        if (search != null && !search.trim().isEmpty()) {
	            sql = "SELECT * FROM services WHERE servicetitle ILIKE ? OR servicedescription ILIKE ? OR category ILIKE ?";
	        } else {
	            sql = "SELECT * FROM services";
	        }

	        PreparedStatement ps = conn.prepareStatement(sql);

	        if (search != null && !search.trim().isEmpty()) {
	            String searchPattern = "%" + search + "%";
	            ps.setString(1, searchPattern);
	            ps.setString(2, searchPattern);
	            ps.setString(3, searchPattern);
	        }

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            service = new Service();
	            service.setServiceid(rs.getInt("serviceid"));
	            service.setServicetitle(rs.getString("servicetitle"));
	            service.setServicedescription(rs.getString("servicedescription"));
	            service.setPrice(rs.getDouble("price"));
	            service.setCategory(rs.getString("category"));
	            service.setRating(rs.getDouble("rating"));
	            service.setDemand(rs.getInt("demand"));
	            allServices.add(service);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        if (conn != null) {
	            conn.close();
	        }
	    }
	    return allServices;
	}

}
