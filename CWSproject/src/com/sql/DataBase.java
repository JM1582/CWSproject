package com.sql;

import java.sql.*;

import com.model.*;

public class DataBase {
	Connection conn = null;
	Statement st = null;
	
	public boolean connet() throws SQLException, java.lang.ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/CWSproject"; //接口3306，明确database的location
		String user = "root";
		String password = "abcd1234";
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			System.out.println("Database connection fail.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void factoryReset() {//初始化
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//建立对话
		this.dropAllTable();
		String strSQL = "create table IF NOT EXISTS user("//+连接string
				+ "userId int not null auto_increment,"
				+ "userName varchar(20) not null,"
				+ "passWord varchar(20) not null,"
				+ "type int not null,"
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
		}
		strSQL = "create table IF NOT EXISTS patientInfo("
				+ "patientId int not null auto_increment,"
				+ "CWSNumber varchar(20) not null,"
				+ "icon int not null,"
				//+ "MRP int,"
				+ "formTemplateId int,"
				+ "primary key (patientId),"
				+ "unique key (CWSNumber) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
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
		}
		strSQL = "create table IF NOT EXISTS document("
				+ "documentId int not null auto_increment,"
				+ "serialNumber int not null,"
				+ "formTemplateId int not null,"
				+ "documentName varchar(100),"
				+ "version int not null,"
				+ "description varchar(100),"
				+ "date varchar(20) not null,"
				+ "authorId int not null,"
				+ "CWSNumber varchar(20) not null,"
				+ "sign int not null,"
				+ "primary key (documentID) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS actionPlan("
				+ "actionPlanId int not null auto_increment,"
				+ "CWSNumber varchar(20) not null,"
				+ "primary key (actionPlanId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS actionEntry("
				+ "actionEntryId int not null auto_increment,"
				+ "domainId varchar(20) not null,"
				+ "cScore int,"
				+ "fScore int,"
				+ "primary key (actionEntryId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS action("
				+ "actionId int not null auto_increment,"
				+ "intervention varchar(20) not null,"
				+ "careProviderId int,"
				+ "comment varchar(40),"
				+ "primary key (actionId) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS partScalar("
				+ "partId int not null,"
				+ "scalarName varchar(20) not null,"
				+ "scalarValue1 varchar(5),"
				+ "scalarValue2 varchar(5) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS domainValue("
				+ "documentId int not null,"
				+ "domainId varchar(20) not null,"
				+ "domainValue1 varchar(5),"
				+ "domainValue2 varchar(5) )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		

		//relationship of tables-连接user and patientInfo
		strSQL = "create table IF NOT EXISTS user_patientInfo("
				+ "userId int not null, "
				+ "userName varchar(20) not null, "
				+ "patientId int not null "
				+ "CWSNumber varchar(20) not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		/* the document table already include CWSNumber
		strSQL = "create table IF NOT EXISTS patientInfo_document("//连接 patientInfo and document
				+ "patientId int not null,"
				+ "documentId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		*/
		/* the actionPlan table already include CWSNumber
		strSQL = "create table IF NOT EXISTS patientInfo_actionPlan("
				+ "patientId int not null,"
				+ "actionPlanId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		*/
		strSQL = "create table IF NOT EXISTS actionPlan_actionEntry("
				+ "actionPlanId int not null,"
				+ "actionEntryId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS actionEntry_action("
				+ "actionEntryId int not null,"
				+ "actionId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
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
		}
	}
	
	public void disconnect(){
		try{
			st.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("Database closing fail.");
			e.printStackTrace();
		}
	}
	
	public void dropTalbe(String tableName){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String strSQL = "drop table IF EXISTS "+tableName;
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public void dropAllTable(){
		DatabaseMetaData md = null;
		try {
			md = conn.getMetaData();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ResultSet rs = null;
		try {
			rs = md.getTables(null, null, "%", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			while(rs.next()){
				this.dropTalbe(rs.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
