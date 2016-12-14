package com.sql;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import com.model.*;

public class DocumentSQL extends DataBase{
	
	public void createDocument(Document document){//save user data
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "insert into document values("
				+ "null,"
				+ ""+document.getSerialNumber()+","
				+ ""+document.getFormTemplate().getTemplateId()+","
				+ "'"+document.getDocumentName()+"',"
				+ ""+document.getVersion()+","
				+ "null,"
				+ "'"+document.getDate()+"',"
				+ "'"+document.getAuthor().getUserId()+"',"
				+ "'"+document.getCWSNumber()+"',"
				+ ""+document.getSign()+")";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public void setDocument(Document document){//save user data
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "update user set "
				+ "documentId="+Integer.toString(document.getDocumentId())+","
				+ "serialNumber="+document.getSerialNumber()+","
				+ "formTemplateid="+document.getFormTemplate().getTemplateId()+","
				+ "documentName='"+document.getDocumentName()+"',"
				+ "version="+document.getVersion()+","
				+ "description=null,"
				+ "date='"+document.getDate()+"',"
				+ "authorId='"+document.getAuthor().getUserId()+"',"
				+ "CWSNumber='"+document.getCWSNumber()+"',"
				+ "sign="+document.getSign()+" "
				+ "where documentId="+Integer.toString(document.getDocumentId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public Document getDocument(int documentId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where documentId="+Integer.toString(documentId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connet();
				FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("forTemplateId"));
				formTemplateSQL.disconnect();
				
				UserSQL userSQL = new UserSQL();
				CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				userSQL.disconnect();
					
				Document document = new Document(formTemplate, careProvider, rs.getString("CWSNumber"));
				document.setSerialNumber(rs.getInt("serialNumber"));
				document.setDocumentName(rs.getString("documentNamte"));
				document.setVersion(rs.getInt("version"));
				document.setDate(rs.getString("date"));
				document.setSign(rs.getBoolean("sign"));
				
				return  document;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public Map getDocumentByCWSNumber(String CWSNumber){
		Map<Integer, Document> documentMap = new HashMap<Integer, Document>();
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				Document document = this.getDocument(rs.getInt("documentId"));
				documentMap.put(document.getDocumentId(), document);
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			return null;
		}
		return documentMap;
	}

	public Document getDocumentByName (String documentName){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where documentName='"+documentName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				Document document = this.getDocument(rs.getInt("documentId"));
				return  document;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public int fakeGetNewDocumentId(){
		
		return (int) (1+Math.random()*(10000-1+1));
	}
}
