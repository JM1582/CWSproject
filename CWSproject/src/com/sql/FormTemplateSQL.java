package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class FormTemplateSQL extends DataBase{
	public int getFormTemplateIdByFormTemplateName(String formTemplateName){
		int formTemplateId = -1;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				formTemplateId = rs.getInt("formTemplateId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return formTemplateId;
		
	}
	
	public boolean isExist(FormTemplate formTemplate){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplate.getTemplateName()+"'";
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
	
	//need to be removed
	public int searchFormTemplateId(String formTemplateName){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
			return -1;
		}
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return rs.getInt("formTemplateId");
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return -1;
		
	}
	
	public void setFormTemplate(FormTemplate formTemplate){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = null;
		if(this.isExist(formTemplate)){
			formTemplate.setTemplateId(this.getFormTemplateIdByFormTemplateName(formTemplate.getTemplateName()));
			strSQL = "update formTemplate set "
					+ "formTemplateId="+Integer.toString(formTemplate.getTemplateId())+","
					+ "formTemplateName='"+formTemplate.getTemplateName()+"' "
					+ "where formTemplateId="+Integer.toString(formTemplate.getTemplateId());
			
		} else {
			strSQL = "insert into formTemplate values("
					+ "null,"
					+ "'"+formTemplate.getTemplateName()+"')";
		}
		
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		
		this.clearFormTemplateDomain(formTemplate);
		
		Map partMap = formTemplate.getPartsMap();
		Iterator partIt = partMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partMap.get(partId);
			this.setPart(part);
			Map subSetMap = part.getSubSetMap();
			Iterator subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				this.setSubSet(subSet);
				Map domainMap = subSet.getDomainMap();
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					this.setDomain(domain);
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
			e.printStackTrace();
		}
		
		formTemplate.setPartsMap(this.getPartMap(formTemplateId));
		return formTemplate;
	}

	public FormTemplate getFormTemplateByName(String formTemplateName){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
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
				formTemplate= this.getFormTemplate(rs.getInt("formTemplateId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return formTemplate;
	}
	
	public Map<String, OnePart> getPartMap(int formTemplateId){
		Map<String, OnePart> partMap = new HashMap<String, OnePart>();
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate_domain where formTemplateId="+Integer.toString(formTemplateId);
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			while(rs.next()){
				String partId = rs.getString("partId");
				if(!partMap.containsKey(partId)){
					OnePart part=this.getPart(partId);
					part.setSubSetMap(this.getSubSetMap(partId,formTemplateId));
					partMap.put(partId, part);
				}
			}
			return partMap;
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	private Map<String, SubSet> getSubSetMap(String partId, int formTemplateId) {
		Map<String, SubSet> subSetMap = new HashMap<String, SubSet>();
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate_domain where "
				+ "formTemplateId="+Integer.toString(formTemplateId)+" and "
				+ "partId='"+partId+"'";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			while(rs.next()){
				String subSetId = rs.getString("subSetId");
				if(!subSetMap.containsKey(subSetId)){
					SubSet subSet=this.getSubSet(subSetId);
					subSet.setDomainMap(this.getDomainMap(subSetId,partId,formTemplateId));
					subSetMap.put(subSetId, subSet);
				}
			}
			return subSetMap;
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	private Map<String, Domain> getDomainMap(String subSetId, String partId, int formTemplateId) {
		Map<String, Domain> domaintMap = new HashMap<String, Domain>();
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from formTemplate_domain where "
				+ "formTemplateId="+Integer.toString(formTemplateId)+" and "
				+ "partId='"+partId+"' and "
				+ "subSetId='"+subSetId+"'";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try{
			while(rs.next()){
				String domainId = rs.getString("domainId");
				if(!domaintMap.containsKey(domainId)){
					Domain domain=this.getDomain(domainId);
					domaintMap.put(domainId, domain);
				}
			}
			return domaintMap;
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean isExistOnePart(OnePart part){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from onePart where partId='"+part.getPartId()+"'";
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
	
	public OnePart getPart(String partId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from onePart where partId='"+partId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				OnePart part = new OnePart();
				part.setPartId(partId);
				part.setPartName(rs.getString("partName"));
				part.setPartDescription(rs.getString("partDescription"));
				return part;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public void setPart(OnePart part){
		String strSQL = null;
		if(this.isExistOnePart(part)){
			strSQL = "update onePart set "
					+ "partId='"+part.getPartId()+"', "
					+ "partName='"+part.getPartName()+"', "
					+ "partDescription='"+part.getPartDescription()+"' "
					+ "where partId='"+part.getPartId()+"'";
		} else {
			strSQL = "insert into onePart values("
					+ "'"+part.getPartId()+"', "
					+ "'"+part.getPartName()+"', "
					+ "'"+part.getPartDescription()+"')";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public boolean isExistSubSet(SubSet subSet){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from subSet where subSetId='"+subSet.getSubSetId()+"'";
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
	
	public SubSet getSubSet(String subSetId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from subSet where subSetId='"+subSetId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				SubSet subSet = new SubSet(rs.getString("subSetId"), rs.getString("subSetName"));
				return subSet;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public void setSubSet(SubSet subSet){
		String strSQL = null;
		if(this.isExistSubSet(subSet)){
			strSQL = "update subSet set "
					+ "subSetId='"+subSet.getSubSetId()+"', "
					+ "subSetName='"+subSet.getSubSetName()+"' "
					+ "where subSetId='"+subSet.getSubSetId()+"'";
		} else {
			strSQL = "insert into subSet values("
					+ "'"+subSet.getSubSetId()+"', "
					+ "'"+subSet.getSubSetName()+"' )";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public boolean isExistDomain(Domain domain){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from domain where subSetId='"+domain.getDomainId()+"'";
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
	
	public Domain getDomain(String domainId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from domain where domainId='"+domainId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				Domain domain = new Domain(rs.getString("domainId"), rs.getString("domainName"));
				return domain;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}
	
	public void setDomain(Domain domain){
		String strSQL = null;
		if(this.isExistDomain(domain)){
			strSQL = "update domain set "
					+ "domainId='"+domain.getDomainId()+"', "
					+ "domainName='"+domain.getDomainName()+"' "
					+ "where domainId='"+domain.getDomainId()+"'";
		} else {
			strSQL = "insert into domain values("
					+ "'"+domain.getDomainId()+"', "
					+ "'"+domain.getDomainName()+"' )";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}

	public void clearFormTemplateDomain(FormTemplate formTemplate){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String strSQL = "delete form formTemplate_domain where formTemplateId="+formTemplate.getTemplateId()+"";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public void removeFormTemplate(FormTemplate formTemplate){
		
	}
}
