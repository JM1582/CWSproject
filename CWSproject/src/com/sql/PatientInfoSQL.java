package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class PatientInfoSQL extends DataBase{
	public int getPatientIdByCWSNumber(String CWSNumber){
		int patientInfoId = -1;
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from patientInfo where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				patientInfoId = rs.getInt("patientInfoId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return patientInfoId;
		
	}
	
	public boolean isExist(PatientInfo patientInfo){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
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
	
	public PatientInfo setPatientInfo(PatientInfo patientInfo){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = null;
		if(this.isExist(patientInfo)){
			strSQL = "update patientInfo set "
					+ "patientInfoId="+Integer.toString(patientInfo.getPatientId())+","
					+ "CWSNumber='"+patientInfo.getCWSNumber()+"',"
					+ "icon="+Integer.toString(patientInfo.getIcon())+","
					//+ "MRP='"+Integer.toString(patientInfo.getMRP().getUserId())+"',"
					+ "formTemplateId="+patientInfo.getFormTemplate().getId()+" "
					+ "where patientInfoId="+Integer.toString(patientInfo.getPatientId());
		} else {
			strSQL = "insert into patientInfo values("
					+ "null,"
					+ "'"+patientInfo.getCWSNumber()+"', "
					+ Integer.toString(patientInfo.getIcon())+", "
					//+ Integer.toString(patientInfo.getMRP().getUserId())+", "
					+ Integer.toString(patientInfo.getFormTemplate().getId())+" "
					+ ")";
		}
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		this.setCareProviderMap(patientInfo);
		patientInfo.setPatientId(this.getPatientIdByCWSNumber(patientInfo.getCWSNumber()));
		return patientInfo;
	}
	
	public void setCareProviderMap(PatientInfo patientInfo){
		this.clearCareProviderForPatientInfo(patientInfo);
		Map careProviderMap = patientInfo.getCareProviderMap();
		Iterator careProviderIt = careProviderMap.keySet().iterator();
		while(careProviderIt.hasNext()){
			String userName = (String) careProviderIt.next();
			CareProvider tmpCareProvider = (CareProvider) careProviderMap.get(userName);
			String strSQL = "insert into user_PatientInfo values("
					+ Integer.toString(tmpCareProvider.getId())+", "
					+ "'"+tmpCareProvider.getUserName()+"', "
					+ Integer.toString(patientInfo.getPatientId())+","
					+ "'"+patientInfo.getCWSNumber()+"' )";
			try{
				st.executeUpdate(strSQL);
			} catch (Exception e){
				System.out.println("Fail: "+strSQL);
				e.printStackTrace();
			}
		}
	}
	
	public PatientInfo getCareProviderMap(PatientInfo patientInfo) {
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from user_patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
		try {
			ResultSet rs = st.executeQuery(strSQL);
			UserSQL userSQL = new UserSQL();
			try {
				userSQL.connet();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			while(rs.next()){
				CareProvider careProvider = userSQL.getUserByUserName(rs.getString("userName")).toCareProvider();
				patientInfo.addCareProvider(careProvider);
			}
			userSQL.disconnect();
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return patientInfo;
	}

	public PatientInfo getDocumentMap(PatientInfo patientInfo){
		DocumentSQL documentSQL = new DocumentSQL();
		try {
			documentSQL.connet();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		patientInfo.setDocumentMap(documentSQL.getDocumentByCWSNumber(patientInfo.getCWSNumber()));
		documentSQL.disconnect();
		return patientInfo;
	}

	public PatientInfo getFormTemplate(PatientInfo patientInfo, int formTemplateId){
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		try {
			formTemplateSQL.connet();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		FormTemplate formTemplate = formTemplateSQL.getFormTemplate(formTemplateId);
		formTemplateSQL.disconnect();
		patientInfo.setFormTemplate(formTemplate);
		return patientInfo;
	}
	
	public PatientInfo getPatientInfo(int patientInfoId){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from patientInfo where patientInfoId="+patientInfoId;
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				String CWSNumber = rs.getString("CWSNumber");
				
				PatientInfo patientInfo = new PatientInfo(CWSNumber, rs.getInt("icon"));
				patientInfo.setPatientId(rs.getInt("patientInfoId"));
				
				patientInfo = this.getFormTemplate(patientInfo, rs.getInt("formTemplateId"));
				
				patientInfo = this.getCareProviderMap(patientInfo);
				//UserSQL userSQL = new UserSQL();
				//userSQL.connet();
				//CareProvider MRP = userSQL.getUser(rs.getInt("MRP")).toCareProvider();
				//userSQL.disconnect();
				//patientInfo.setMRP(MRP);
				
				patientInfo = this.getDocumentMap(patientInfo);
				
				return  patientInfo;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
		return null;
	}

	public PatientInfo getPatientInfoByCWSNumber(String CWSNumber){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "select * from patientInfo where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				PatientInfo patientInfo = this.getPatientInfo(rs.getInt("patientInfoId"));

				return  patientInfo;
			}
		}catch (Exception e){
			System.out.println("Get patientInfo query fail.");
			e.printStackTrace();
		}
		return  null;
	}
	
	//need to be changed or removed
	public PatientInfo searchPatient(String CWSNumber) {
		if(CWSNumber==null){
			return null;
		}
		UserSQL userSQL = new UserSQL();
		try {
			userSQL.connet();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//========================================================
		
		FormTemplate formTemplate = new FormTemplate("default");
		Part1 part1 = new Part1();
		Part2 part2 = new Part2();
		Part3 part3 = new Part3();
		formTemplate.addPart(part1);
		formTemplate.addPart(part2);
		formTemplate.addPart(part3);
		
		//========================================================

		PatientInfo patientInfo = new PatientInfo("cws123", 0);

		CareProvider careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		careProvider = userSQL.getUserByUserName("John").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		//formTemplate = formTemplateSQL.getFormTemplateByName(1);
		patientInfo.setFormTemplate(formTemplate);
		
		ActionPlan actionPlan = new ActionPlan(patientInfo.getCWSNumber(), userSQL.getUserByUserName("Tom").toCareProvider());
		ActionEntry actionEntry = new ActionEntry("0");
		Domain domain = new Domain("b110", "Consciousness");
		actionEntry.setDomain(domain);
		actionEntry.setCscore("3");
		actionEntry.setFscore("1");
		Action action = new Action("0");
		action.setCareProvider(userSQL.getUserByUserName("Tom").toCareProvider());
		action.setIntervention("Measurement");
		actionEntry.addAction(action);

		careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		action = new Action("1");
		action.setCareProvider(careProvider);
		action.setIntervention("Test");
		actionEntry.addAction(action);
		
		actionPlan.addActionEntry(actionEntry);
		
		patientInfo.addActionPlan(actionPlan);
		
		return patientInfo;
	}
	
	public void clearCareProviderForPatientInfo(PatientInfo patientInfo){
		try {
			st = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String strSQL = "delete from user_patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	// need to be removed
	public PatientInfo fakeSearchPatient(String CWSNumber) {
		FakeSQL fakeSQL = new FakeSQL();
		if (CWSNumber.equals("cws123")){
			return fakeSQL.getPatientInfo();
		}
		return null;
	}
	
}
