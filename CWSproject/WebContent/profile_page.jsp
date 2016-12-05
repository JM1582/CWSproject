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
<link rel="stylesheet" type="text/css" href="buttons.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="font_styles.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="div_left_right.css" charset="utf-8" >
<script type="text/javascript" src="collapse.js" ></script>
</head>
<body class="picLUp2">
<div class="hidden_above3"></div>
<div class="hidden_above2">
<div id=Content-Left3 align="right" >
<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button></div>


</div>
	<%
	CareProvider careProvider = (CareProvider)session.getAttribute("user");
	if(careProvider == null){ %>
		<script language="javascript" type="text/javascript">
			window.location.href='logout_servlet';
		</script>
	<%return;
	} %>
	
<p class="engrave30"><strong>&nbsp;&nbsp;<%=careProvider.getFirstName() %> <%=careProvider.getLastName() %></strong></p>
<p class="engrave23"><strong>&emsp;&emsp;<%=careProvider.getTitle() %></strong></p>
<p class="engrave23"><strong>&emsp;&emsp;Location: <%=careProvider.getFacility() %></strong></p>
<p class="engrave23"><strong>&emsp;&emsp;Email: <%=careProvider.getEmail() %></strong></p>
 
<form align="center" action="search_patient_servlet">
<p style= font-size:22px>Search for your patient:</p>
&emsp;&emsp;&emsp;&emsp;<input type="text" name="CWSNumber" style="width:232px; height:35px;">
<input type="submit" value="Search" 
class="button1">
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