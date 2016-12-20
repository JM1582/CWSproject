package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class FormTemplateSQL extends DataBase{
	public int getFormTemplateIdByFormTemplateName(String formTemplateName) throws Exception{
		int formTemplateId = -1;
		st = conn.createStatement();
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				formTemplateId = rs.getInt("formTemplateId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return formTemplateId;
		
	}
	
	public boolean isExist(FormTemplate formTemplate) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplate.getName()+"'";
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
	
	//need to be removed
	public int searchFormTemplateId(String formTemplateName) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				return rs.getInt("formTemplateId");
			}
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return -1;
		
	}
	
	public FormTemplate setFormTemplate(FormTemplate formTemplate) throws Exception{
		st = conn.createStatement();
		String strSQL = null;
		if(this.isExist(formTemplate)){
			formTemplate.setId(this.getFormTemplateIdByFormTemplateName(formTemplate.getName()));
			strSQL = "update formTemplate set "
					+ "formTemplateId="+Integer.toString(formTemplate.getId())+","
					+ "formTemplateName='"+formTemplate.getName()+"' "
					+ "where formTemplateId="+Integer.toString(formTemplate.getId());
			
		} else {
			strSQL = "insert into formTemplate values("
					+ "null,"
					+ "'"+formTemplate.getName()+"')";
		}
		
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		
		this.clearFormTemplateDomain(formTemplate);
		
		Map<String, OnePart> partMap = formTemplate.getPartsMap();
		Iterator<String> partIt = partMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partMap.get(partId);
			this.setPart(part);
			Map<String, SubSet> subSetMap = part.getSubSetMap();
			Iterator<String> subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				this.setSubSet(subSet);
				Map<String, Domain> domainMap = subSet.getDomainMap();
				Iterator<String> domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					this.setDomain(domain);
					strSQL = "insert into formTemplate_domain values("
							+ "'"+domain.getId()+"',"
							+ "'"+subSet.getId()+"',"
							+ "'"+part.getId()+"',"
							+ ""+formTemplate.getId()+")";
					try{
						st.executeUpdate(strSQL);
					} catch (Exception e){
						System.out.println("Fail: "+strSQL);
						e.printStackTrace();
						throw e;
					}
				}
			}
		}
		formTemplate.setId(this.getFormTemplateIdByFormTemplateName(formTemplate.getName()));
		return formTemplate;
	}
	
	public FormTemplate getFormTemplate(int formTemplateId) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from formTemplate where formTemplateId="+Integer.toString(formTemplateId);
		ResultSet rs = null;
		try{
			rs = st.executeQuery(strSQL);
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		FormTemplate formTemplate = null;
		try {
			if(rs.next()){ 
				formTemplate= new FormTemplate(rs.getString("formTemplateName"));
				formTemplate.setId(rs.getInt("formTemplateId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		formTemplate.setPartsMap(this.getPartMap(formTemplateId));
		return formTemplate;
	}

	public FormTemplate getFormTemplateByName(String formTemplateName) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from formTemplate where formTemplateName='"+formTemplateName+"'";
		ResultSet rs = null;
		try{
			rs = st.executeQuery(strSQL);
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		FormTemplate formTemplate = null;
		try {
			if(rs.next()){ 
				formTemplate= this.getFormTemplate(rs.getInt("formTemplateId"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return formTemplate;
	}
	
	public Map<String, OnePart> getPartMap(int formTemplateId) throws Exception{
		Map<String, OnePart> partMap = new HashMap<String, OnePart>();
		st = conn.createStatement();
		String strSQL = "select * from formTemplate_domain where formTemplateId="+Integer.toString(formTemplateId);
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
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
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return partMap;
	}
	
	private Map<String, SubSet> getSubSetMap(String partId, int formTemplateId) throws Exception {
		Map<String, SubSet> subSetMap = new HashMap<String, SubSet>();
		st = conn.createStatement();
		String strSQL = "select * from formTemplate_domain where "
				+ "formTemplateId="+Integer.toString(formTemplateId)+" and "
				+ "partId='"+partId+"'";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
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
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return subSetMap;
	}

	private Map<String, Domain> getDomainMap(String subSetId, String partId, int formTemplateId) throws Exception {
		Map<String, Domain> domaintMap = new HashMap<String, Domain>();
		st = conn.createStatement();
		String strSQL = "select * from formTemplate_domain where "
				+ "formTemplateId="+Integer.toString(formTemplateId)+" and "
				+ "partId='"+partId+"' and "
				+ "subSetId='"+subSetId+"'";
		ResultSet rs = null;
		try {
			rs = st.executeQuery(strSQL);
		} catch (SQLException e1) {
			System.out.println("Fail: "+strSQL);
			e1.printStackTrace();
			throw e1;
		}
		try{
			while(rs.next()){
				String domainId = rs.getString("domainId");
				if(!domaintMap.containsKey(domainId)){
					Domain domain=this.getDomain(domainId);
					domaintMap.put(domainId, domain);
				}
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return domaintMap;
	}
	
	public boolean isExistOnePart(OnePart part) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from onePart where partId='"+part.getId()+"'";
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
	
	public OnePart getPart(String partId) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from onePart where partId='"+partId+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				OnePart part = new OnePart();
				part.setId(partId);
				part.setName(rs.getString("partName"));
				part.setDescription(rs.getString("partDescription"));
				part = this.getPartScalar(part);
				return part;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return null;
	}
	
	private OnePart getPartScalar(OnePart part) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from partScalar where partId='"+part.getId()+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			int size = 0;
			int valueAmount = 1;
			if (rs.last()) {
				if(rs.getString("scalarValue2")!=null){
					valueAmount = 2;
				}
				size = rs.getRow();
				rs.beforeFirst();
			}
			
			String scalarName[] = new String[size];
			String scalarValue[][] = new String[valueAmount][size];
			int count = 0;
			
			while(rs.next()){
				scalarName[count]=rs.getString("scalarName");
				for(int i=0;i<valueAmount;i++){
					scalarValue[i][count]=rs.getString("scalarValue"+Integer.toString(i+1));
				}
				count++;
			}
			part.setScalarName(scalarName);
			part.setScalarValue(scalarValue);
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return part;
	}

	public void setPart(OnePart part) throws Exception{
		String strSQL = null;
		if(this.isExistOnePart(part)){
			strSQL = "update onePart set "
					+ "partId='"+part.getId()+"', "
					+ "partName='"+part.getName()+"', "
					+ "partDescription='"+part.getDescription()+"' "
					+ "where partId='"+part.getId()+"'";
		} else {
			strSQL = "insert into onePart values("
					+ "'"+part.getId()+"', "
					+ "'"+part.getName()+"', "
					+ "'"+part.getDescription()+"')";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		this.setPartScalar(part);
	}
	
	private void setPartScalar(OnePart part) throws SQLException {
		this.clearPartScalar(part);
		
		Statement st = conn.createStatement();
		for(int i=0;i<part.getScalarName().length;i++){
			String strSQL = null;
			if(part.getScalarValue().length==1){
				strSQL = "insert into partScalar values("
						+ "'"+part.getId()+"', "
						+ "'"+part.getScalarName()[i]+"', "
						+ "'"+part.getScalarValue()[0][i]+"', "
						+ "null )";
				
			} else if(part.getScalarValue().length==2) {
				strSQL = "insert into partScalar values("
						+ "'"+part.getId()+"', "
						+ "'"+part.getScalarName()[i]+"', "
						+ "'"+part.getScalarValue()[0][i]+"', "
						+ "'"+part.getScalarValue()[1][i]+"' )";
			}
			try{
				st.executeUpdate(strSQL);
			} catch (Exception e){
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
				throw e;
			}
		}
	}

	private void clearPartScalar(OnePart part) throws SQLException {
		st = conn.createStatement();
		String strSQL = "delete from partScalar where partId='"+part.getId()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}

	public boolean isExistSubSet(SubSet subSet) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from subSet where subSetId='"+subSet.getId()+"'";
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
	
	public SubSet getSubSet(String subSetId) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		return null;
	}
	
	public void setSubSet(SubSet subSet) throws Exception{
		String strSQL = null;
		if(this.isExistSubSet(subSet)){
			strSQL = "update subSet set "
					+ "subSetId='"+subSet.getId()+"', "
					+ "subSetName='"+subSet.getName()+"' "
					+ "where subSetId='"+subSet.getId()+"'";
		} else {
			strSQL = "insert into subSet values("
					+ "'"+subSet.getId()+"', "
					+ "'"+subSet.getName()+"' )";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public boolean isExistDomain(Domain domain) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from domain where domainId='"+domain.getId()+"'";
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
	
	public Domain getDomain(String domainId) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		return null;
	}
	
	public void setDomain(Domain domain) throws Exception{
		String strSQL = null;
		if(this.isExistDomain(domain)){
			strSQL = "update domain set "
					+ "domainId='"+domain.getId()+"', "
					+ "domainName='"+domain.getName()+"' "
					+ "where domainId='"+domain.getId()+"'";
		} else {
			strSQL = "insert into domain values("
					+ "'"+domain.getId()+"', "
					+ "'"+domain.getName()+"' )";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}

	public void clearFormTemplateDomain(FormTemplate formTemplate) throws SQLException{
		st = conn.createStatement();
		String strSQL = "delete from formTemplate_domain where formTemplateId="+formTemplate.getId()+"";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
	}
	
	public void removeFormTemplate(FormTemplate formTemplate){
		
	}

	public Map<Integer, FormTemplate> getAllFormTemplate() throws Exception {
		Map<Integer, FormTemplate> formTemplateMap = new HashMap();
		st = conn.createStatement();
		String strSQL = "select * from formTemplate";
		ResultSet rs = null;
		try{
			rs = st.executeQuery(strSQL);
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		try {
			while(rs.next()){ 
				int formTemplateId = rs.getInt("formTemplateId");
				FormTemplate formTemplate = this.getFormTemplate(formTemplateId);
				formTemplateMap.put(formTemplate.getId(), formTemplate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return formTemplateMap;
	}
}
