package com.sql;

import java.sql.*;

import com.model.*;

public class DataBase {
	Connection conn = null;
	Statement st = null;
	
	public boolean connect() throws SQLException, java.lang.ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/CWSproject"; //接口3306，明确database的location
		String user = "root";
		String password = "liusheng95";
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			System.out.println("Database connection fail.");
			e.printStackTrace();
			throw e;
		}
		return true;
	}
	
	public void factoryReset() throws SQLException {//初始化
		st = conn.createStatement();//建立对话
		this.dropAllTable();
		String strSQL = "create table IF NOT EXISTS user("//+连接string
				+ "userId int not null auto_increment,"
				+ "userName varchar(20) not null,"
				+ "passWord varchar(20),"
				+ "type int,"
				+ "title varchar(20),"
				+ "firstName varchar(20),"
				+ "lastName varchar(20),"
				+ "facility varchar(20),"
				+ "email varchar(40),"
				+ "primary key (userId),"
				+ "unique key (userName) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS patientInfo("
				+ "patientInfoId int not null auto_increment,"
				+ "CWSNumber varchar(20) not null,"
				+ "icon int,"
				//+ "MRP int,"
				+ "formTemplateId int,"
				+ "primary key (patientInfoId),"
				+ "unique key (CWSNumber) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS formTemplate("
				+ "formTemplateId int not null auto_increment,"
				+ "formTemplateName varchar(100) not null,"
				+ "primary key (formTemplateId),"
				+ "unique key (formTemplateName) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS onePart("
				+ "partId varchar(20) not null,"
				+ "partName varchar(100),"
				+ "partDescription varchar(100),"
				+ "primary key (partId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS subSet("
				+ "subSetId varchar(20) not null,"
				+ "subSetName varchar(100),"
				+ "primary key (subSetId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS domain("
				+ "domainId varchar(20) not null,"
				+ "domainName varchar(100),"
				+ "primary key (domainId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS document("
				+ "documentId int not null auto_increment,"
				+ "serialNumber int,"
				+ "formTemplateId int,"
				+ "documentName varchar(100),"
				+ "version int,"
				+ "description varchar(100),"
				+ "date varchar(20),"
				+ "authorId int not null,"
				+ "CWSNumber varchar(20),"
				+ "sign int,"
				+ "primary key (documentId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS domainValue("
				+ "documentId int not null, "
				+ "domainId varchar(20), "
				+ "value1 varchar(5), "
				+ "value2 varchar(5))";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS actionPlan("
				+ "actionPlanId int not null auto_increment, "
				+ "CWSNumber varchar(20), "
				+ "authorId int, "
				+ "date varchar(20), "
				+ "sign int, "
				+ "primary key (actionPlanId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS actionEntry("
				+ "actionEntryId int not null auto_increment,"
				+ "domainId varchar(20),"
				+ "cScore varchar(5),"
				+ "fScore varchar(5),"
				+ "primary key (actionEntryId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS action("
				+ "actionId int not null auto_increment,"
				+ "intervention varchar(20),"
				+ "careProviderId int,"
				+ "comment varchar(40),"
				+ "primary key (actionId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS partScalar("
				+ "partId varchar(20) not null,"
				+ "scalarName varchar(40),"
				+ "scalarValue1 varchar(5),"
				+ "scalarValue2 varchar(5) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS domainValue("
				+ "documentId int not null,"
				+ "domainId varchar(20),"
				+ "domainValue0 varchar(5),"
				+ "domainValue1 varchar(5) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		

		//relationship of tables-连接user and patientInfo
		strSQL = "create table IF NOT EXISTS user_patientInfo("
				+ "userId int not null, "
				+ "userName varchar(20) not null, "
				+ "patientInfoId int not null, "
				+ "CWSNumber varchar(20) not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		/* the document table already include CWSNumber
		strSQL = "create table IF NOT EXISTS patientInfo_document("//连接 patientInfo and document
				+ "patientInfoId int not null,"
				+ "documentId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		*/
		/* the actionPlan table already include CWSNumber
		strSQL = "create table IF NOT EXISTS patientInfo_actionPlan("
				+ "patientInfoId int not null,"
				+ "actionPlanId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		*/
		strSQL = "create table IF NOT EXISTS actionPlan_action( "
				+ "actionPlanId int not null, "
				+ "actionEntryId int not null, "
				+ "actionId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		strSQL = "create table IF NOT EXISTS formTemplate_domain("
				+ "domainId varchar(20) not null,"
				+ "subSetId varchar(20) not null,"
				+ "partId varchar(20) not null,"
				+ "formTemplateId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
	}
	
	public void disconnect() throws Exception{
		try{
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Database closing fail.");
			e.printStackTrace();
			throw e;
		}
	}
	
	public void dropTalbe(String tableName) throws SQLException{
		st = conn.createStatement();
		String strSQL = "drop table IF EXISTS "+tableName;
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public void dropAllTable() throws SQLException{
		DatabaseMetaData md = null;
		try {
			md = conn.getMetaData();
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw e1;
		}
		ResultSet rs = null;
		try {
			rs = md.getTables(null, null, "%", null);
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			while(rs.next()){
				this.dropTalbe(rs.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public int lastInsertId() throws SQLException{
		st = conn.createStatement();
		String strSQL = "select last_insert_id()";
		try {
			ResultSet rs  = st.executeQuery(strSQL);
			if(rs.next()){
				return rs.getInt("last_insert_id()");
			}
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return -1;
	}
}
