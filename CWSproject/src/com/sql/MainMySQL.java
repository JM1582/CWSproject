package com.sql;

import java.sql.*;

import com.model.*;

public class MainMySQL {
	public static void main(String[] args) throws SQLException, ClassNotFoundException{
		try{
		UserSQL userSQL = new UserSQL();
		userSQL.connect();
		userSQL.factoryReset();
		
		CareProvider careProvider = new CareProvider( "Tom", "abcd1234");
		careProvider.setType(UserType.CAREPROVIDER);
		careProvider.setEmail("toms123@gmail.com");
		careProvider.setFacility("Ottawa");
		careProvider.setFirstName("Tom");
		careProvider.setLastName("Smith");
		careProvider.setTitle("MD Psychiatrist");
		
		careProvider = userSQL.setUser(careProvider).toCareProvider();
		careProvider = userSQL.setUser(careProvider).toCareProvider();//save in database
		
		careProvider = new CareProvider( "John", "abcd1234");
		careProvider.setType(UserType.CAREPROVIDER);
		careProvider.setEmail("johnd123@gmail.com");
		careProvider.setFacility("Ottawa");
		careProvider.setFirstName("John");
		careProvider.setLastName("Dow");
		careProvider.setTitle("Nurse");

		careProvider = userSQL.setUser(careProvider).toCareProvider();
		careProvider = userSQL.setUser(careProvider).toCareProvider();
		

		Admin admin = new Admin( "admin", "admin");
		admin.setType(UserType.ADMIN);
		admin.setEmail("admin@gmail.com");
		admin.setFacility("Ottawa");
		admin.setFirstName("Admin");
		admin.setLastName("Admin");
		admin.setTitle("Admin");
		
		admin = userSQL.setUser(admin).toAdmin();
		admin = userSQL.setUser(admin).toAdmin();
		
		//========================================================
		
		FormTemplate formTemplate = new FormTemplate("default");
		Part1 part1 = new Part1();
		Part2 part2 = new Part2();
		Part3 part3 = new Part3();
		formTemplate.addPart(part1);
		formTemplate.addPart(part2);
		formTemplate.addPart(part3);
		
		FormTemplateSQL formTemplateSQL= new FormTemplateSQL();
		formTemplateSQL.connect();
		formTemplate = formTemplateSQL.setFormTemplate(formTemplate);
		formTemplate = formTemplateSQL.setFormTemplate(formTemplate);
		
		//========================================================

		PatientInfo patientInfo = new PatientInfo("cws123", 0);

		careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		careProvider = userSQL.getUserByUserName("John").toCareProvider();
		patientInfo.addCareProvider(careProvider);
		
		//formTemplate = formTemplateSQL.getFormTemplateByName(1);
		formTemplate=formTemplateSQL.getFormTemplateByName("default");
		patientInfo.setFormTemplate(formTemplate);
		
		PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
		patientInfoSQL.connect();
		patientInfo = patientInfoSQL.setPatientInfo(patientInfo);
		patientInfo = patientInfoSQL.setPatientInfo(patientInfo);
		
		//========================================================
		
		Document document = new Document(formTemplate, careProvider, patientInfo.getCWSNumber());
		document.setVersion(1);
		document.setDateToday();
		DocumentSQL documentSQL = new DocumentSQL();
		documentSQL.connect();
		document = documentSQL.setDocument(document);
		document = documentSQL.setDocument(document);
		
		patientInfo.addDocument(document);
		
		//========================================================
		
		ActionPlanSQL actionPlanSQL = new ActionPlanSQL();
		actionPlanSQL.connect();
		
		ActionPlan actionPlan = new ActionPlan(patientInfo.getCWSNumber(), careProvider);
		ActionEntry actionEntry = new ActionEntry(0);
		Domain domain = formTemplateSQL.getDomain("b110");
		actionEntry.setDomain(domain);
		actionEntry.setCscore("3");
		actionEntry.setFscore("1");
		
		Action action = new Action(0);
		careProvider = userSQL.getUserByUserName("Tom").toCareProvider();
		action.setCareProvider(careProvider);
		action.setIntervention("Measurement");
		action = actionPlanSQL.setAction(action);
		action = actionPlanSQL.setAction(action);
		
		actionEntry.addAction(action);
		
		action = new Action(0);
		careProvider = userSQL.getUserByUserName("John").toCareProvider();
		action.setCareProvider(careProvider);
		action.setIntervention("Test");
		action = actionPlanSQL.setAction(action);
		action = actionPlanSQL.setAction(action);
		
		actionEntry.addAction(action);
		
		actionPlanSQL.setActionEntry(actionEntry);
		
		actionPlan.addActionEntry(actionEntry);
		actionPlan.setDateToday();
		actionPlan = actionPlanSQL.setActionPlan(actionPlan);
		actionPlan = actionPlanSQL.setActionPlan(actionPlan);

		actionPlan = actionPlanSQL.getAcionPlan(actionPlan.getId());
		
		patientInfo.addActionPlan(actionPlan);

		//========================================================
		
		userSQL.disconnect();
		formTemplateSQL.disconnect();
		patientInfoSQL.disconnect();
		documentSQL.disconnect();
		actionPlanSQL.disconnect();
		
		//========================================================
		} catch (Exception e){
			e.printStackTrace();
		}
		System.out.println("Datebase reset completed.");
	}
}
