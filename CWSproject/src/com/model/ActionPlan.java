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
	private Map<Integer, ActionEntry> actionEntryMap;
	
	public ActionPlan(String CWSNumber, CareProvider author){
		this.CWSNumber = CWSNumber;
		this.author = author;
		this.actionEntryMap = new HashMap<Integer, ActionEntry>();
	}
	
	public int getId(){
		return this.actionPlanId;
	}
	public void setId(int actionPlanId){
		this.actionPlanId = actionPlanId;
	}

	public String getCWSNumber(){
		return this.CWSNumber;
	}
	public void setCWSNumber(String CWSNumber){
		this.CWSNumber = CWSNumber;
	}

	public Map<Integer, ActionEntry> getActionEntryMap(){
		return this.actionEntryMap;
	}
	public void setActionEntryMap(Map<Integer, ActionEntry> actionEntryMap){
		this.actionEntryMap = actionEntryMap;
	}
	public void addActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.put(actionEntry.getId(), actionEntry);
	}
	public void removeActionEntry(ActionEntry actionEntry){
		this.actionEntryMap.remove(actionEntry.getId());
	}
	
	public String getDate(){
		return this.date;
	}
	public void setDate(String newDate){
		this.date = newDate;
	}
	public void setDateToday(){
		//Month need to be changed!!!!
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = new Date();
		String dateStr =  (String)sdf.format(date);
		this.date = dateStr;
	}
	public boolean laterThan(ActionPlan actionPlan) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		try {
			Date dateFromThis = sdf.parse(this.getDate());
			Date dateFormParam = sdf.parse(actionPlan.getDate());
			if(dateFromThis.after(dateFormParam)){
				return true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	public String getDateOnly() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy HH:mm:ss", Locale.ENGLISH);
		Date date = null;
		try {
			date = sdf.parse(this.date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
		sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH);
		String dateStr =  (String)sdf.format(date);
		return dateStr;
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
