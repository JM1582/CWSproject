package com.model;
import java.util.*;


public class PatientInfo {
	private int patientInfoId;
	private String CWSNumber;
	private int icon=-1;
	private CareProvider MRP;
	private FormTemplate formTemplate;
	private Map<String, CareProvider> careProviderMap;
	private Map<Integer, Document> documentMap;
	private Map<Integer, ActionPlan> actionPlanMap;
	
	public PatientInfo(String CWSNumber, int icon) {
		this.CWSNumber = CWSNumber;
		this.icon = icon;
		this.careProviderMap = new HashMap<String, CareProvider>();
		this.documentMap = new HashMap<Integer, Document>();
		this.actionPlanMap = new HashMap<Integer, ActionPlan>();
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
	//patientInfoId;
	public int getId(){
		return patientInfoId;
	}
	public void setId(int newPaitentId){
		this.patientInfoId = newPaitentId;
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
	public Map<String, CareProvider> getCareProviderMap(){
		return this.careProviderMap;
	}
	public void setCareProviderMap(Map<String, CareProvider> careProviderMap){
		this.careProviderMap = careProviderMap;
	}
	public void addCareProvider(CareProvider careProvider) {
		careProviderMap.put(careProvider.getUserName(), careProvider);
	}
	public void removeCareProvider(CareProvider careProvider) {
		careProviderMap.remove(careProvider.getUserName());
	}
	//documentMap add and remove
	public Map<Integer, Document> getDocumentMap(){
		return this.documentMap;
	}
	public void setDocumentMap(Map<Integer, Document> documentMap){
		this.documentMap = documentMap;
	}
	public void addDocument(Document document) {
		documentMap.put(document.getId(), document);
	}
	public void removeDocument(Document document) {
		careProviderMap.remove(document.getId());
	}
	//actionPlanMap add and remove
	public Map<Integer, ActionPlan> getActionPlanMap(){
		return this.actionPlanMap;
	}
	public void setActionPlanMap(Map<Integer, ActionPlan> actionPlanMap){
		this.actionPlanMap = actionPlanMap;
	}
	public void addActionPlan(ActionPlan actionPlan) {
		actionPlanMap.put(actionPlan.getId(), actionPlan);
	}
	public void removeActionPlan(ActionPlan actionPlan) {
		actionPlanMap.remove(actionPlan.getId());
	}
}
