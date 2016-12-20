package com.sql;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.model.*;



public class UserSQL extends DataBase{
	public int getUserIdByUserName(String userName) throws Exception{
		int userId = -1;
		st = conn.createStatement();
		String strSQL = "select * from user where userName='"+userName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				userId = rs.getInt("userId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return userId;
		
	}
	
	public boolean isExist(User user) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from user where userId="+user.getId()+"";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	public boolean isExistUserName(User user) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from user where userName='"+user.getUserName()+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return true;
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	public User setUser(User user) throws Exception{//save user data
		if(this.isExist(user)){
			this.updateUser(user);
		} else {
			user = this.insertUser(user);
		}
		return user;
	}
	
	private User insertUser(User user) throws Exception {
		if(this.isExistUserName(user)){
			System.out.println("Username was already taken: "+user.getUserName());
			throw new SQLException();
		}
		String strSQL = "insert into user("
				+ "userName,"
				+ "passWord,"
				+ "type,"
				+ "title,"
				+ "firstName,"
				+ "lastName,"
				+ "facility,"
				+ "email) "
				+ "values(?,?,?,?,?,?,?,?)";
		PreparedStatement st = null;
		st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		
		st.setString(1, user.getUserName());
		st.setString(2, user.getPassWord());
		st.setInt(3, (Integer)user.getType().ordinal());
		st.setString(4, user.getTitle());
		st.setString(5, user.getFirstName());
		st.setString(6, user.getLastName());
		st.setString(7, user.getFacility());
		st.setString(8, user.getEmail());
		
		try{
			st.executeUpdate();
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		try {
			ResultSet rs = st.getGeneratedKeys();
			if(rs.next()){
				user.setId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return user;
	}

	private void updateUser(User user) throws Exception {
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
					+ "where userId="+Integer.toString(user.getId());

		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}

	public User getUser(int userId) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from user where userId='"+userId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				User user = new User(rs.getString("userName"), rs.getString("passWord"));
				user.setId(rs.getInt("userId"));
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
			throw e;
		}
		return null;
	}
	
	public User getUserByUserName(String userName) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		return null;
	}

	public User userLogin(User user) throws Exception{
		User rsUser = this.getUserByUserName(user.getUserName());
		if(rsUser.getPassWord().equals(user.getPassWord())){
			return rsUser;
		}
		return null;
	}
	
	public void removeUser(User user) throws SQLException{
		st = conn.createStatement();
		String strSQL = "delete from user where userName='"+user.getUserName()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		strSQL = "delete form user_patientInfo where userName='"+user.getUserName()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Map<Integer, User> getAllUser() throws Exception{
		Map<Integer, User> userMap = new HashMap<Integer, User>();
		st = conn.createStatement();
		String strSQL = "select * from user";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				int userId = rs.getInt("userId");
				User user = this.getUser(userId);
				
				userMap.put(userId, user);
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return userMap;
	}

	public Map<Integer, CareProvider> getAllCareProvider() throws Exception {
		Map<Integer, CareProvider> careProviderMap = new HashMap<Integer, CareProvider>();
		st = conn.createStatement();
		String strSQL = "select * from user where type="+Integer.toString(UserType.CAREPROVIDER.ordinal());
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				int userId = rs.getInt("userId");
				CareProvider careProvider = this.getUser(userId).toCareProvider();
				
				careProviderMap.put(userId, careProvider);
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return careProviderMap;
	}
	
	// need to be removed
	public User getUserSQL(User user) throws Exception{
		try {
			this.connect();
		} catch (ClassNotFoundException e) {
			System.out.println("Database connection1 fail.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (SQLException e) {
			System.out.println("Database connection2 fail.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
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
