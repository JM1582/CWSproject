package com.model;
import java.util.*;

public class FormTemplate {
	//attributes
	protected int templateId=-1;
	protected String templateName;
	protected Map partsMap;
	
	//constructor
	public FormTemplate(String templateName){
		this.templateName = templateName;
		this.partsMap = new HashMap();
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
	public void setPartsMap(Map partsMap) {
		this.partsMap = partsMap;
	}
	public Map getPartsMap() {
		return this.partsMap;
	}
	public void addPart(OnePart part){
		this.partsMap.put(part.getId(), part);
	}
	public void removePart(OnePart part){
		this.partsMap.remove(part.getId());
	}
	
	public Map getAllDomainMap(){
		Map allDomainMap = new HashMap();
		Map partsMap = this.getPartsMap();
		Iterator partIt = partsMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partsMap.get(partId);
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
