<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
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

<script type="text/javascript">
function add_domain(){
	var action_plan_tables = document.getElementById('action_plan');
	action_plan_tables.createElement("table");
}
</script>


</head>

<body class="grayblue">

<!-- banner -->
<div class = "backgroundwhite">
<div class = "green1">
<h1 ><font face = Brandon size = "11" color = "white" ><p align="center">Collaborative Workflow Solutions</p></font></h1>
</div>
<div align="right">
	<button type="button"  onclick="location.href='profile_page.jsp'">Close File</button>
	<button type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div></div>
<!-- banner end -->

<!-- login verification -->
	<%
	CareProvider careProvider = (CareProvider)session.getAttribute("user");
	if(careProvider == null){ %>
		<script language="javascript" type="text/javascript">
			window.location.href='logout_servlet';
		</script>
	<%return;
	} %>

<%PatientInfo patientInfo = (PatientInfo)session.getAttribute("patientInfo");
if (patientInfo == null) {
}
Map documentMap = patientInfo.getDocumentMap();
Map actionPlanMap = patientInfo.getActionPlanMap();
ActionPlan actionPlan = (ActionPlan) session.getAttribute("actionPlan");
TreeMap allDomainMap = new TreeMap((Map) session.getAttribute("allDomainMap")); %>

<div id="header">
	<h3>CWS No.: <%=patientInfo.getCWSNumber() %>        <img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="50" height="50"></h3>
	
</div>

<table><tr>

<td valign="top" width="15%" >

<!-- navigation bar -->
<div id="nav">
<br>
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a id="provider_input" onclick="expand('document_list')">PROVIDER INPUT</a>
  	<ul id="document_list" class="sublist_collapse">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document document = (Document)documentMap.get(documentId);
		if (document != null) { %>
	<li><a href="edit_document_servlet?documentId=<%=String.valueOf(document.getDocumentId()) %>"><small>
	<%=document.getDate() %>:<br><%=document.getAuthor().getFirstName() %> <%=document.getAuthor().getLastName() %></small></a></li>
<%		}
	}
} %>
  	<li><a href="create_document_servlet" ><small>New Document</small></a></li>
  	</ul>
  </li>
  <li><a class="active" id="action_plan" onclick="expand('action_plan_list')">ACTION PLAN</a></li>
  	<ul id="action_plan_list" class="sublist_collapse">
<%if(actionPlanMap != null){
	Iterator it = actionPlanMap.keySet().iterator();
	while(it.hasNext()){
		int actionPlanId = (Integer) it.next();
		ActionPlan tmpActionPlan = (ActionPlan)actionPlanMap.get(actionPlanId);
		if (tmpActionPlan != null) { %>
	<li><a <%if (actionPlanId==actionPlan.getActionPlanId()){ %>class="active"<%} %> href="view_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getActionPlanId()) %>">
	<small>Action Plan ID: <%=tmpActionPlan.getActionPlanId() %></small></a></li>
<%		}
	}
} %>
  	<li><a href="create_action_plan_servlet"><small>New Action Plan</small></a></li>
  	</ul>
</ul>
</div>

</td>

<td valign="top" width="85%" >
<div id="action_plan">
<h3>Action Plan</h3>

<form iname="actionPlanForm" action="save_action_plan_servlet">
<div align="right">
<input type="submit" name="sign" value="Sign">
<input type="submit" value="Save">
</div>

<table border="1" width="100%">
	<tr>
		<td><strong>Domain</strong></td>
		<td><strong>Current Score</strong></td>
		<td><strong>Future Score</strong></td>
		<td><strong>Intervention</strong></td>
		<td><strong>Responsibility</strong></td>
		<td><strong>Title</strong></td>
	</tr>
<%if(actionPlan != null){
	Map actionEntryMap = actionPlan.getActionEntryMap();
	if(actionEntryMap!=null){
		Iterator actionEntryIt = actionEntryMap.keySet().iterator();
		while(actionEntryIt.hasNext()){
			int actionEntryId = (Integer) actionEntryIt.next();
			ActionEntry actionEntry = (ActionEntry) actionEntryMap.get(actionEntryId);
			Map actionMap = actionEntry.getActionMap();
			Iterator actionIt = actionMap.keySet().iterator();
			boolean firstLine = true;
			while(actionIt.hasNext()){
				String actionId = (String) actionIt.next();
				Action action = (Action) actionMap.get(actionId); %>

	<tr>
	<!--  -->
	<%if(firstLine){ %>
		<!-- selecting domain name -->
		<td><select>
		<%Iterator allDomainIt = allDomainMap.keySet().iterator();
		while(allDomainIt.hasNext()){
			String tmpDomainId = (String) allDomainIt.next();
			Domain tempDomain = (Domain) allDomainMap.get(tmpDomainId); %>
			<option <%if(tmpDomainId.equals(actionEntry.getDomain().getDomainId())){ %>selected<%} %>><%=tempDomain.getDomainName() %></option>
		<%} %>
		<option selected="selected"> </option>
		</select></td>
	<!--current score and future score has default value
		<td><input type="text" name="cScore_<%=Integer.toString(actionEntry.getActionEntryId()) %>" value="<%=actionEntry.getCscore() %>" >
		<td><input type="text" name="fScore_<%=Integer.toString(actionEntry.getActionEntryId()) %>" value="<%=actionEntry.getFscore() %>" >
	  -->	
		<td><input type="text" name="cScore_<%=Integer.toString(actionEntry.getActionEntryId()) %>" value=  "">
		<td><input type="text" name="fScore_<%=Integer.toString(actionEntry.getActionEntryId()) %>" value= "" >
	<%} else{ %>
		<td colspan="3"></td>
	<%} 
	firstLine = false; %>
		<td><%=action.getIntervention() %></td>
		<%Map careProviderMap = patientInfo.getCareProviderMap();
		if(careProviderMap != null) { %>
		<td><select>
<%			Iterator careProviderIt = careProviderMap.keySet().iterator();
			while(careProviderIt.hasNext()){
				String userName = (String) careProviderIt.next();
				CareProvider tmpCareProvider = (CareProvider) careProviderMap.get(userName); %>
			<option <%if(userName.equals(action.getCareProvider().getUserName())){ %>selected<%} %>><%=tmpCareProvider.getFirstName() %> <%=tmpCareProvider.getLastName() %></option>
<%			} %>
		<option selected="selected"> </option>
		</select></td>
<%		} %>
		<td><%=action.getCareProvider().getTitle() %></td>
	</tr>
		
<%			}
		}
	}
}
%>

</table>

<input type="button" onclick="add_domain()" value="+">
</form>
</div>
</td>

</tr></table>

</body>
</html>