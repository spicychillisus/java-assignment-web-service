package com.example.java_assignment_web_service.db;
import java.util.*;
import java.sql.*;
public class ServiceModel {
	
	private Config neon = new Config();
	private String url = neon.getConnectionUrl();
	private String username = neon.getUser();
	private String dbPassword = neon.getPassword();
	
	public ArrayList<Service> allServices() throws SQLException {
		ArrayList<Service> allServices = new ArrayList<Service>();
		Connection conn = null;
		String sql = "";
		Service service = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(url, username, dbPassword);
			sql = "SELECT * FROM services";
			PreparedStatement ps = conn.prepareStatement(sql);
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
		}
		
		
		
		return allServices;
	}
}
