package com.model;
import java.text.ParseException;
import java.util.*;


public class PatientInfo {
	private int patientInfoId = -1;
	private String CWSNumber;
	private int icon=-1;
	private CareProvider MRP;
	private FormTemplate formTemplate;
	private Map<String, CareProvider> careProviderMap;
	private Map<Integer, Document> documentMap;
	private Map<Integer, ActionPlan> actionPlanMap;
	private Map<String, Map<String,String[]>> summaryMap;
	
	public PatientInfo(String CWSNumber, int icon) {
		this.CWSNumber = CWSNumber;
		this.icon = icon;
		this.careProviderMap = new HashMap<String, CareProvider>();
		this.documentMap = new HashMap<Integer, Document>();
		this.actionPlanMap = new HashMap<Integer, ActionPlan>();
		this.summaryMap = new HashMap<String, Map<String,String[]>>();
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
	
	public Map<String, Map<String,String[]>> getSummaryMap(){
		this.summaryMap = new HashMap<String, Map<String,String[]>>();
		Iterator<String> careProviderIt = this.careProviderMap.keySet().iterator();
		while(careProviderIt.hasNext()){
			String userName = (String) careProviderIt.next();
			Map<String,String[]> domainValueMap = this.getSummaryDocumentOf(userName);
			if(domainValueMap!=null && domainValueMap.size()>0){
				this.summaryMap.put(userName, domainValueMap);
			}
		}
		return this.summaryMap;
	}

	public Map<String,String[]> getSummaryDocumentOf(String userName) {
		Map<String,String[]> domainValueMap = new HashMap<String,String[]>();
		
		Document rsDocument = this.newestSignedDocumentOf(userName);
		while(rsDocument!=null){
			domainValueMap = this.getUnsetDomainValueFrom(domainValueMap,rsDocument.getDomainValueMap());
			rsDocument = this.getOlderDocument(rsDocument);
		}
		return domainValueMap;
	}

	private Map<String, String[]> getUnsetDomainValueFrom(
			Map<String, String[]> tarMap, Map<String, String[]> srcMap) {
		Iterator<String> srcIt = srcMap.keySet().iterator();
		while(srcIt.hasNext()){
			String domainId = (String) srcIt.next();
			String[] srcDomainValue = srcMap.get(domainId);
			if(!tarMap.containsKey(domainId)){
				tarMap.put(domainId, srcDomainValue.clone());
			}else{
				String[] tarDomainValue = tarMap.get(domainId);
				for(int i=0;i<srcDomainValue.length;i++){
					if(srcDomainValue[i]!=null && !srcDomainValue[i].equals("")){
						if(tarDomainValue[i]==null || tarDomainValue[i].equals("")){
							System.out.println(this.documentMap.get(3).getDomainValueMap().get("b110")[0]);
							System.out.println(this.documentMap.get(3).getDomainValueMap().get("b114")[0]);
							tarDomainValue[i]=srcDomainValue[i];
							System.out.println(this.documentMap.get(3).getDomainValueMap().get("b110")[0]);
							System.out.println(this.documentMap.get(3).getDomainValueMap().get("b114")[0]);
						}
					}
				}
				tarMap.put(domainId, tarDomainValue);
			}
		}
		return tarMap;
	}

	private Document getOlderDocument(Document document) {
		Document rsDocument = null;
		Iterator<Integer> documentIt = this.documentMap.keySet().iterator();
		while(documentIt.hasNext()){
			int documentId = (Integer) documentIt.next();
			Document tmpDocument = this.documentMap.get(documentId);
			try {
				if(tmpDocument.getSign() && tmpDocument.getAuthor().getUserName().equals(document.getAuthor().getUserName()) && document.laterThan(tmpDocument)){
					if(rsDocument==null || document.laterThan(rsDocument)){
						rsDocument = tmpDocument;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return rsDocument;
	}

	private Document updateDomainValue(Document document) {
		Document newerDocument = this.getNewerDocument(document);
		if(newerDocument!=null){
			newerDocument.getUnsetDomainValueFrom(document);
			document = newerDocument;
			document = this.updateDomainValue(document);
		}
		return document;
	}

	private Document getNewerDocument(Document document) {
		Document rsDocument = null;
		Iterator<Integer> documentIt = this.documentMap.keySet().iterator();
		while(documentIt.hasNext()){
			int documentId = (Integer) documentIt.next();
			Document tmpDocument = this.documentMap.get(documentId);
			try {
				if(tmpDocument.getSign() && tmpDocument.getAuthor().getUserName().equals(document.getAuthor().getUserName()) && tmpDocument.laterThan(document)){
					if(rsDocument==null || rsDocument.laterThan(tmpDocument)){
						rsDocument = tmpDocument;
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return null;
			}
		}
		return rsDocument;
	}

	private Document oldestSignedDocumentOf(String userName) {
		Document rsDocument = null;
		Iterator<Integer> documentIt = this.documentMap.keySet().iterator();
		while(documentIt.hasNext()){
			int documentId = (Integer) documentIt.next();
			Document document = this.documentMap.get(documentId);
			if(document.getSign() && document.getAuthor().getUserName().equals(userName)){
				try {
					if(rsDocument==null || rsDocument.laterThan(document)){
						rsDocument = document;
					}
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return rsDocument;
	}


	private Document newestSignedDocumentOf(String userName) {
		Document rsDocument = null;
		Iterator<Integer> documentIt = this.documentMap.keySet().iterator();
		while(documentIt.hasNext()){
			int documentId = (Integer) documentIt.next();
			Document document = this.documentMap.get(documentId);
			if(document.getSign() && document.getAuthor().getUserName().equals(userName)){
				try {
					if(rsDocument==null || document.laterThan(rsDocument)){
						rsDocument = document;
					}
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return rsDocument;
	}
}
