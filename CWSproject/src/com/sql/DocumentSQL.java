package com.sql;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

import com.model.*;

public class DocumentSQL extends DataBase{
	public int getDocumentIdByDocumentName(String documentName) throws Exception{
		int documentId = -1;
		st = conn.createStatement();
		String strSQL = "select * from document where documentName='"+documentName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				documentId = rs.getInt("documentId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return documentId;
		
	}
	
	public boolean isExist(Document document) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from document where documentId="+document.getId()+"";
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
	
	public Document setDocument(Document document) throws Exception{//save user data
		if(this.isExist(document)){
			this.updateDocument(document);
		} else {
			document = this.insertDocument(document);
		}
		this.setDomainValue(document);
		return document;
	}
	
	public void updateDocument(Document document) throws Exception{
		st = conn.createStatement();
		String strSQL = "update document set "
					+ "documentId="+Integer.toString(document.getId())+","
					+ "serialNumber="+document.getSerialNumber()+","
					+ "formTemplateid="+document.getFormTemplate().getId()+","
					+ "documentName='"+document.getName()+"',"
					+ "version="+document.getVersion()+","
					+ "description='"+document.getId()+"',"
					+ "date='"+document.getDate()+"',"
					+ "authorId='"+document.getAuthor().getId()+"',"
					+ "CWSNumber='"+document.getCWSNumber()+"',"
					+ "sign="+document.getSign()+" "
					+ "where documentId="+Integer.toString(document.getId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public Document insertDocument(Document document) throws Exception{
		String strSQL = "insert into document("
				+ "serialNumber,"
				+ "formTemplateId,"
				+ "documentName,"
				+ "version,"
				+ "description,"
				+ "date,"
				+ "authorId,"
				+ "CWSNumber,"
				+ "sign) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement st = null;
		st = this.conn.prepareStatement(strSQL,Statement.RETURN_GENERATED_KEYS);
		
		st.setInt(1, document.getSerialNumber());
		st.setInt(2, document.getFormTemplate().getId());
		st.setString(3, document.getName());
		st.setInt(4, document.getVersion());
		st.setString(5, document.getDescription());
		st.setString(6, document.getDate());
		st.setInt(7, document.getAuthor().getId());
		st.setString(8, document.getCWSNumber());
		st.setBoolean(9, document.getSign());
		
		
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
				document.setId(rs.getInt(1));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return document;
	}
	
	public void setDomainValue(Document document) throws SQLException {
		this.clearDomainValue(document);
		Map<String, String[]> domainValueMap = document.getDomainValueMap();
		Iterator<String> domainValueIt = domainValueMap.keySet().iterator();
		while(domainValueIt.hasNext()){
			String domainId = (String) domainValueIt.next();
			String[] domainValue = domainValueMap.get(domainId);
			String strSQL = "insert into domainValue values("
					+ Integer.toString(document.getId())+", "
					+ domainId+", "
					+ domainValue[0]+", "
					+ domainValue[1]+")";
			st = conn.createStatement();
			try {
				st.executeUpdate(strSQL);
			} catch (SQLException e) {
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
				throw e;
			}
		}
	}

	public Document getDocument(int documentId) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from document where documentId="+Integer.toString(documentId);
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connect();
				FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("forTemplateId"));
				formTemplateSQL.disconnect();
				
				UserSQL userSQL = new UserSQL();
				CareProvider careProvider = userSQL.getUser(rs.getInt("authorId")).toCareProvider();
				userSQL.disconnect();
					
				Document document = new Document(formTemplate, careProvider, rs.getString("CWSNumber"));
				document.setSerialNumber(rs.getInt("serialNumber"));
				document.setName(rs.getString("documentNamte"));
				document.setVersion(rs.getInt("version"));
				document.setDate(rs.getString("date"));
				document.setSign(rs.getBoolean("sign"));
				
				document = this.getDomainValueMap(document);
				
				return  document;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	private Document getDomainValueMap(Document document) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from domainValue where documentId="+Integer.toString(document.getId());
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
			throw e;
		}
		return null;
	}

	public Map getDocumentByCWSNumber(String CWSNumber) throws Exception{
		Map<Integer, Document> documentMap = new HashMap<Integer, Document>();
		st = conn.createStatement();
		String strSQL = "select * from document where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			while(rs.next()){
				Document document = this.getDocument(rs.getInt("documentId"));
				documentMap.put(document.getId(), document);
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return documentMap;
	}

	public Document getDocumentByName (String documentName) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		return null;
	}

	public void clearDomainValue(Document document) throws SQLException{
		st = conn.createStatement();
		String strSQL = "delete from domainValue where documentId="+document.getId();
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	// need to be removed
	public int fakeGetNewDocumentId(){
		
		return (int) (1+Math.random()*(10000-1+1));
	}
}
