package com.model;

import java.util.*;

public class User {
	//attributes
	private int userId;
	private String userName;
	private String passWord;
	private UserType type;
	private String title;
	private String firstName;
	private String lastName;
	private String facility;
	private String email;
	

	//constructor
	public User(String userName, String passWord){
		this.userName=userName;
		this.passWord=passWord;
	}
	//functions
	//userId
	public int getUserId(){
		return this.userId;
	}
	public void setUserId(int userId){
		this.userId = userId;
	}
	//userName
	public String getUserName(){
		return this.userName;
	}
	public void setUserName(String newUserName){
		this.userName = newUserName;
	}
	//passWord
	public UserType getType(){
		return this.type;
	}
	public void setType(UserType type){
		this.type = type;
	}
	//type: admin or care provider
	public String getPassWord(){
		return this.passWord;
	}
	public void setPassWord(String newPassWord){
		this.passWord = newPassWord;
	}
	//title
	public String getTitle(){
		return this.title;
	}
	public void setTitle(String newTitle){
		this.title = newTitle;
	}
	//first name
	public String getFirstName(){
		return this.firstName;
	}
	public void setFirstName(String newFirstName){
		this.firstName = newFirstName;
	}
	//last name
	public String getLastName(){
		return this.lastName;
	}
	public void setLastName(String newLastName){
		this.lastName = newLastName;
	}
	//facility
	public String getFacility(){
		return this.facility;
	}
	public void setFacility(String newFacility){
		this.facility = newFacility;
	}
	//email
	public String getEmail(){
		return this.email;
	}
	public void setEmail(String newEmail){
		this.email = newEmail;
	}
	
	public CareProvider toCareProvider(){
		CareProvider careProvider = new CareProvider(this.userName, this.passWord);
		careProvider.setUserId(this.userId);
		careProvider.setType(this.type);
		careProvider.setTitle(this.title);
		careProvider.setFirstName(this.firstName);
		careProvider.setLastName(this.lastName);
		careProvider.setFacility(this.facility);
		careProvider.setEmail(this.email);
		careProvider.setPatientInfoMap(new HashMap());
		//!!!!!!!!!!!!
		return careProvider;
	}
	
	public Admin toAdmin(){
		Admin admin = new Admin(this.userName, this.passWord);
		admin.setUserId(this.userId);
		admin.setType(this.type);
		admin.setTitle(this.title);
		admin.setFirstName(this.firstName);
		admin.setLastName(this.lastName);
		admin.setFacility(this.facility);
		admin.setEmail(this.email);
		return admin;
	}
}
