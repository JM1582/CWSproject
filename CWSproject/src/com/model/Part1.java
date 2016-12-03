package com.model;
import java.util.*;

public class Part1 extends OnePart{
	
	public Part1() {
		this.setPartId("defaultPart1");
		this.setPartName("BODY FUNCTIONS");
		this.setPartDescription("Physiological functions of body systems (including psychological functions)");
		this.setScalarName(new String[]{ "Complete Impairment","Severe Impairment","Moderate Impairment","Mild Impairment", "No Problem", "Not applicable"}); 
		this.setScalarValue(new String[][]{{"4","3","2","1","0","9"}});
		
		SubSet subSet;
		
		subSet = new SubSet("b1","MENTAL FUNCTIONS");
		subSet.addDomain(new Domain("b110", "Consciousness"));
		this.addSubSet(subSet);
		
		//b2
		subSet = new SubSet("b2","SENSORY FUNCTIONS");
		this.addSubSet(subSet);
		//b3
		subSet = new SubSet("b3","VOICE AND SPEECH FUNCTIONS");
		this.addSubSet(subSet);
		//b4
		subSet = new SubSet("b4","FUNCTIONS OF THE CARDIOVASCULAR, HAEMATOLOGICAL, IMMUNOLOGICAL AND RESPIRATORY SYSTEMS");
		this.addSubSet(subSet);
		//b5
		subSet = new SubSet("b5","FUNCTIONS OF THE DIGESTIVE, METABOLIC AND ENDOCRINE SYSTEMS");
		this.addSubSet(subSet);		
		//b6
		subSet = new SubSet("b6","GENITOURINARY AND REPRODUCTIVE FUNCTIONS");
		this.addSubSet(subSet);		
		//b7
		subSet = new SubSet("b7","NEUROMUSCULOSKELETAL AND MOVEMENT RELATED FUNCTIONS");
		this.addSubSet(subSet);		
		//b8
		subSet = new SubSet("b8","FUNCTIONS OF THE SKIN AND RELATED STRUCTURES");
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
