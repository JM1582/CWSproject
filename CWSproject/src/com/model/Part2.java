package com.model;
import java.util.*;

public class Part2 extends OnePart{
	
	public Part2() {
		this.setId("defaultPart2");
		this.setName("CAPACITY AND PERFORMANCE");
		this.setDescription("Execution of a task or action by an individual and involvement in a life situation");
		this.setScalarName(new String[]{"Complete Impairment","Severe Impairment","Moderate Impairment","Mild Impairment", "No Problem", "Not applicable"}); 
		this.setScalarValue(new String[][]{{"4","3","2","1","0","9"},{"4","3","2","1","0","9"}});
		
		SubSet subSet;
		//
		subSet = new SubSet("d1","LEARNING AND APPLYING KNOWLEDGE");
		subSet.addDomain(new Domain("d110", "Watching"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d115", "Listening"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d140", "Learning to read"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d145", "Learning to write"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d150", "Learning to calculate (arithmetic)"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d170", "Solving problems"));
		this.addSubSet(subSet);
		//d2
		subSet = new SubSet("d2","GENERAL TASKS AND DEMANDS");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d210", "Undertaking a single task"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d220", "Undertaking multiple tasks"));
		this.addSubSet(subSet);
		//d3
		subSet = new SubSet("d3","COMMUNICATION");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d310", "Communicating with - reveiving - spoken messages"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d315", "Communicating with - receiving - non-verbal messages"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d330", "Speaking"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d335", "Producing non-verbal messages"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("d350", "Conversation"));
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
