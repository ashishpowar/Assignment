package com.amdocs.media.assignment.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.amdocs.media.assignment.database.UserDao;
import com.amdocs.media.assignment.jwtutil.JwtUtil;
import com.amdocs.media.assignment.model.AuthenticationRequest;
import com.amdocs.media.assignment.model.AuthenticationResponse;
import com.amdocs.media.assignment.service.MyUserDetailService;
import com.amdocs.media.assignment.model.User;;


@RestController
public class UserLoginController {
	 @Autowired
	    private KafkaTemplate<String, User> kafkaTemplate;
	 private static final String TOPIC = "Kafka_User";
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private	MyUserDetailService userDetailsService;
	@Autowired
	private UserDao dao;

	

	
	@PostMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String saveProfileData(@RequestBody  Map<String, Object> responseBody)
	{
		int res =dao.insert("userDetail",responseBody);
		if(res == 1)
		{
			return "Save Profile Data Sucessfully";
		}
		return "Technical Issue";
				
	}
	@DeleteMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String deleteProfileData(@RequestBody  Map<String, Object> responseBody)
	{
		 kafkaTemplate.send(TOPIC, new User("", "", String.valueOf(responseBody.get("username"))));
		 int res= dao.delete("userDetail","username",responseBody.get("username"));
		 if(res == 1)
			{
				return "Delete Profile Data Sucessfully";
			}
			return "Technical Issue";
		
	}
	
	@PutMapping(value = "/profile", produces = MediaType.APPLICATION_JSON_VALUE)
	public String UpdateProfileData(@RequestBody  Map<String, Object> responseBody)
	{
		
		Map<String, Object> whereBody =new HashMap<String, Object>();
		whereBody.put("username",responseBody.get("username"));
		responseBody.remove("username");
		kafkaTemplate.send(TOPIC, new User(String.valueOf(responseBody.get("address")), String.valueOf(responseBody.get("mobileno")), String.valueOf(responseBody.get("username"))));
		int res= dao.update("userDetail", responseBody, whereBody);
		if(res == 1)
		{
			return "Update Profile Data Sucessfully";
		}
		return "Technical Issue";
		 
		
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {

		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}
		catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}


		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String jwt = jwtTokenUtil.generateToken(userDetails);
		

		return ResponseEntity.ok(new AuthenticationResponse(jwt,userDetails.getUsername()));
	}


}
