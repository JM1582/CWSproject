package com.sql;

import java.sql.*;

import com.model.*;

public class DataBase {
	Connection conn = null;
	Statement st = null;
	
	public boolean connet() throws SQLException, java.lang.ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/CWSproject"; //�ӿ�3306����ȷdatabase��location
		String user = "root";
		String password = "liusheng95";
		try {
			conn = DriverManager.getConnection(url,user,password);
		} catch (Exception e) {
			System.out.println("Database connection fail.");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void factoryReset() {//��ʼ��
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//�����Ի�
		this.dropAllTable();
		String strSQL = "create table IF NOT EXISTS user("//+����string
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
				+ "MRP int,"
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
		

		//relationship of tables-����user and patientInfo
		strSQL = "create table IF NOT EXISTS user_PatientInfo("
				+ "userId int not null,"
				+ "patientId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS patientInfo_document("//���� patientInfo and document
				+ "patientId int not null,"
				+ "documentId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
		strSQL = "create table IF NOT EXISTS patientInfo_actionPlan("
				+ "patientId int not null,"
				+ "actionPlanId int not null )";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
		}
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
			System.out.println("Database closing success.");
		} catch (Exception e) {
			System.out.println("Database closing fail.");
			System.out.println(e.getMessage());
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