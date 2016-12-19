package com.model;

public enum Intervention {
	ASSESSMENT,
	MEASUREMENT,
	TEST,
	BUIPSY,
	SPECIMEN_COLLECTION,
	MONITORING,
	CHALLENGE,
	ASSAY,
	CULTURE,
	ALIMENTATION,
	DRESSING,
	VENTILATION_TRANSFUSION,
	PROCEDURES,
	MANUAL_THERAPY,
	PSYCHOTHERAPY,
	EXERCISE,
	EDUCATION,
	COUNSELLING,
	TRAINING,
	WORKFORCE_DEVELOPMENT,
	ACCUPUNCTURE,
	PREPARATION,
	PROVISION,
	CARE_GIVING,
	PERSONAL_ASSISTANCE,
	PERSONAL_SUPPORT,
	ADVOCACY,
	VROKERAGE,
	DISPENSING,
	PRESCRIPTION,
	TRANSPORT,
	COMMUNITY_DEVELOPMENT,
	PERSONAL_RISK_REDUCTION,
	DIETARY_MODIFICATIONS,
	ENVIRONMENTAL_REMEDIATION;
	
	public String toString(){
		switch (this.ordinal()){
			case 1: return "Assessment";
			case 2: return "Measurement";
			case 3: return "Test";
			case 4: return "Buipsy";
			case 5: return "Specimen Collection";
			case 6: return "Monitoring";
			case 7: return "Challenge";
			case 8: return "Assay";
			case 9: return "CULTURE";
			case 10: return "ALIMENTATION";
			case 11: return "DRESSING";
			case 12: return "VENTILATION_TRANSFUSION";
			case 13: return "PROCEDURES";
			case 14: return "MANUAL_THERAPY";
			case 15: return "PSYCHOTHERAPY";
			case 16: return "EXERCISE";
			case 17: return "EDUCATION";
			case 18: return "COUNSELLING";
			case 19: return "TRAINING";
			case 20: return "WORKFORCE_DEVELOPMENT";
			case 21: return "ACCUPUNCTURE";
			case 22: return "PREPARATION";
			case 23: return "PROVISION";
			case 24: return "CARE_GIVING";
			case 25: return "PERSONAL_ASSISTANCE";
			case 26: return "PERSONAL_SUPPORT";
			case 27: return "ADVOCACY";
			case 28: return "VROKERAGE";
			case 29: return "DISPENSING";
			case 30: return "PRESCRIPTION";
			case 31: return "TRANSPORT";
			case 32: return "COMMUNITY_DEVELOPMENT";
			case 33: return "PERSONAL_RISK_REDUCTION";
			case 34: return "DIETARY_MODIFICATIONS";
			case 35: return "ENVIRONMENTAL_REMEDIATION";
		}
		return null;
	}
}
