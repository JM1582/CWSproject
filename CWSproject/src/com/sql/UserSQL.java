package com.sql;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.model.*;



public class UserSQL extends DataBase{
	public int getUserIdByUserName(String userName){
		int userId = -1;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from user where userName='"+userName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				userId = rs.getInt("userId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return userId;
		
	}
	
	public boolean isExist(User user){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where userName='"+user.getUserName()+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return false;
	}
	
	public void setUser(User user){//save user data
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = null;
		if(this.isExist(user)){
			user.setUserId(this.getUserIdByUserName(user.getUserName()));
			strSQL = "update user set "
					+ "userName='"+user.getUserName()+"',  "
					+ "passWord='"+user.getPassWord()+"', "
					+ "type="+Integer.toString((Integer) user.getType().ordinal())+", "
					+ "title='"+user.getTitle()+"', "
					+ "firstName='"+user.getFirstName()+"', "
					+ "lastName='"+user.getLastName()+"', "
					+ "facility='"+user.getFacility()+"', "
					+ "email='"+user.getEmail()+"' "
					+ "where userId="+Integer.toString(user.getUserId());
		} else {
			strSQL = "insert into user values("
					+ "null,"
					+ "'"+user.getUserName()+"',"
					+ "'"+user.getPassWord()+"',"
					+ Integer.toString((Integer) user.getType().ordinal())+","
					+ "'"+user.getTitle()+"',"
					+ "'"+user.getFirstName()+"',"
					+ "'"+user.getLastName()+"',"
					+ "'"+user.getFacility()+"',"
					+ "'"+user.getEmail()+"' )";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public User getUser(int userId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from user where userId='"+userId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				User user = new User(rs.getString("userName"), rs.getString("passWord"));
				user.setUserId(rs.getInt("userId"));
				user.setType(UserType.values()[rs.getInt("type")]);
				user.setTitle(rs.getString("title"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setFacility(rs.getString("facility"));
				user.setEmail(rs.getString("email"));
				return  user;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public User getUserByUserName(String userName){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from user where userName='"+userName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				User user = this.getUser(rs.getInt("userId"));
				return user;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	public User userLogin(User user){
		User rsUser = this.getUser(user.getUserId());
		if(rsUser.getPassWord().equals(user.getPassWord())){
			return rsUser;
		}
		return null;
	}
	
	public void removeUser(User user){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "delete from user where userName='"+user.getUserName()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		strSQL = "delete form user_patientInfo where userName='"+user.getUserName()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// need to be removed
	public User getUserSQL(User user){
		try {
			this.connet();
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
	
	
	// need to be removed
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
