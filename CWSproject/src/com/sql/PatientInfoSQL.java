package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class PatientInfoSQL extends DataBase{
	public void createPatientInfo(PatientInfo patientInfo){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "insert into patientInfo values("
				+ "null,"
				+ "'"+patientInfo.getCWSNumber()+"', "
				+ Integer.toString(patientInfo.getIcon())+", "
				//+ Integer.toString(patientInfo.getMRP().getUserId())+", "
				+ Integer.toString(patientInfo.getFormTemplate().getTemplateId())+" "
				+ ")";
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public void setPatientInfo(PatientInfo patientInfo){
		try {
			st = conn.createStatement();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		String strSQL = "update patientInfo set "
				+ "patientId="+Integer.toString(patientInfo.getPatientId())+","
				+ "CWSNumber='"+patientInfo.getCWSNumber()+"',"
				+ "icon="+Integer.toString(patientInfo.getIcon())+","
				//+ "MRP='"+Integer.toString(patientInfo.getMRP().getUserId())+"',"
				+ "formTemplateId="+patientInfo.getFormTemplate().getTemplateId()+" "
				+ "where patientId="+Integer.toString(patientInfo.getPatientId());
		try{
			st.executeUpdate(strSQL);
		} catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
		}
	}
	
	public PatientInfo getPatientInfo(int patientId){
		try{
			st = conn.createStatement();
			String strSQL = "select * from document where patientId='"+patientId+"'";
			ResultSet rs = st.executeQuery(strSQL);
			rs.next();
			FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
			formTemplateSQL.connet();
			FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("forTemplateId"));
			
			UserSQL userSQL = new UserSQL();
			userSQL.connet();
			CareProvider MRP = userSQL.getUser(rs.getInt("MRP")).toCareProvider();

			PatientInfo patientInfo = new PatientInfo(rs.getString("CWSNumber"), rs.getInt("icon"));
			patientInfo.setPatientId(rs.getInt("patientId"));
			patientInfo.setMRP(MRP);
			patientInfo.setFormTemplate(formTemplate);
			System.out.println("Get patientInfo query success.");
			
			return  patientInfo;
		}catch (Exception e){
			System.out.println("Get patientInfo query fail.");
			e.printStackTrace();
			return null;
		}
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
				PatientInfo patientInfo = new PatientInfo(rs.getString("CWSNumber"), rs.getInt("icon"));
				patientInfo.setPatientId(rs.getInt("patientId"));
				
				// get patient MRP from database
				//UserSQL userSQL = new UserSQL();
				//userSQL.connet();
				//CareProvider MRP = userSQL.getUser(rs.getInt("MRP")).toCareProvider();
				//userSQL.disconnect();
				//patientInfo.setMRP(MRP);
				
				//get formTemplate form database
				FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
				formTemplateSQL.connet();
				FormTemplate formTemplate = formTemplateSQL.getFormTemplate(rs.getInt("formTemplateId"));
				formTemplateSQL.disconnect();
				patientInfo.setFormTemplate(formTemplate);

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
	
	// need to be removed
	public PatientInfo fakeSearchPatient(String CWSNumber) {
		FakeSQL fakeSQL = new FakeSQL();
		if (CWSNumber.equals("cws123")){
			return fakeSQL.getPatientInfo();
		}
		return null;
	}
	
}
