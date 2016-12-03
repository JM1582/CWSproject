package com.model;
import java.util.*;


public class PatientInfo {
	private int patientId;
	private String CWSNumber;
	private int icon;
	private CareProvider MRP;
	private FormTemplate formTemplate;
	private Map careProviderMap;
	private Map documentMap;
	private Map actionPlanMap;
	
	public PatientInfo(String CWSNumber, int icon) {
		this.CWSNumber = CWSNumber;
		this.icon = icon;
		this.careProviderMap = new HashMap();
		this.documentMap = new HashMap();
		this.actionPlanMap = new HashMap();
	}
	
	//set and get-------------------------------------------!!!
	//CWSNumber
	public String getCWSNumber(){
		return this.CWSNumber;
	}
	public void setCWSNumber(String newCWSNumber){
		this.CWSNumber = newCWSNumber;
	}
	//icon
	public int getIcon(){
		return this.icon;
	}
	public void setIcon(int newIcon){
		this.icon = newIcon;
	}
	//patientId;
	public int getPatientId(){
		return patientId;
	}
	public void setPatientId(int newPaitentId){
		this.patientId = newPaitentId;
	}	
	//MRP
	public CareProvider getMRP(){
		return this.MRP; 
	}
	public void setMRP(CareProvider newMRP){
		this.MRP = newMRP;
	}
	//FormTemplate
	public FormTemplate getFormTemplate(){
		return this.formTemplate; 
	}
	public void setFormTemplate(FormTemplate formTemplate){
		this.formTemplate = formTemplate;
	}
	//CareProviders, using Map
	public void getCareProviders() {
		if (this.careProviderMap != null){
			Iterator it = careProviderMap.keySet().iterator();
			while (it.hasNext()){
				int careProviderKey = (int)it.next();
				CareProvider careProvider = (CareProvider)careProviderMap.get(careProviderKey);
			}
		}
	}
	public Map getCareProviderMap(){
		return this.careProviderMap;
	}
	public void setCareProviderMap(Map careProviderMap){
		this.careProviderMap = careProviderMap;
	}
	public void addCareProvider(CareProvider careProvider) {
		careProviderMap.put(careProvider.getUserName(), careProvider);
	}
	public void removeCareProvider(CareProvider careProvider) {
		careProviderMap.remove(careProvider.getUserName());
	}
	//documentMap add and remove
	public Map getDocumentMap(){
		return this.documentMap;
	}
	public void setDocumentMap(Map documentMap){
		this.documentMap = documentMap;
	}
	public void addDocument(Document document) {
		documentMap.put(document.getDocumentId(), document);
	}
	public void removeDocument(Document document) {
		careProviderMap.remove(document.getDocumentId());
	}
	//actionPlanMap add and remove
	public Map getActionPlanMap(){
		return this.actionPlanMap;
	}
	public void setActionPlanMap(Map actionPlanMap){
		this.actionPlanMap = actionPlanMap;
	}
	public void addActionPlan(ActionPlan actionPlan) {
		actionPlanMap.put(Integer.toString(actionPlan.getActionPlanId()), actionPlan);
	}
	public void removeActionPlan(ActionPlan actionPlan) {
		actionPlanMap.remove(Integer.toString(actionPlan.getActionPlanId()));
	}
}
