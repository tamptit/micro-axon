package com.tanthanh.userservice.controller;

import com.tanthanh.userservice.data.User;
import com.tanthanh.userservice.model.UserDTO;
import com.tanthanh.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/regis")
public class RegisController {
	
	@Autowired
	UserService userService;
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping()
	public UserDTO registry (@RequestBody UserDTO dto) {
		return userService.saveUser(dto);
	}

	@GetMapping("/{username}")
	public String encodePasswordTest (@PathVariable String username, @RequestParam String pwdTest) {
		UserDetails ud = userDetailsService.loadUserByUsername(username);
		System.out.println("pwdEncode= " + passwordEncoder.encode(pwdTest));
		String ta = "ta";
		String s1 = passwordEncoder.encode(ta);
		String s2 = passwordEncoder.encode(ta);
		String s3 = passwordEncoder.encode(ta);
		System.out.println("L1: " + s1);
		System.out.println("L2: " + s2);
		System.out.println("L3: " + s3);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println("L1: " + encoder.matches(s1, "ta"));
		System.out.println("L2: " + encoder.matches(s2, "ta"));
		System.out.println("L3: " + encoder.matches(s3, "ta"));
		System.out.println("l1: " + passwordEncoder.matches(ta, s1));
		System.out.println("l2: " + passwordEncoder.matches(ta, s2));
		System.out.println("l3: " + passwordEncoder.matches(ta, s3));
		return "ok";
	}

}
