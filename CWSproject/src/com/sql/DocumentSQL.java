package com.sql;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import com.model.*;

public class DocumentSQL extends DataBase{
	public int getDocumentIdByDocumentName(String documentName){
		int documentId = -1;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where documentName='"+documentName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				documentId = rs.getInt("documentId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return documentId;
		
	}
	
	public boolean isExist(Document document){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from document where documentId="+document.getDocumentId()+"";
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
	
	public Document setDocument(Document document){//save user data
		String strSQL = null;
		if(this.isExist(document)){
			this.updateDocument(document);
		} else {
			document = this.insertDocument(document);
		}
		this.setDomainValue(document);
		return document;
	}
	
	public void updateDocument(Document document){
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
					+ "description='"+document.getDocumentId()+"',"
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
	
	public Document insertDocument(Document document){
		String strSQL = "insert into document values("
					+ "null,"
					+ ""+document.getSerialNumber()+","
					+ ""+document.getFormTemplate().getTemplateId()+","
					+ "'"+document.getDocumentName()+"',"
					+ ""+document.getVersion()+","
					+ "'"+document.getDescription()+"',"
					+ "'"+document.getDate()+"',"
					+ "'"+document.getAuthor().getUserId()+"',"
					+ "'"+document.getCWSNumber()+"',"
					+ ""+document.getSign()+")";
		PreparedStatement pstmt = null;
		try {
			pstmt = (PreparedStatement) this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			pstmt.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		try {
			ResultSet rs = pstmt.getGeneratedKeys();
			if(rs.next()){
				document.setDocumentId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return document;
	}
	
	public void setDomainValue(Document document) {
		this.clearDomainValue(document);
		Map<String, String[]> domainValueMap = document.getDomainValueMap();
		Iterator domainValueIt = domainValueMap.keySet().iterator();
		while(domainValueIt.hasNext()){
			String domainId = (String) domainValueIt.next();
			String[] domainValue = domainValueMap.get(domainId);
			String strSQL = "insert into domainValue values("
					+ Integer.toString(document.getDocumentId())+", "
					+ domainId+", "
					+ domainValue[0]+", "
					+ domainValue[1]+")";
			try {
				st = conn.createStatement();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				st.executeUpdate(strSQL);
			} catch (SQLException e) {
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
			}
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
				
				document = this.getDomainValueMap(document);
				
				return  document;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	private Document getDomainValueMap(Document document) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from domainValue where documentId="+Integer.toString(document.getDocumentId());
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				String domainId = rs.getString("domainId");
				String domainValue[] = new String[2];
				domainValue[0] = rs.getString("domainValue0");
				domainValue[1] = rs.getString("domainValue1");
				document.addDomainValue(domainId, domainValue);
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

	public void clearDomainValue(Document document){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String strSQL = "delete form domainValue where documentId="+document.getDocumentId();
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	// need to be removed
	public int fakeGetNewDocumentId(){
		
		return (int) (1+Math.random()*(10000-1+1));
	}
}
