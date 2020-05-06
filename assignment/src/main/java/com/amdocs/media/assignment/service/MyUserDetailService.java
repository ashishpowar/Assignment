package com.amdocs.media.assignment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amdocs.media.assignment.database.UserDao;
import com.amdocs.media.assignment.database.UserDaoImpl;

@Service
public class MyUserDetailService implements UserDetailsService {
	@Autowired
	private UserDaoImpl userDao;
	
	 @Override
	    public UserDetails loadUserByUsername(String strUser) throws UsernameNotFoundException {
		 System.out.println("Enter Username "+ strUser);
		 List<Map<String, Object>> list=userDao.getData("USER_DATA", strUser);
		 System.out.println(list.get(0).get("email"));
		 System.out.println(list.get(0).get("username"));
		 String userName=(String) list.get(0).get("username");
		 String password=(String) list.get(0).get("username");
		 
	        return new User(userName, password,
	                new ArrayList<>());
	    }

}
