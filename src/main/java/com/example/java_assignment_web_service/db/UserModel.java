package com.example.java_assignment_web_service.db;

import java.sql.*;
import java.util.*;

public class UserModel {
	
	private Config neon = new Config();
	private String url = neon.getConnectionUrl();
	private String username = neon.getUser();
	private String dbPassword = neon.getPassword();
	
	public int checkUserExists(int userid) throws SQLException {
		int userCount = 0;
		Connection conn = DriverManager.getConnection(url, username, dbPassword);
		
		try {
			String sql = "SELECT COUNT(id) AS count FROM users WHERE id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userid);
			ResultSet rs = ps.executeQuery();
			if (rs.next() && rs.getInt("count") > 0) {
				userCount = rs.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userCount;
	}
}
