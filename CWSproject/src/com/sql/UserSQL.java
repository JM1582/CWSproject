package com.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.model.*;



public class UserSQL extends DataBase{
	
	public void createUser(User user){//save user data
		try{
			st = conn.createStatement();
			st.executeUpdate("insert into user values("
					+ "null,"
					+ "'"+user.getUserName()+"',"
					+ "'"+user.getPassWord()+"',"
					+ Integer.toString((Integer) user.getType().ordinal())+","
					+ "'"+user.getTitle()+"',"
					+ "'"+user.getFirstName()+"',"
					+ "'"+user.getLastName()+"',"
					+ "'"+user.getFacility()+"',"
					+ "'"+user.getEmail()+"' )");
			System.out.println("Add user success.");
		} catch (Exception e){
			System.out.println("Add user fail.");
			System.out.println(e.getMessage());
		}
	}
	
	public void setUser(User user){//save user data
		try{
			st = conn.createStatement();
			String strSQL = "update user set "
					+ "userName='"+user.getUserName()+"',  "
					+ "passWord='"+user.getPassWord()+"', "
					+ "type="+Integer.toString((Integer) user.getType().ordinal())+", "
					+ "title='"+user.getTitle()+"', "
					+ "firstName='"+user.getFirstName()+"', "
					+ "lastName='"+user.getLastName()+"', "
					+ "facility='"+user.getFacility()+"', "
					+ "email='"+user.getEmail()+"' "
					+ "where userId="+Integer.toString(user.getUserId());
			st.executeUpdate(strSQL);
			System.out.println("Update user success.");
		} catch (Exception e){
			System.out.println("Update user fail.");
			System.out.println(e.getMessage());
		}
	}
	
	public User getUser(int userId){
		try{
			st = conn.createStatement();
			String strSQL = "select * from user where userId='"+userId+"'";
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			User user = new User(rs.getString("userName"), rs.getString("passWord"));
			user.setUserId(rs.getInt("userId"));
			user.setType(UserType.values()[rs.getInt("type")]);
			user.setTitle(rs.getString("title"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setFacility(rs.getString("facility"));
			user.setEmail(rs.getString("email"));
			return  user;
		}catch (Exception e){
			System.out.println("Database query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public User getUserByUserName(String userName){
		try{
			st = conn.createStatement();
			String strSQL = "select * from user where userName='"+userName+"'";
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			User user = new User(rs.getString("userName"), rs.getString("passWord"));
			user.setUserId(rs.getInt("userId"));
			user.setType(UserType.values()[rs.getInt("type")]);
			user.setTitle(rs.getString("title"));
			user.setFirstName(rs.getString("firstName"));
			user.setLastName(rs.getString("lastName"));
			user.setFacility(rs.getString("facility"));
			user.setEmail(rs.getString("email"));
			return  user;
		}catch (Exception e){
			System.out.println("Database query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}

	public User userLogin(User user){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String strSQL = "select * from user where userName='"+user.getUserName()+"' and passWord='"+user.getPassWord()+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				User rsUser = new User(rs.getString("userName"), rs.getString("passWord"));
				rsUser.setUserId(rs.getInt("userId"));
				rsUser.setType(UserType.values()[rs.getInt("type")]);
				rsUser.setTitle(rs.getString("title"));
				rsUser.setFirstName(rs.getString("firstName"));
				rsUser.setLastName(rs.getString("lastName"));
				rsUser.setFacility(rs.getString("facility"));
				rsUser.setEmail(rs.getString("email"));
				return  rsUser;
			} else {
				return null;
			}
		}catch (Exception e){
			System.out.println("Query fail: "+strSQL);
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	
	public User getUserSQL(User user){
		try {
			System.out.println("Database connection.");
			this.connet();
			System.out.println("Database connection done.");
		} catch (ClassNotFoundException e) {
			System.out.println("Database connection1 fail.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Database connection2 fail.");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		
		User rs_user = this.getUserByUserName(user.getUserName());
		
		return rs_user;
	}

	public User fakeUserLogin(User user) {
		FakeSQL fakeSQL = new FakeSQL();
		User rsUser = fakeSQL.getUser(user.getUserName());
		if(rsUser==null){
			return null;
		}
		if (rsUser.getUserName().equals(user.getUserName()) && rsUser.getPassWord().equals(user.getPassWord())){
			return rsUser;
		}
		return null;
	}
}
