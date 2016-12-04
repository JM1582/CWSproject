<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*,com.model.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info</title>
<link rel="stylesheet" type="text/css" href="nav_bar.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="div_formats.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="background.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="div_left_right.css" charset="utf-8" >
<script type="text/javascript" src="collapse.js" ></script>
</head>
<body class="grayblue">
<!-- banner -->
<div class = "backgroundwhite">
<div class = "green1">
<h1 ><font face = Brandon size = "11" color = "white" ><p align="center">Collaborative Workflow Solutions</p></font></h1>
</div>
<br>
</div>

<div align="right"><button type="button"  onclick="location.href='logout_servlet'">Logout</button></div>
	<%
	CareProvider careProvider = (CareProvider)session.getAttribute("user");
	if(careProvider == null){ %>
		<script language="javascript" type="text/javascript">
			window.location.href='logout_servlet';
		</script>
	<%return;
	} %>
	
<p style=font-size:32px><strong>&nbsp;&nbsp;<%=careProvider.getFirstName() %> <%=careProvider.getLastName() %></strong></p>
<p style=font-size:22px;><strong>&emsp;&emsp;<%=careProvider.getTitle() %></strong></p>
<p style=font-size:22px;><strong>&emsp;&emsp;Location: <%=careProvider.getFacility() %></strong></p>
<p style= font-size:22px;><strong>&emsp;&emsp;Email: <%=careProvider.getEmail() %></strong></p>

<!-- 
<p align="center">Select Your Patients:<br></p>

<table align="center">
	<tr>
		<td align="center">Patient CSW Number</td>
		<td align="center">Image</td>
		<td align="center">Action</td>
	</tr>
	<%
	Map patientInfoMap = careProvider.getMyPatientInfoMap();//iterator, point to next value
	if (patientInfoMap != null) {
		Iterator it = patientInfoMap.keySet().iterator();
		while(it.hasNext()){
			String cwsNumber = (String)it.next();
			PatientInfo patientInfo = (PatientInfo)patientInfoMap.get(cwsNumber);
			if(patientInfo != null){
	%>
	<tr>
		<td align="center"><%=patientInfo.getCWSNumber() %></td>
		<td align="center">Left for image</td>
		<td align="center"><a href="view_patient_info_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">View</a></td>
	</tr>
	<% 		}
		}
	} %>
</table>
 -->
 
<form align="center" action="search_patient_servlet">
<p style= font-size:22px>Search for your patient:</p>
&emsp;&emsp;&emsp;&emsp;<input type="text" name="CWSNumber" style="width:232px; height:35px;">
<input type="submit" value="Search" 
style="width:120px;  font:Brandon; font-size:18px; height:35px; color:#314a51; 
						background:#eb7571 ">
<br><br><br><br>
</form>
<!-- a form which show the search result (omitted)
	<%
	PatientInfo patientInfo = (PatientInfo)session.getAttribute("patientInfo");
	if (patientInfo != null){
	%>
	<table align="center"><tr>
		<td align="center"><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>"><%=patientInfo.getCWSNumber() %></a></td>
		<td align="center">Image ID = <%=patientInfo.getIcon() %></td>
	</tr></table>
	<% } %>
 -->

</body>
</html>