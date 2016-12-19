package com.model;
import java.util.*;


public class CareProvider extends User{
	private Map<String, PatientInfo> patientInfoMap;

	public CareProvider(String userName, String passWord) {
		super(userName, passWord);
	}
	
	public CareProvider getCareProvider() {
		return this;
	}
	
	public Map<String, PatientInfo> getPatientInfoMap(){
		return this.patientInfoMap;
	}
	public void setPatientInfoMap(Map<String, PatientInfo> patientInfoMap){
		this.patientInfoMap = patientInfoMap;
	}
	
	public Map<String, PatientInfo> getMyPatientInfoMap(){
		Map<String, PatientInfo> patientInfoMap = new HashMap<String, PatientInfo>();
		PatientInfo patientInfo = new PatientInfo("cws0001", 1);
		patientInfoMap.put(patientInfo.getCWSNumber(), patientInfo);
		return patientInfoMap;
	}
	
	public void inviteCareProvider(PatientInfo patientInfo, CareProvider careProvider){
		patientInfo.addCareProvider(careProvider);
	}

}
