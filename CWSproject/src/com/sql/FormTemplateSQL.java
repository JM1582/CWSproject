package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class FormTemplateSQL extends DataBase{
	
	public void createFormTemplate(FormTemplate formTemplate){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		String strSQL = "insert into formTemplate values("
				+ "null,"
				+ "'"+formTemplate.getTemplateName()+"')";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			return;
		}
		
		Map partMap = formTemplate.getPartsMap();
		Iterator partIt = partMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partMap.get(partId);
			strSQL="insert into onePart values("
					+ "'"+part.getPartId()+"', "
					+ "'"+part.getPartName()+"',"
					+ "'"+part.getPartDescription()+"')";
			try{
				st.executeUpdate(strSQL);
			} catch (Exception e){
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
			}
			Map subSetMap = part.getSubSetMap();
			Iterator subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				strSQL = "insert into subSet values("
						+ "'"+subSet.getSubSetId()+"',"
						+ "'"+subSet.getSubSetName()+"')";
				try{
					st.executeUpdate(strSQL);
				} catch (Exception e){
					System.out.println("Fail: "+strSQL);
					e.printStackTrace();
				}
				Map domainMap = subSet.getDomainMap();
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					strSQL = "insert into domain values("
							+ "'"+domain.getDomainId()+"',"
							+ "'"+domain.getDomainName()+"')";
					try{
						st.executeUpdate(strSQL);
					} catch (Exception e){
						System.out.println("Fail: "+strSQL);
						e.printStackTrace();
					}
					strSQL = "insert into formTemplate_domain values("
							+ "'"+domain.getDomainId()+"',"
							+ "'"+subSet.getSubSetId()+"',"
							+ "'"+part.getPartId()+"',"
							+ ""+formTemplate.getTemplateId()+")";
					try{
						st.executeUpdate(strSQL);
					} catch (Exception e){
						System.out.println("Fail: "+strSQL);
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	public void setFormTemplate(FormTemplate formTemplate){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return;
		}
		String strSQL = "update formTemplate set "
				+ "formTemplateId="+Integer.toString(formTemplate.getTemplateId())+","
				+ "formTemplateName='"+formTemplate.getTemplateName()+"' "
				+ "where formTemplateId="+Integer.toString(formTemplate.getTemplateId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}

		strSQL = "delete from formTemplate_domain where formTemplateId="+Integer.toString(formTemplate.getTemplateId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}

		Map partMap = formTemplate.getPartsMap();
		Iterator partIt = partMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partMap.get(partId);
			strSQL = "update onePart set "
					+ "partId='"+part.getPartId()+"', "
					+ "partName='"+part.getPartName()+"', "
					+ "partDescription='"+part.getPartDescription()+"' "
					+ "where partId='"+part.getPartId()+"'";
			try{
				st.executeUpdate(strSQL);
			} catch (Exception e){
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
			}
			Map subSetMap = part.getSubSetMap();
			Iterator subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				strSQL = "update subSet set "
						+ "subSetId='"+subSet.getSubSetId()+"', "
						+ "subSetName='"+subSet.getSubSetName()+"' "
						+ "where subSetId='"+subSet.getSubSetId()+"'";
				try{
					st.executeUpdate(strSQL);
				} catch (Exception e){
					System.out.println("Fail: "+strSQL);
					e.printStackTrace();
				}
				Map domainMap = subSet.getDomainMap();
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					strSQL = "update domain set "
							+ "domainId='"+domain.getDomainId()+"', "
							+ "domainName='"+domain.getDomainName()+"' "
							+ "where domainId='"+domain.getDomainId()+"'";
					try{
						st.executeUpdate(strSQL);
					} catch (Exception e){
						System.out.println("Fail: "+strSQL);
						e.printStackTrace();
					}
					strSQL = "insert into formTemplate_domain values("
							+ "'"+domain.getDomainId()+"',"
							+ "'"+subSet.getSubSetId()+"',"
							+ "'"+part.getPartId()+"',"
							+ ""+formTemplate.getTemplateId()+")";
					try{
						st.executeUpdate(strSQL);
					} catch (Exception e){
						System.out.println("Fail: "+strSQL);
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public FormTemplate getFormTemplate(int formTemplateId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate where formTemplateId="+Integer.toString(formTemplateId);
		ResultSet rs = null;
		try{
			rs = st.executeQuery(strSQL);
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			return null;
		}
		FormTemplate formTemplate = null;
		try {
			if(rs.next()){ 
				formTemplate= new FormTemplate(rs.getString("formTemplateName"));
				formTemplate.setTemplateId(rs.getInt("formTemplateId"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		formTemplate.setPartsMap(this.getPartMap(formTemplateId));
		//!!!!!need add domain search
		return formTemplate;
	}

	public FormTemplate getFormTemplateByName(int formTemplateId){
		try{
			st = conn.createStatement();
			String strSQL = "select * from formTemplate where formTemplateId="+Integer.toString(formTemplateId);
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			FormTemplate formTemplate = new FormTemplate(rs.getString("formTemplateName"));
			formTemplate.setTemplateId(rs.getInt("formTemplateId"));
			formTemplate.setPartsMap(this.getPartMap(rs.getInt("formTemplateId")));
			return formTemplate;
		}catch (Exception e){
			System.out.println("Database query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Map getPartMap(int formTemplateId){
		Map partMap = new HashMap();
		OnePart part;
		SubSet subSet;
		Domain domain;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate_domain where formTemplateId="+Integer.toString(formTemplateId);
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try{
			while(rs.next()){
				part=this.getPart(rs.getString("partId"));
				subSet=this.getSubSet(rs.getString("subSetId"));
				domain=this.getDomain(rs.getString("domainId"));
				
				subSet.addDomain(domain);
				part.addSubSet(subSet);
				
				
				partMap.put(rs.getShort("partId"), part);
				
				subSet = new SubSet(rs.getString("subSetId"), rs.getString("subSetName"));
				subSet.setSubSetId(rs.getString("subSetId"));
				
			}
			//!!!!!need add domain search
			return partMap;
		}catch (Exception e){
			System.out.println("Database query fail.");
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public OnePart getPart(String partId){
		return null;
	}
	
	public SubSet getSubSet(String subSetId){
		try{
			st = conn.createStatement();
			String strSQL = "select * from subSet where subSetId='"+subSetId+"'";
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			SubSet subSet = new SubSet(rs.getString("subSetId"), rs.getString("subSetName"));
			//subSet.setTemplateId(rs.getInt("formTemplateId"));
			//!!!!!need add domain search
			return subSet;
		}catch (Exception e){
			System.out.println("Database query fail.");
			System.out.println(e.getMessage());
			return null;
		}
		
	}

	public Domain getDomain(String domainId){
		return null;
	}
	
}
