<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,com.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info</title>
<link rel="stylesheet" type="text/css" href="nav_bar.css">
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    width: 180px;
    background-color: #f1f1f1;
}

li a {
    display: block;
    color: #000;
    padding: 0;
    text-decoration: none;
}

li a.active {
    background-color: #4CAF50;
    color: white;
}

li a:hover:not(.active) {
    background-color: #555;
    color: white;
}

body {
	background-color: #8caec9;
}
</style>

</head>

<body>
<div align="right">
	<button type="button"  onclick="location.href='profile_page.jsp'">Close File</button>
	<button type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div>
	<%
	CareProvider careProvider = (CareProvider)session.getAttribute("user");
	if(careProvider == null){ %>
		<script language="javascript" type="text/javascript">
			window.location.href='logout_servlet';
		</script>
	<%return;
	} %>
	
<%
PatientInfo patientInfo = (PatientInfo)session.getAttribute("patientInfo");
 %>
 
<div>
	<h3>CWS No.: <%=patientInfo.getCWSNumber() %>        <img src="flower<%=(Integer)patientInfo.getIcon() %>.jpg" ></h3>
	
</div>

<table><tr>

<td valign="top" width="180" >
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a class="active" href="documents_list_page.jsp">PROVIDER INPUT</a></li>
  <li><a href="edit_action_plan_servlet">ACTION PLAN</a></li>
</ul>
</td>

<td valign="top" >
<div align="center">
<form name="createDocumentForm" method="post" action="create_document_servlet" onSubmit="return submitcheck()" align="center">
	<input type="submit" value="Create New Form">
</form>
<table align="center" border="1">
	<tr>
		<td align="center"><strong>Title</strong></td>
		<td align="center"><strong>Care Provider</strong></td>
		<td align="center"><strong>Date</strong></td>
		<!-- <td align="center"><strong>Version</strong></td> -->
		<td align="center"></td>
	</tr>
	
	<% Map documentMap = patientInfo.getDocumentMap();
	if (documentMap != null){ 
		Iterator it = documentMap.keySet().iterator();
		while(it.hasNext()){
			int documentId = (Integer)it.next();
			Document document = (Document)documentMap.get(documentId);
			if (document != null) {
	%>
	<tr>
		<td align="center"><%=document.getAuthor().getTitle() %></td>
		<td align="center"><%=document.getAuthor().getFirstName() %> <%=document.getAuthor().getLastName() %></td>
		<td align="center"><%=document.getDate() %></td>
		<!-- <td align="center">Version <%=document.getVersion() %></td> -->
		<td align="center"><a href="edit_document_servlet?documentId=<%=String.valueOf(document.getDocumentId()) %>">View</a></td>
	</tr>
	<% } } } %>
</table>
</div>
</td>

</tr></table>

</body>
</html>