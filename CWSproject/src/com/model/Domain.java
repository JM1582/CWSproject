package com.model;

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

}
