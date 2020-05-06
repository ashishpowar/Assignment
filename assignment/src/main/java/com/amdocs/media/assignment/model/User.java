package com.amdocs.media.assignment.model;

public class User {
	 private String address;
	    private String mobileno;
	    private String username;
	    
		
		public User(String address, String mobileno, String username) {
			super();
			this.address = address;
			this.mobileno = mobileno;
			this.username = username;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getMobileno() {
			return mobileno;
		}
		public void setMobileno(String mobileno) {
			this.mobileno = mobileno;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	    

}
