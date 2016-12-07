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
		subSet.addDomain(new Domain("b114", "Orientation"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b117", "Intellectual"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b130", "Energy and drive functions"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b134", "Sleep"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b140", "Attention"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b144", "Memory"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b152", "Emotional functions"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b156", "Perceptual functions"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b164", "Higher level cognitive functions"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b167", "Language"));
		this.addSubSet(subSet);
		
		
		//b2
		subSet = new SubSet("b2","SENSORY FUNCTIONS");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b210", "Seeing"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b230", "Hearing"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b235", "Vestibular"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b280", "Pain"));
		this.addSubSet(subSet);
		
		//b3
		subSet = new SubSet("b3","VOICE AND SPEECH FUNCTIONS");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b310", "Voice"));
		this.addSubSet(subSet);
		
		//b4
		subSet = new SubSet("b4","FUNCTIONS OF THE CARDIOVASCULAR, HAEMATOLOGICAL, IMMUNOLOGICAL AND RESPIRATORY SYSTEMS");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b410", "Heart"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b420", "Blood pressure"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b430", "Heamatological (blood)"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b435", "Immunological (allergies, hypersensitivity)"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b440", "Respiration (breathing"));
		this.addSubSet(subSet);
		
		//b5
		subSet = new SubSet("b5","FUNCTIONS OF THE DIGESTIVE, METABOLIC AND ENDOCRINE SYSTEMS");
		this.addSubSet(subSet);	
		subSet.addDomain(new Domain("b515", "Digestive"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b525", "Defecation"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b530", "Weight maintenance"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b555", "Endocrine glands (hormonal changes)"));
		this.addSubSet(subSet);	
		//b6
		subSet = new SubSet("b6","GENITOURINARY AND REPRODUCTIVE FUNCTIONS");
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b620", "Urination functions"));
		this.addSubSet(subSet);
		subSet.addDomain(new Domain("b640", "Secual functions"));
		this.addSubSet(subSet);
		
		
		//b7
		subSet = new SubSet("b7","NEUROMUSCULOSKELETAL AND MOVEMENT RELATED FUNCTIONS");
		this.addSubSet(subSet);		
		subSet.addDomain(new Domain("b710", "Mobility of joint"));
		this.addSubSet(subSet);	
		subSet.addDomain(new Domain("b730", "Muscle power"));
		this.addSubSet(subSet);	
		subSet.addDomain(new Domain("b735", "Muscle tone"));
		this.addSubSet(subSet);	
		subSet.addDomain(new Domain("b765", "Involuntary movements"));
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
