package com.sql;

import java.sql.*;
import java.util.*;

import com.model.*;

public class PatientInfoSQL extends DataBase{
	public int getPatientIdByCWSNumber(String CWSNumber) throws Exception{
		int patientInfoId = -1;
		st = conn.createStatement();
		String strSQL = "select * from patientInfo where CWSNumber='"+CWSNumber+"'";
		try{
			ResultSet rs = st.executeQuery(strSQL);
			if(rs.next()){
				patientInfoId = rs.getInt("patientInfoId");
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return patientInfoId;
		
	}
	
	public boolean isExist(PatientInfo patientInfo) throws Exception{
		st = conn.createStatement();
		String strSQL = "select * from patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
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
	
	public PatientInfo setPatientInfo(PatientInfo patientInfo) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		this.setCareProviderMap(patientInfo);
		patientInfo.setPatientId(this.getPatientIdByCWSNumber(patientInfo.getCWSNumber()));
		return patientInfo;
	}
	
	public void setCareProviderMap(PatientInfo patientInfo) throws Exception{
		this.clearCareProviderForPatientInfo(patientInfo);
		Map<String, CareProvider> careProviderMap = patientInfo.getCareProviderMap();
		Iterator<String> careProviderIt = careProviderMap.keySet().iterator();
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
				throw e;
			}
		}
	}
	
	public PatientInfo getCareProviderMap(PatientInfo patientInfo) throws Exception {
		st = conn.createStatement();
		String strSQL = "select * from user_patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
		try {
			ResultSet rs = st.executeQuery(strSQL);
			UserSQL userSQL = new UserSQL();
			userSQL.connect();
			while(rs.next()){
				CareProvider careProvider = userSQL.getUserByUserName(rs.getString("userName")).toCareProvider();
				patientInfo.addCareProvider(careProvider);
			}
			userSQL.disconnect();
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return patientInfo;
	}

	public PatientInfo getDocumentMap(PatientInfo patientInfo) throws Exception{
		DocumentSQL documentSQL = new DocumentSQL();
		documentSQL.connect();
		patientInfo.setDocumentMap(documentSQL.getDocumentByCWSNumber(patientInfo.getCWSNumber()));
		documentSQL.disconnect();
		return patientInfo;
	}

	public PatientInfo getFormTemplate(PatientInfo patientInfo, int formTemplateId) throws Exception{
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		formTemplateSQL.connect();
		FormTemplate formTemplate = formTemplateSQL.getFormTemplate(formTemplateId);
		formTemplateSQL.disconnect();
		patientInfo.setFormTemplate(formTemplate);
		return patientInfo;
	}
	
	public PatientInfo getPatientInfo(int patientInfoId) throws Exception{
		st = conn.createStatement();
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
				
				patientInfo = this.getActionPlanMap(patientInfo);
				
				return  patientInfo;
			}
		}catch (Exception e){
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
		}
		return null;
	}

	private PatientInfo getActionPlanMap(PatientInfo patientInfo) throws Exception {
		ActionPlanSQL actionPlanSQL = new ActionPlanSQL();
		actionPlanSQL.connect();
		patientInfo.setActionPlanMap(actionPlanSQL.getActionPlanByCWSNumber(patientInfo.getCWSNumber()));
		actionPlanSQL.disconnect();
		return patientInfo;
	}

	public PatientInfo getPatientInfoByCWSNumber(String CWSNumber) throws Exception{
		st = conn.createStatement();
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
			throw e;
		}
		return  null;
	}
	
	//need to be changed or removed
	public PatientInfo searchPatient(String CWSNumber) throws Exception {
		if(CWSNumber==null){
			return null;
		}
		UserSQL userSQL = new UserSQL();
		userSQL.connect();
		
		
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
		ActionEntry actionEntry = new ActionEntry(0);
		Domain domain = new Domain("b110", "Consciousness");
		actionEntry.setDomain(domain);
		actionEntry.setCscore("3");
		actionEntry.setFscore("1");
		Action action = new Action(0);
		action.setCareProvider(userSQL.getUserByUserName("Tom").toCareProvider());
		action.setIntervention("Measurement");
		actionEntry.addAction(action);

		careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		action = new Action(1);
		action.setCareProvider(careProvider);
		action.setIntervention("Test");
		actionEntry.addAction(action);
		
		actionPlan.addActionEntry(actionEntry);
		
		patientInfo.addActionPlan(actionPlan);
		
		return patientInfo;
	}
	
	public void clearCareProviderForPatientInfo(PatientInfo patientInfo) throws SQLException{
		st = conn.createStatement();
		String strSQL = "delete from user_patientInfo where CWSNumber='"+patientInfo.getCWSNumber()+"'";
		try {
			st.executeUpdate(strSQL);
		} catch (SQLException e) {
			System.out.println("Fail: "+strSQL);
			e.printStackTrace();
			throw e;
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
