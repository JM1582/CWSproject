package com.model;
import java.util.*;

public class ActionEntry {
	private String actionEntryId;
	private Domain domain;
	private String cScore;
	private String fScore;
	private Map actionMap;
	//constructor
	public ActionEntry(String actionEntryId){
		this.actionEntryId = actionEntryId;
		this.actionMap = new HashMap();
	}
	//set and get
	//actionEntryId
	public String getActionEntryId(){
		return this.actionEntryId;
	}
	public void setActionEntryId(String actionEntryId){
		this.actionEntryId = actionEntryId;
	}
	//domain
	public Domain getDomain(){
		return this.domain;
	}
	public void setDomain(Domain domain){
		this.domain = domain;
	}
	//CSore
	public String getCscore(){
		return this.cScore;
	}
	public void setCscore(String newCscore){
		this.cScore = newCscore;
	}
	//FScore
	public String getFscore(){
		return this.fScore;
	}
	public void setFscore(String newFscore){
		this.fScore = newFscore;
	}
	//ActionMap---------------------------------------------------------------?????
	
	public Map getActionMap() {
		return this.actionMap;
	}
	public void setActionMap(Map actionMap) {
		this.actionMap = actionMap;
	}
	//addAction function
	public void addAction(Action action){
		this.actionMap.put(action.getCareProvider().getUserName(), action);
	}
	//removeAction
	public void removeAction(Action action) {
		actionMap.remove(action.getCareProvider().getUserName());
	}

}