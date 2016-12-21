<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Management Page</title>
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >
</head>
<body>
<!-- login verification, should always be in beginning -->
<%Admin admin = (Admin)session.getAttribute("user");
if(admin == null || admin.getType()!=UserType.ADMIN){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
<%return;
} %>

<div id=Content-Left3 align="right" >
<button class="button_logout" type="button"  onclick="location.href='admin_page.jsp'" >Home</button>
<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div>

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
		<td><%=patientInfo.getCWSNumber() %></td>
		<td><%=patientInfo.getIcon() %></td>
		<%if(patientInfo.getFormTemplate()!=null){ %>
		<td><%=patientInfo.getFormTemplate().getName() %></td>
		<%}else{ %>
		<td>not set</td>
		<%} %>
		<td><button type="button"  onclick="location.href='edit_patient_servlet?patientInfoId=<%=patientInfo.getId() %>'">Edit</button></td>
	</tr>
<%} %>
</table>
<button type="button"  onclick="location.href='create_patient_servlet'">+</button>

</body>
</html>