<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.model.*,java.util.*;" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info Page</title>
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >
</head>
<body>

<!-- login verification, should always be in beginning -->
<%Admin admin = (Admin)session.getAttribute("user");
if(admin == null || admin.getType()!=UserType.ADMIN){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
<%	return;
}
User account = (User) session.getAttribute("account");
Map<Integer, FormTemplate> formTemplateMap = (Map<Integer, FormTemplate>) session.getAttribute("formTemplateMap");
%>

<div id=Content-Left3 align="right" >
<button class="button_logout" type="button"  onclick="location.href='admin_page.jsp'" >Home</button>
<button class="button_logout" type="button"  onclick="location.href='patient_management_servlet'" >Back</button>
<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div>

<form id="patientIfoForm" action="save_patientInfo_servlet" >
<input type="submit" value="Save" class="button1">
<table>
	<tr>
		<td>Patient ID</td>
		<td>CWS Number</td>
		<td>Icon</td>
		<td>Template</td>
		<td></td>
	</tr>
<%Map<Integer,PatientInfo> patientInfoMap = (Map<Integer, PatientInfo>) session.getAttribute("patientInfoMap");
Iterator<Integer> patientInfoIt = patientInfoMap.keySet().iterator();
while(patientInfoIt.hasNext()){
	int patientInfoId = (Integer) patientInfoIt.next();
	PatientInfo patientInfo = patientInfoMap.get(patientInfoId); %>
	<tr>
		<td><%=patientInfo.getId()%></td>
		<td><input type="text" name="CWSNumber" 
			<%if(patientInfo.getCWSNumber()!=null){ %>value="<%=patientInfo.getCWSNumber() %>"<%} %>></td>
		<td><input type="text" name="icon" 
			<%if(patientInfo.getIcon()!=-1){ %>value="<%=patientInfo.getIcon() %>"<%} %>></td>
		<td><select name="formTemplateId">
			<%if(patientInfo.getFormTemplate()==null){ %>
			<option selected></option>
			<%}
			Iterator<Integer> formTemplateIt = formTemplateMap.keySet().iterator();
			while(formTemplateIt.hasNext()){
				int formTemplateId = formTemplateIt.next();
				FormTemplate formTemplate = formTemplateMap.get(formTemplateId); %>
			<option value="<%=formTemplate.getId() %>" <%if(patientInfo.getFormTemplate().getId()==formTemplate.getId()){ %>selected<%} %>>
				<%=formTemplate.getName() %>
			</option>
			<%} %>
		</select></td>
	</tr>
<%} %>

</table>
</form>

</body>
</html>