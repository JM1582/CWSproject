package com.sql;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import com.model.*;

public class DocumentSQL extends DataBase{
	
	public void createDocument(Document document){//save user data
		try{
			st = conn.createStatement();
			st.executeUpdate("insert into document values("
					+ "null,"
					+ ""+document.getSerialNumber()+","
					+ ""+document.getFormTemplate().getTemplateId()+","
					+ "'"+document.getDocumentName()+"',"
					+ ""+document.getVersion()+","
					+ "null,"
					+ "'"+document.getDate()+"',"
					+ "'"+document.getAuthor().getUserId()+"',"
					+ "'"+document.getCWSNumber()+"',"
					+ ""+document.getSign()+")");
			System.out.println("Add document success.");
		} catch (Exception e){
			System.out.println("Add document fail.");
			System.out.println(e.getMessage());
		}
	}
	
	public void setDocument(Document document){//save user data
		try{
			st = conn.createStatement();
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
			st.executeUpdate(strSQL);
			System.out.println("Update document success.");
		} catch (Exception e){
			System.out.println("Update document fail.");
			System.out.println(e.getMessage());
		}
	}
	
	public Document getDocument(int documentId){
		try{
			st = conn.createStatement();
			String strSQL = "select * from document where documentId='"+documentId+"'";
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
			formTemplateSQL.connet();
			FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("forTemplateId"));
			
			UserSQL userSQL = new UserSQL();
			CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				
			Document document = new Document(formTemplate, careProvider, rs.getString("CWSNumber"));
			document.setSerialNumber(rs.getInt("serialNumber"));
			document.setDocumentName(rs.getString("documentNamte"));
			document.setVersion(rs.getInt("version"));
			document.setDate(rs.getString("date"));
			document.setSign(rs.getBoolean("sign"));
			
			System.out.println("Get document query success.");
			
			return  document;
		}catch (Exception e){
			System.out.println("Get document query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Map getDocumentByCWSNumber(String CWSNumber){
		Map documentMap = new HashMap();
		try{
			st = conn.createStatement();
			String strSQL = "select * from document where CWSNumber='"+CWSNumber+"'";
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connet();
				FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("forTemplateId"));
				
				UserSQL userSQL = new UserSQL();
				CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				
				Document document = new Document(formTemplate, careProvider, rs.getString("CWSNumber"));
				document.setSerialNumber(rs.getInt("serialNumber"));
				document.setDocumentName(rs.getString("documentNamte"));
				document.setVersion(rs.getInt("version"));
				document.setDate(rs.getString("date"));
				document.setSign(rs.getBoolean("sign"));
				documentMap.put(document.getDocumentId(), document);
			}
			System.out.println("Get documentMap query success.");
			return  documentMap;
		}catch (Exception e){
			System.out.println("Get documentMap query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public int fakeGetNewDocumentId(){
		
		return (int) (1+Math.random()*(10000-1+1));
	}
}
