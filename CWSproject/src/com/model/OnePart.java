package com.model;

import java.util.*;

public class OnePart {
	protected String partId;
	protected String partName;
	protected String partDescription;
	protected String scalarName[];
	protected String scalarValue[][];
	protected Map<String, SubSet> subSetMap = new HashMap<String, SubSet>();
	
	public void Part(String partId, String partName){
		this.partId = partId;
		this.partName = partName;
		this.subSetMap = new HashMap<String, SubSet>();
	}
	
	public String getId(){
		return this.partId;
	}
	public void setId(String partId){
		this.partId = partId;
	}

	public String getName(){
		return this.partName;
	}
	public void setName(String partName){
		this.partName = partName;
	}

	public String getDescription(){
		return this.partDescription;
	}
	public void setDescription(String partDescription){
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
	public void setSubSetMap(Map<String, SubSet> subSetMap) {
		this.subSetMap = subSetMap;
	}
	public Map<String, SubSet> getSubSetMap() {
		return this.subSetMap;
	}
	public void addSubSet(SubSet subSet){
		this.subSetMap.put(subSet.getId(), subSet);
	}
	public void removeSubSet(SubSet subSet){
		this.subSetMap.remove(subSet.getId());
	}
	
	public boolean isContainDomain(String domainId){
		Iterator<String> subSetIt = this.subSetMap.keySet().iterator();
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
		Iterator<String> subSetIt = this.subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String) subSetIt.next();
			SubSet subSet = (SubSet) this.subSetMap.get(subSetId);
			if(subSet.hasDomainValue(domainValueMap)){
				return true;
			}
		}
		return false;
	}
	public boolean hasDomainValueWithSummaryMap(Map<Integer, Document> summaryMap){
		Iterator<Integer> summaryIt = summaryMap.keySet().iterator();
		if(summaryIt.hasNext()){
			int documentId = (Integer) summaryIt.next();
			Document document = (Document) summaryMap.get(documentId);
			if(this.hasDomainValue(document.getDomainValueMap())){
				return true;
			}
		}
		return false;
	}
}
