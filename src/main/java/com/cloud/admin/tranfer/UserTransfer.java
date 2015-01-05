package com.cloud.admin.tranfer;

public class UserTransfer {
	
	private String username;
	
	private String role;
	
	public UserTransfer(String username, String role){
		this.username=username;
		this.role=role;
		
	};
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	public String getRole() {
		return role;
	}

}
