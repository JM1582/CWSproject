package com.model;

import java.util.*;

public class OnePart {
	protected String partId;
	protected String partName;
	protected String partDescription;
	protected String scalarName[];
	protected String scalarValue[][];
	protected Map subSetMap = new HashMap();
	
	public void Part(String partId, String partName){
		this.partId = partId;
		this.partName = partName;
		this.subSetMap = new HashMap();
	}
	
	public String getPartId(){
		return this.partId;
	}
	public void setPartId(String partId){
		this.partId = partId;
	}

	public String getPartName(){
		return this.partName;
	}
	public void setPartName(String partName){
		this.partName = partName;
	}

	public String getPartDescription(){
		return this.partDescription;
	}
	public void setPartDescription(String partDescription){
		this.partDescription = partDescription;
	}

	public void setScalarName(String[] newScalarName){
		this.scalarName = newScalarName;
	}
	
	public String[] getScalarName(){
		return this.scalarName;
	}
	
	public void setScalarValue(String[][] newScalarValue){
		this.scalarValue = newScalarValue;
	}
	
	public String[][] getScalarValue(){
		return this.scalarValue;
	}
	
	//subSet map
	public void setSubSetMap(Map subSetMap) {
		this.subSetMap = subSetMap;
	}
	public Map getSubSetMap() {
		return this.subSetMap;
	}
	public void addSubSet(SubSet subSet){
		this.subSetMap.put(subSet.getSubSetId(), subSet);
	}
	public void removeSubSet(SubSet subSet){
		this.subSetMap.remove(subSet.getSubSetId());
	}
	
	public boolean isContainDomain(String domainId){
		Iterator subSetIt = this.subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String) subSetIt.next();
			SubSet subSet = (SubSet) this.subSetMap.get(subSetId);
			if(subSet.isContainDomain(domainId)){
				return true;
			}
		}
		return false;
	}
	public boolean hasDomainValue(Map domainValueMap){
		Iterator subSetIt = this.subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String) subSetIt.next();
			SubSet subSet = (SubSet) this.subSetMap.get(subSetId);
			if(subSet.hasDomainValue(domainValueMap)){
				return true;
			}
		}
		return false;
	}
	public boolean hasDomainValueWithSummaryMap(Map summaryMap){
		Iterator subSetIt = this.subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String) subSetIt.next();
			SubSet subSet = (SubSet) this.subSetMap.get(subSetId);
			if(subSet.hasDomainValueWithSummaryMap(summaryMap)){
				return true;
			}
		}
		return false;
	}
}
