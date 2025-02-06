package com.example.java_assignment_web_service.controller;
import com.example.java_assignment_web_service.db.*;
import java.sql.*;
import java.util.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("services")
public class ServiceController {
	private ServiceModel model = new ServiceModel();
	
	@RequestMapping(method=RequestMethod.GET, path="/")
	public ArrayList<Service> allServices() throws SQLException {
		ArrayList<Service> services = new ArrayList<Service>();
		try {
			services = model.allServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return services;
	}
}
