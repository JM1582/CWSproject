package com.model;
import java.util.*;

public class FormTemplate {
	//attributes
	protected int templateId=-1;
	protected String templateName;
	protected Map<String, OnePart> partsMap;
	
	//constructor
	public FormTemplate(String templateName){
		this.templateName = templateName;
		this.partsMap = new HashMap<String, OnePart>();
	}
	
	public String getName(){
		return this.templateName;
	}
	public void setName(String templateName){
		this.templateName = templateName;
	}

	public int getId(){
		return this.templateId;
	}
	public void setId(int templateId){
		this.templateId = templateId;
	}
	
	//subSet map
	public void setPartsMap(Map<String, OnePart> partsMap) {
		this.partsMap = partsMap;
	}
	public Map<String, OnePart> getPartsMap() {
		return this.partsMap;
	}
	public void addPart(OnePart part){
		this.partsMap.put(part.getId(), part);
	}
	public void removePart(OnePart part){
		this.partsMap.remove(part.getId());
	}
	
	public Map<String, Domain> getAllDomainMap(){
		Map<String, Domain> allDomainMap = new HashMap<String, Domain>();
		Map<String, OnePart> partsMap = this.getPartsMap();
		Iterator<String> partIt = partsMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = partIt.next();
			OnePart part = partsMap.get(partId);
			Map subSetMap = part.getSubSetMap();
			Iterator subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				Map domainMap = subSet.getDomainMap();
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					allDomainMap.put(domainId, domain);
				}
			}
		}
		return allDomainMap;
	}
	
	
}
