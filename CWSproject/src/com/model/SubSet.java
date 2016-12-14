package com.model;

import java.util.*;

public class SubSet {
	private String subSetId;
	private String subSetName;
	private Map domainMap;
	
	public SubSet(String subSetId, String subSetName){
		this.subSetId = subSetId;
		this.subSetName = subSetName;
		this.domainMap = new HashMap();
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
	
	public Map getDomainMap(){
		return this.domainMap;
	}
	public void setDomainMap(Map domainMap){
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
	public boolean hasDomainValue(Map domainValueMap){
		Iterator domainValueIt = domainValueMap.keySet().iterator();
		while(domainValueIt.hasNext()){
			String domainId = (String) domainValueIt.next();
			if(this.isContainDomain(domainId)){
				String domainValue[] = (String[]) domainValueMap.get(domainId);
				if(domainValue!=null){
					boolean atLeastOne = false;
					for(int i=0;i<domainValue.length;i++){
						if(domainValue[i]!=null){
							atLeastOne = true;
						}
					}
					if(atLeastOne){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean hasDomainValueWithSummaryMap(Map summaryMap){
		Iterator summaryIt = summaryMap.keySet().iterator();
		while(summaryIt.hasNext()){
			String userName = (String) summaryIt.next();
			Document document = (Document) summaryMap.get(userName);
			if(this.hasDomainValue(document.getDomainValueMap())){
				return true;
			}
		}
		return false;
	}
}


