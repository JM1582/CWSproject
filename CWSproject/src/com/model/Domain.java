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
	public String getDomainId() {
		return this.domainId;
	}
	public void setDomainId(String newDomainId){
		this.domainId = newDomainId;
	}
	//domainName
	public String getDomainName(){
		return this.domainName;
	}	
	public void setDomainName(String newDomainName){
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
	public boolean hasDomainValueWithSummaryMap(Map<String, Document> summaryMap){
		Iterator<String> summaryIt = summaryMap.keySet().iterator();
		while(summaryIt.hasNext()){
			String userName = (String) summaryIt.next();
			Document document = (Document) summaryMap.get(userName);
			String domainValue[] = (String[]) document.getDomainValueMap().get(domainId);
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
		return false;
	}

}
