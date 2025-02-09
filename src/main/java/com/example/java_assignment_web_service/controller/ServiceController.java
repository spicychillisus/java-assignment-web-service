package com.example.java_assignment_web_service.controller;
import com.example.java_assignment_web_service.db.*;
import java.sql.*;
import java.util.*;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("services")
public class ServiceController {
	private ServiceModel model = new ServiceModel();
	
	@RequestMapping(method = RequestMethod.GET, path = "/allServices")
	public ArrayList<Service> allServices(@RequestParam(value = "search", required = false) String search) throws SQLException {
	    ArrayList<Service> services = new ArrayList<>();
	    try {
	        services = model.allServices(search);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return services;
	}

}
