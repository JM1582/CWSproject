package com.model;

import java.util.*;

public class ActionPlan {
	private int actionPlanId;
	private String CWSNumber;
	private CareProvider author;
	private Date date;
	private Map actionEntryMap;
	
	public ActionPlan(int actionPlanId, String CWSNumber){
		this.actionPlanId = actionPlanId;
		this.CWSNumber = CWSNumber;
		this.actionEntryMap = new HashMap();
	}
	
	public int getActionPlanId(){
		return this.actionPlanId;
	}
	public void setActionPlanId(int actionPlanId){
		this.actionPlanId = actionPlanId;
	}

	public String getCWSNumber(){
		return this.CWSNumber;
	}
	public void setCWSNumber(String CWSNumber){
		this.CWSNumber = CWSNumber;
	}

	public Map getActionEntryMap(){
		return this.actionEntryMap;
	}
	public void setActionEntryMap(Map actionEntryMap){
		this.actionEntryMap = actionEntryMap;
	}
	public void addActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.put(actionEntry.getActionEntryId(), actionEntry);
	}
	public void removeActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.remove(actionEntry.getActionEntryId());
	}
}
