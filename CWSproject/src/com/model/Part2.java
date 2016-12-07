package com.model;
import java.util.*;

public class Part2 extends OnePart{
	
	public Part2() {
		this.setPartId("defaultPart2");
		this.setPartName("CAPACITY AND PERFORMANCE");
		this.setPartDescription("Execution of a task or action by an individual and involvement in a life situation");
		this.setScalarName(new String[]{"Complete Impairment","Severe Impairment","Moderate Impairment","Mild Impairment", "No Problem", "Not applicable"}); 
		this.setScalarValue(new String[][]{{"4","3","2","1","0","9"},{"4","3","2","1","0","9"}});
		
		SubSet subSet;
		//
		subSet = new SubSet("d1","LEARNING AND APPLYING KNOWLEDGE");
		subSet.addDomain(new Domain("d110", "Watching"));
		this.addSubSet(subSet);
		//d2
		subSet = new SubSet("d2","GENERAL TASKS AND DEMANDS");
		this.addSubSet(subSet);
		//d3
		subSet = new SubSet("d3","COMMUNICATION");
		this.addSubSet(subSet);
		//d4
		subSet = new SubSet("d4","MOBILITY");
		this.addSubSet(subSet);
		//d5
		subSet = new SubSet("d5","SELF CARE");
		this.addSubSet(subSet);		
		//d6
		subSet = new SubSet("d6","DOMESTIC LIFE");
		this.addSubSet(subSet);		
		//d7
		subSet = new SubSet("d7","INTERPERSONAL INTERACTIONS AND RELATIONSHIPS");
		this.addSubSet(subSet);		
		//d8
		subSet = new SubSet("d8","MAJOR LIFE AREAS");
		this.addSubSet(subSet);
		//d9
		subSet = new SubSet("d8","COMMUNITY, SOCIAL AND CIVIC LIFE");
		this.addSubSet(subSet);
	}
	/*
	public void setScore(String domainId, int[] score){
		Domain domain = (Domain)this.subSet.get(domainId);
		domain.setScore(score);
		this.subSet.replace(domainId, domain);
	}
	*/
}
