package com.sql;

import com.model.*;

public class FakeSQL {
	public User getUser(String userName){
		CareProvider careProvider=null;
		if(userName.equals("Tom")){
			careProvider = new CareProvider( "Tom", "abcd1234");
			careProvider.setType(UserType.CAREPROVIDER);
			careProvider.setEmail("toms123@gmail.com");
			careProvider.setFacility("Ottawa");
			careProvider.setFirstName("Tom");
			careProvider.setLastName("Smith");
			careProvider.setTitle("MD Psychiatrist");
			return (User) careProvider;
		} else if(userName.equals("John")){
			careProvider = new CareProvider( "John", "abcd1234");
			careProvider.setType(UserType.CAREPROVIDER);
			careProvider.setEmail("johnd123@gmail.com");
			careProvider.setFacility("Ottawa");
			careProvider.setFirstName("John");
			careProvider.setLastName("Dow");
			careProvider.setTitle("Nurse");
			return (User) careProvider;
		} else if(userName.equals("admin")){
			Admin admin = new Admin( "admin", "admin");
			admin.setType(UserType.ADMIN);
			admin.setEmail("admin@gmail.com");
			admin.setFacility("Ottawa");
			admin.setFirstName("Admin");
			admin.setLastName("Admin");
			admin.setTitle("Admin");
			return (User) admin;
		}
		return null;
	}
	
	public FormTemplate getFormTemplate(){
		FormTemplate formTemplate = new FormTemplate("default");
		Part1 part1 = new Part1();
		Part2 part2 = new Part2();
		Part3 part3 = new Part3();
		formTemplate.addPart(part1);
		formTemplate.addPart(part2);
		formTemplate.addPart(part3);
		
		return formTemplate;
	}
	
	public PatientInfo getPatientInfo(){
		PatientInfo patientInfo = new PatientInfo("cws123", 0);

		CareProvider careProvider = this.getUser("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		careProvider = this.getUser("John").toCareProvider();
		patientInfo.addCareProvider(careProvider);

		
		patientInfo.setFormTemplate(this.getFormTemplate());

		ActionPlan actionPlan = new ActionPlan(patientInfo.getCWSNumber(), this.getUser("Tom").toCareProvider());
		actionPlan.setActionPlanId(0);
		actionPlan.setDateToday();
		ActionEntry actionEntry = new ActionEntry("0");
		Domain domain = new Domain("b110", "Consciousness");
		actionEntry.setDomain(domain);
		actionEntry.setCscore("3");
		actionEntry.setFscore("1");
		
		Action action = new Action("0");
		action.setCareProvider(this.getUser("Tom").toCareProvider());
		action.setIntervention("Measurement");
		actionEntry.addAction(action);
		
		patientInfo.addCareProvider(this.getUser("John").toCareProvider());
		action = new Action("1");
		action.setCareProvider(this.getUser("John").toCareProvider());
		action.setIntervention("Test");
		actionEntry.addAction(action);
		
		actionPlan.addActionEntry(actionEntry);
		
		patientInfo.addActionPlan(actionPlan);
		
		return patientInfo;
	}

}
