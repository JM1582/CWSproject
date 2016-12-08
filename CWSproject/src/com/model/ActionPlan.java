package com.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ActionPlan {
	private int actionPlanId=-1;
	private String CWSNumber;
	private CareProvider author;
	private String date;
	private boolean sign=false;
	private Map<String, ActionEntry> actionEntryMap;
	
	public ActionPlan(String CWSNumber, CareProvider author){
		this.CWSNumber = CWSNumber;
		this.author = author;
		this.actionEntryMap = new HashMap<String, ActionEntry>();
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

	public Map<String, ActionEntry> getActionEntryMap(){
		return this.actionEntryMap;
	}
	public void setActionEntryMap(Map<String, ActionEntry> actionEntryMap){
		this.actionEntryMap = actionEntryMap;
	}
	public void addActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.put(actionEntry.getActionEntryId(), actionEntry);
	}
	public void removeActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.remove(actionEntry.getActionEntryId());
	}
	
	public String getDate(){
		return this.date;
	}
	public void setDate(String newDate){
		this.date = newDate;
	}
	public void setDateToday(){
		//Month need to be changed!!!!
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		Date date = new Date();
		this.date = (String)sdf.format(date);
	}
	public boolean laterThan(ActionPlan actionPlan){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		try {
			Date dateFromThis = sdf.parse(this.getDate());
			Date dateFormParam = sdf.parse(actionPlan.getDate());
			if(dateFromThis.after(dateFormParam)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getDateOnly(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm:ss");
		Date date = null;
		try {
			date = sdf.parse(this.date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sdf = new SimpleDateFormat("dd/mm/yyyy");
		return (String)sdf.format(date);
	}
	
	public CareProvider getAuthor(){
		return this.author;
	}
	public void setAuthor(CareProvider careProvider){
		this.author = careProvider;
	}
	public boolean getSign(){
		return this.sign;
	}
	public void setSign(boolean sign){
		this.sign = sign;
	}
}
