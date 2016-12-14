package com.model;

public class Action {
	private int actionId;
	private String intervention;
	private CareProvider careProvider;
	private String comment;

	public Action(int actionId){
	//----------------------------------???创建时？constructor，必须属性？
		this.actionId = actionId;
		this.intervention = new String();
		this.comment = new String();
	}
	
	//set and get
	//id
	public int getId(){
		return this.actionId;
	}
	public void setId(int actionId){
		this.actionId = actionId;
	}
	//intervention
	public String getIntervention(){
		return this.intervention;
	}
	public void setIntervention(String newIntervention){
		this.intervention = newIntervention;
	}
	//careProvider
	public CareProvider getCareProvider(){
		return this.careProvider;
	}
	public void setCareProvider(CareProvider newCareProvider){
		this.careProvider = newCareProvider;
	}
	//comment
	public String getComment(){
		return this.comment;
	}
	public void setComment(String newComment){
		this.comment = newComment;
	}
}	
