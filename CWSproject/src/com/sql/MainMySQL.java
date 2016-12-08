package com.sql;

import java.sql.*;

import com.model.*;

public class MainMySQL {
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		UserSQL userSQL = new UserSQL();
		if(!userSQL.connet()){ 
			return;
		}
		userSQL.factoryReset();
		
		CareProvider careProvider = new CareProvider( "Tom", "abcd1234");
		careProvider.setType(UserType.CAREPROVIDER);
		careProvider.setEmail("toms123@gmail.com");
		careProvider.setFacility("Ottawa");
		careProvider.setFirstName("Tom");
		careProvider.setLastName("Smith");
		careProvider.setTitle("MD Psychiatrist");
		
		userSQL.createUser(careProvider);
		userSQL.setUser(careProvider);//save in database
		
		careProvider = new CareProvider( "John", "abcd1234");
		careProvider.setType(UserType.CAREPROVIDER);
		careProvider.setEmail("johnd123@gmail.com");
		careProvider.setFacility("Ottawa");
		careProvider.setFirstName("John");
		careProvider.setLastName("Dow");
		careProvider.setTitle("Nurse");
		
		userSQL.createUser(careProvider);
		userSQL.setUser(careProvider);
		

		Admin admin = new Admin( "admin", "admin");
		admin.setType(UserType.ADMIN);
		admin.setEmail("admin@gmail.com");
		admin.setFacility("Ottawa");
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setTitle("Admin");
		userSQL.createUser(admin);
		userSQL.setUser(admin);
		
		//========================================================
		
		FormTemplate formTemplate = new FormTemplate("default");
		Part1 part1 = new Part1();
		Part2 part2 = new Part2();
		Part3 part3 = new Part3();
		formTemplate.addPart(part1);
		formTemplate.addPart(part2);
		formTemplate.addPart(part3);
		
		FormTemplateSQL formTemplateSQL= new FormTemplateSQL();
		formTemplateSQL.connet();
		formTemplateSQL.createFormTemplate(formTemplate);
		formTemplateSQL.setFormTemplate(formTemplate);
		
		//========================================================

		PatientInfo patientInfo = new PatientInfo("cws123", 0);

		careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		careProvider = userSQL.getUserByUserName("John").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		//formTemplate = formTemplateSQL.getFormTemplateByName(1);
		patientInfo.setFormTemplate(formTemplate);
		
		
		

		
		ActionPlan actionPlan = new ActionPlan(patientInfo.getCWSNumber(), careProvider);
		ActionEntry actionEntry = new ActionEntry("0");
		Domain domain = new Domain("b110", "Consciousness");
		actionEntry.setDomain(domain);
		actionEntry.setCscore("3");
		actionEntry.setFscore("1");
		Action action = new Action("0");
		action.setCareProvider(careProvider);
		action.setIntervention("Measurement");
		actionEntry.addAction(action);

		careProvider = new CareProvider( "John", "abcd1234");
		careProvider.setEmail("johnd123@gmail.com");
		careProvider.setFirstName("John");
		careProvider.setLastName("Doe");
		patientInfo.addCareProvider(careProvider);
		
		action = new Action("1");
		action.setCareProvider(careProvider);
		action.setIntervention("Test");
		actionEntry.addAction(action);
		
		actionPlan.addActionEntry(actionEntry);
		
		patientInfo.addActionPlan(actionPlan);
		
		userSQL.disconnect();
		formTemplateSQL.disconnect();
		
	}
}
