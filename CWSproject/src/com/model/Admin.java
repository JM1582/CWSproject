package com.model;

public class Admin extends User{
	public Admin(String userName, String passWord) {
		super(userName, passWord);
		// TODO Auto-generated constructor stub
	}

	public String getOtherUserName(User user){
		return user.getUserName();
	}
	public void setOtherUserName(User user, String newUserName){
		user.setUserName(newUserName);
	}
}
