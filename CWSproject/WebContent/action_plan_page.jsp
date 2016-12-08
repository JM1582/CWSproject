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
<link rel="stylesheet" type="text/css" href="buttons.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="font_styles.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="formcell_format.css" charset="utf-8" >
<script type="text/javascript" src="collapse.js" ></script>

<script type="text/javascript">
function add_domain(){
	var action_plan_tables = document.getElementById('action_plan');
	action_plan_tables.createElement("table");
}
</script>


</head>

<body class="grayblue">

<!-- banner 
<div class = "backgroundwhite">
<div class = "green1">
<h1 ><font face = Brandon size = "11" color = "white" ><p align="center">Collaborative Workflow Solutions</p></font></h1>
</div>
<div align="right">
	<button type="button"  onclick="location.href='profile_page.jsp'">Close File</button>
	<button type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div></div>
<!-- banner end -->
<div align="right" class="banner2" >
<div class="hidden_above60" ><br>
	<button class="button_logout" type="button"  onclick="location.href='profile_page.jsp'" >Close File</button>
	<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>&emsp;&emsp;
</div></div>


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

 <!-- cws number and icon -->
<div>
<div class="header">
	<p class="engrave60">&nbsp;<img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="80" height="80">
	<strong><%=patientInfo.getCWSNumber() %></strong></p>
</div>
<br><br><br><br><br><br><br>
</div>

<table><tr>

<td valign="top" width="20%" >

<!-- navigation bar -->
<div id="nav">
<br>
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a 
	id="provider_input" onclick="expand('document_list')">PROVIDER INPUT</a>
  	<ul id="document_list" class="sublist_collapse">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document tmpDocument = (Document)documentMap.get(documentId);
		if (tmpDocument != null) { %>
	<li><a href="edit_document_servlet?documentId=<%=String.valueOf(tmpDocument.getDocumentId()) %>"><small>
		<%=tmpDocument.getDateOnly() %><br>
		<%=tmpDocument.getAuthor().getFirstName() %> <%=tmpDocument.getAuthor().getLastName() %><br>
		<%=tmpDocument.getAuthor().getTitle() %>
	</small></a></li>
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
<<<<<<< HEAD
	<li><a <%if (actionPlanId==actionPlan.getActionPlanId()){ %>class="active"<%} %> href="view_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getActionPlanId()) %>"><small>
		<%=tmpActionPlan.getDateOnly() %><br>
=======
	<li><a <%if (actionPlanId==actionPlan.getActionPlanId()){ %>class="active"<%} %> href="edit_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getActionPlanId()) %>"><small>
		<%=tmpActionPlan.getDateOnly() %>:<br>
>>>>>>> 1a0ffa69cfb909a24bdac4e2c686d41d5bc7e3d4
		<%=tmpActionPlan.getAuthor().getFirstName() %> <%=tmpActionPlan.getAuthor().getLastName() %><br>
		<%=tmpActionPlan.getAuthor().getTitle() %>
	</small></a></li>
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
<form name="actionPlanForm" action="save_action_plan_servlet">
<div style="position:fixed; background:#e5e8d4; width:100%; margin-top:20px;">
<p><h2 class="table_header"> &nbsp; Action Plan</h2><p>
<div class="no_margin"><span>
&emsp;
<input type="submit" name="sign" value="Sign" class="button1">
<input type="submit" value="Save" class="button1"></span></div>
</div><br><br><br><br><br><br><br><br>


<div style="background:white; ">
<table border="1" width="100%">
	<tr >
		<td class="tr1_td1"><font><strong>Domain</strong></font></td>
		<td class="tr1_td2"><font><strong>Current Score</strong></font></td>
		<td class="tr1_td3"><font><strong>Future Score</strong></font></td>
		<td class="tr1_td4"><font><strong>Intervention</strong></font></td>
		<td class="tr1_td5"><font><strong>Responsibility</strong></font></td>
	</tr>
<%if(actionPlan != null){
	Map actionEntryMap = actionPlan.getActionEntryMap();
	if(actionEntryMap!=null){
		Iterator actionEntryIt = actionEntryMap.keySet().iterator();
		while(actionEntryIt.hasNext()){
			String actionEntryId = (String) actionEntryIt.next();
			ActionEntry actionEntry = (ActionEntry) actionEntryMap.get(actionEntryId);
			Map actionMap = actionEntry.getActionMap();
			Iterator actionIt = actionMap.keySet().iterator();
			boolean firstLine = true;
			while(actionIt.hasNext()){
				String actionId = (String) actionIt.next();
				Action action = (Action) actionMap.get(actionId); %>

	<tr><!--  -->
	<%if(firstLine){ %>
		<!-- selecting domain name -->
<<<<<<< HEAD
		<td height="80px"><select style="height:80px;">
=======
		<td><select name="domain_<%=actionEntry.getActionEntryId() %>">
<<<<<<< HEAD
>>>>>>> 1a0ffa69cfb909a24bdac4e2c686d41d5bc7e3d4
=======
		<%if(actionEntry.getDomain()==null){ %>
			<option selected></option>
		<%} %>
>>>>>>> 3564789ec59af030778f7fc5dc92499d722598fc
		<%Iterator allDomainIt = allDomainMap.keySet().iterator();
		while(allDomainIt.hasNext()){
			String tmpDomainId = (String) allDomainIt.next();
			Domain tempDomain = (Domain) allDomainMap.get(tmpDomainId); %>
			<option <%if(actionEntry.getDomain()!=null){ if(tmpDomainId.equals(actionEntry.getDomain().getDomainId())){ %>selected<%} } %> value="<%=tmpDomainId %>">
				<%=tempDomain.getDomainName() %>
			</option>
		<%} %>
		</select></td>
	<!--current score and future score has default value -->
		<td><input type="text" name="cScore_<%=actionEntry.getActionEntryId() %>" 
		<%if(actionEntry.getCscore()!=null){ %>value="<%=actionEntry.getCscore() %>"<%} %> ></td>
		<td><input type="text" name="fScore_<%=actionEntry.getActionEntryId() %>" 
		<%if(actionEntry.getFscore()!=null){ %>value="<%=actionEntry.getFscore() %>"<%} %> ></td>
	<!-- 
		<td><input type="text" name="cScore_<%=actionEntry.getActionEntryId() %>" value=  ""></td>
		<td><input type="text" name="fScore_<%=actionEntry.getActionEntryId() %>" value= "" ></td>
	-->
	<%} else{ %>
		<!-- not the firstline of action entry -->
		<td colspan="3"></td>
	<%} 
	firstLine = false; %>
		<td><select name="intervention_<%=actionEntry.getActionEntryId() %>_<%=action.getActionId() %>">
			<!-- need to add intervention list -->
			<%if(action.getIntervention()==null){ %>
			<option selected></option>
			<%} else { %>
			<option value="<%=action.getIntervention() %>"><%=action.getIntervention() %></option>
			<%} %>
		</select></td>
		<%Map careProviderMap = patientInfo.getCareProviderMap();
		if(careProviderMap != null) { %>
		<td><select name="responsibility_<%=actionEntry.getActionEntryId() %>_<%=action.getActionId() %>">
			<%if(action.getCareProvider()==null){ %>
				<option selected></option>
			<%} %>
<%			Iterator careProviderIt = careProviderMap.keySet().iterator();
			while(careProviderIt.hasNext()){
				String userName = (String) careProviderIt.next();
				CareProvider tmpCareProvider = (CareProvider) careProviderMap.get(userName); %>
			<option <%if(action.getCareProvider()!=null){ if(userName.equals(action.getCareProvider().getUserName())){ %>selected<%} } %> value="<%=userName %>">
				<%=tmpCareProvider.getTitle() %>: <%=tmpCareProvider.getFirstName() %> <%=tmpCareProvider.getLastName() %>
			</option>
<%			} %>
		</select></td>
<%		} %>
	</tr>
		
<%			}
		}
	}
}
%>
</table>
<<<<<<< HEAD
</div>
<input type="button" onclick="add_domain()" value="+">
=======

<<<<<<< HEAD
<input type="button" onclick="location.href='add_domain_servlet'" value="+">
>>>>>>> 1a0ffa69cfb909a24bdac4e2c686d41d5bc7e3d4
=======
<input type="button" onclick="location.href='add_action_entry_servlet'" value="+">
>>>>>>> 3564789ec59af030778f7fc5dc92499d722598fc
</form>
</div>
</td>
<td></td>
</tr></table>

</body>
</html>