package com.example.java_assignment_web_service.controller;

import org.springframework.web.bind.annotation.*;

import com.example.java_assignment_web_service.db.*;

@RestController
@RequestMapping("users")
public class UserController {
	private UserModel userModel = new UserModel();
}
