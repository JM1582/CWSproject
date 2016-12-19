package com.model;

import java.util.Iterator;
import java.util.Map;

public class Domain {
	private String domainId;
	private String domainName;
	private int score[];
	private String comment;
	//constructor
	public Domain(String domainId, String domainName){
		this.domainId = domainId;
		this.domainName = domainName;
		this.score = new int[2];
		this.comment = null;
	}
	//functions
	//domainId
	public String getId() {
		return this.domainId;
	}
	public void setId(String newDomainId){
		this.domainId = newDomainId;
	}
	//domainName
	public String getName(){
		return this.domainName;
	}	
	public void setName(String newDomainName){
		this.domainName = newDomainName;
	}
	//score
	public int[] getScore(){
		return this.score;
	}	
	public void setScore(int score[]){
		this.score = score;
	}
	//string comment
	public String getComment(){
		return this.comment;
	}
	public void setComment(String newComment){
		this.comment = newComment;
	}
	
	public boolean hasDomainValue(Map<String, String[]> domainValueMap){
		if(domainValueMap.containsKey(this.domainId)){
			String domainValue[] = (String[]) domainValueMap.get(this.domainId);
			for(int i=0;i<domainValue.length;i++){
				if(domainValue[i]!=null){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean hasDomainValueWithSummaryMap(Map<Integer, Document> summaryMap){
		Iterator<Integer> summaryIt = summaryMap.keySet().iterator();
		while(summaryIt.hasNext()){
			int documentId = (Integer) summaryIt.next();
			Document document = (Document) summaryMap.get(documentId);
			if(this.hasDomainValue(document.getDomainValueMap())){
				return true;
			}
		}
		return false;
	}

}
