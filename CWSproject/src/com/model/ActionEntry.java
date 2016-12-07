package com.model;
import java.util.*;

public class ActionEntry {
	private int actionEntryId;
	private Domain domain;
	private int cScore;
	private int fScore;
	private Map actionMap;
	//constructor
	public ActionEntry(int actionEntryId){
		this.actionEntryId = actionEntryId;
		this.actionMap = new HashMap();
		this.cScore = -100;
		this.fScore = -100;
	}
	//set and get
	//actionEntryId
	public int getActionEntryId(){
		return this.actionEntryId;
	}
	public void setActionEntryId(int actionEntryId){
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
	public int getCscore(){
		return this.cScore;
	}
	public void setCscore(int newCscore){
		this.cScore = newCscore;
	}
	//FScore
	public int getFscore(){
		return this.fScore;
	}
	public void setFscore(int newFscore){
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