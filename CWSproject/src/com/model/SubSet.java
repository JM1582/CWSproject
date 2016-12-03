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
	public String getSubSetId(){
		return this.subSetId;
	}
	public void setSubSetId(String subSetId){
		this.subSetId = subSetId;
	}
	
	//subSetName
	public String getSubSetName(){
		return this.subSetName;
	}
	public void setSubSetName(String newSubSetName){
		this.subSetName = newSubSetName;
	}
	
	public Map getDomainMap(){
		return this.domainMap;
	}
	public void setDomainMap(Map domainMap){
		this.domainMap = domainMap;
	}
	public void addDomain(Domain domain){
		this.domainMap.put(domain.getDomainId(), domain);
	}
	public void removeDomain(Domain domain){
		this.domainMap.remove(domain.getDomainId());
	}
}


