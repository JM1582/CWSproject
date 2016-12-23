package com.model;

import java.util.*;

public class SubSet {
	private String subSetId;
	private String subSetName;
	private Map<String, Domain> domainMap;
	
	public SubSet(String subSetId, String subSetName){
		this.subSetId = subSetId;
		this.subSetName = subSetName;
		this.domainMap = new HashMap<String, Domain>();
	}
	//sebSetId
	public String getId(){
		return this.subSetId;
	}
	public void setId(String subSetId){
		this.subSetId = subSetId;
	}
	
	//subSetName
	public String getName(){
		return this.subSetName;
	}
	public void setName(String newSubSetName){
		this.subSetName = newSubSetName;
	}
	
	public Map<String, Domain> getDomainMap(){
		return this.domainMap;
	}
	public void setDomainMap(Map<String, Domain> domainMap){
		this.domainMap = domainMap;
	}
	public void addDomain(Domain domain){
		this.domainMap.put(domain.getId(), domain);
	}
	public void removeDomain(Domain domain){
		this.domainMap.remove(domain.getId());
	}
	
	public boolean isContainDomain(String domainId){
		if(this.domainMap.containsKey(domainId)){
			return true;
		}
		return false;
	}
	public boolean hasDomainValue(Map<String, String[]> domainValueMap){
		Map<String, Domain> domainMap = this.domainMap;
		Iterator<String> domainIt = domainMap.keySet().iterator();
		while(domainIt.hasNext()){
			String domainId = (String) domainIt.next();
			Domain domain = (Domain) domainMap.get(domainId);
			
			if(domain.hasDomainValue(domainValueMap)){
				return true;
			}
		}
		return false;
	}
	
	public boolean hasDomainValueWithSummaryMap(Map<String, Map<String, String[]>> summaryMap){
		Iterator<String> summaryIt = summaryMap.keySet().iterator();
		while(summaryIt.hasNext()){
			String userName = (String) summaryIt.next();
			Map<String, String[]> domainValueMap = summaryMap.get(userName);
			if(this.hasDomainValue(domainValueMap)){
				return true;
			}
		}
		return false;
	}
}


