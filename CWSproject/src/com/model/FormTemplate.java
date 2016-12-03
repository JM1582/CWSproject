package com.model;
import java.util.*;

public class FormTemplate {
	//attributes
	protected int templateId;
	protected String templateName;
	protected Map partsMap;
	
	//constructor
	public FormTemplate(String templateName){
		this.templateName = templateName;
		this.partsMap = new HashMap();
	}
	
	public String getTemplateName(){
		return this.templateName;
	}
	public void setTemplateName(String templateName){
		this.templateName = templateName;
	}

	public int getTemplateId(){
		return this.templateId;
	}
	public void setTemplateId(int templateId){
		this.templateId = templateId;
	}
	
	//subSet map
	public void setPartsMap(Map partsMap) {
		this.partsMap = partsMap;
	}
	public Map getPartsMap() {
		return this.partsMap;
	}
	public void addPart(OnePart part){
		this.partsMap.put(part.getPartId(), part);
	}
	public void removePart(OnePart part){
		this.partsMap.remove(part.getPartId());
	}

	
	
	
}
