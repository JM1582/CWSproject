package com.model;
import java.util.*;

public class ActionEntry {
	private int actionEntryId;
	private Domain domain;
	private String cScore;
	private String fScore;
	private Map<Integer, Action> actionMap;
	//constructor
	public ActionEntry(int actionEntryId){
		this.actionEntryId = actionEntryId;
		this.actionMap = new HashMap<Integer, Action>();
	}
	//set and get
	//actionEntryId
	public int getId(){
		return this.actionEntryId;
	}
	public void setId(int actionEntryId){
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
	
	public Map<Integer, Action> getActionMap() {
		return this.actionMap;
	}
	public void setActionMap(Map<Integer, Action> actionMap) {
		this.actionMap = actionMap;
	}
	//addAction function
	public void addAction(Action action){
		this.actionMap.put(action.getId(), action);
	}
	//removeAction
	public void removeAction(Action action) {
		actionMap.remove(action.getId());
	}

}