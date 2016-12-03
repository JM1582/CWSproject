package com.model;
import java.util.*;

import com.sql.*;


public class CareProvider extends User{
	private Map patientInfoMap;

	public CareProvider(String userName, String passWord) {
		super(userName, passWord);
		// TODO Auto-generated constructor stub
	}
	
	public CareProvider getCareProvider() {
		return this;
	}
	
	public Map getPatientInfoMap(){
		return this.patientInfoMap;
	}
	public void setPatientInfoMap(Map patientInfoMap){
		this.patientInfoMap = patientInfoMap;
	}
	
	public Map getMyPatientInfoMap(){
		Map patientInfoMap = new HashMap();
		PatientInfo patientInfo = new PatientInfo("cws0001", 1);
		patientInfoMap.put(patientInfo.getCWSNumber(), patientInfo);
		return patientInfoMap;
	}
	
	public void inviteCareProvider(PatientInfo patientInfo, CareProvider careProvider){
		patientInfo.addCareProvider(careProvider);
	}

}
