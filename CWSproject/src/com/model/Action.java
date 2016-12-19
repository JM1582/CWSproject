package com.model;

public class Action {
	private int actionId;
	private Intervention intervention;
	private CareProvider careProvider;
	private String comment;

	public Action(int actionId){
	//----------------------------------???创建时？constructor，必须属性？
		this.actionId = actionId;
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
	public Intervention getIntervention(){
		return this.intervention;
	}
	public void setIntervention(Intervention intervention){
		this.intervention = intervention;
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
