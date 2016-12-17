<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info</title>
<link rel="stylesheet" type="text/css" href="css/nav_bar.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_formats.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/background.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_left_right.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/font_styles.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/formcell_format.css" charset="utf-8" >

<script type="text/javascript" src="collapse.js" ></script>

<style type="text/css">
select{
	width: 100%;
	white-space: pre-wrap;
}
</style>

</head>

<body class="grayblue">
<!-- login verification, should always be in beginning -->
<%CareProvider careProvider = (CareProvider)session.getAttribute("user");
if(careProvider == null){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
<%return;
}
PatientInfo patientInfo = (PatientInfo)session.getAttribute("patientInfo");
if (patientInfo == null) {
}
Map documentMap = patientInfo.getDocumentMap();
Map actionPlanMap = patientInfo.getActionPlanMap();
ActionPlan actionPlan = (ActionPlan) session.getAttribute("actionPlan");
TreeMap allDomainMap = new TreeMap((Map) session.getAttribute("allDomainMap")); %>

<!-- banner -->
<div id="banner" align="right" class="banner2" ><br>
	<button class="button_logout" type="button"  onclick="location.href='profile_page.jsp'" >Close File</button>
	<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>&emsp;&emsp;
</div>


<!-- patientInfo: cws number and icon -->
<div id="patient_info" class="header">
	<p class="engrave60">&nbsp;<img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="80" height="80">
	<strong><%=patientInfo.getCWSNumber() %></strong></p>
</div>

<!-- navigation bar -->
<div id="nav" class="nav_bar">
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>"><font size=5 style="line-height:60px">SUMMARY VIEW</font></a></li>
  <li><a 
	id="provider_input" onclick="expand('document_list')"><font size=5 style="line-height:60px">PROVIDER INPUT</font></a>
  	<ul id="document_list" class="sublist_collapse">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document tmpDocument = (Document)documentMap.get(documentId);
		if (tmpDocument != null) { %>
	<li><a href="edit_document_servlet?documentId=<%=String.valueOf(tmpDocument.getId())%>"><small>
		<%=tmpDocument.getDateOnly() %><br>
		<%=tmpDocument.getAuthor().getFirstName() %> <%=tmpDocument.getAuthor().getLastName() %><br>
		<%=tmpDocument.getAuthor().getTitle() %>
	</small></a></li>
<%		}
	}
} %>
  	<li><a href="create_document_servlet" ><font size=4.5>New Document</font></a></li>
  	</ul>
  </li>
  <li><a class="active" id="action_plan" onclick="expand('action_plan_list')"><font size=5 style="line-height:60px">ACTION PLAN</font></a></li>
  	<ul id="action_plan_list" class="sublist_collapse">
<%if(actionPlanMap != null){
	Iterator it = actionPlanMap.keySet().iterator();
	while(it.hasNext()){
		int actionPlanId = (Integer) it.next();
		ActionPlan tmpActionPlan = (ActionPlan)actionPlanMap.get(actionPlanId);
		if (tmpActionPlan != null) { %>
	<li><a <%if (actionPlanId==actionPlan.getId()){%>class="active"<%}%> href="edit_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getId())%>"><small>
		<%=tmpActionPlan.getDateOnly() %><br>
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


<form name="actionPlanForm" action="save_action_plan_servlet">

<!-- document body header -->
<div  class="body_header">
<p><h2 class="form_instruction"> &nbsp; Action Plan</h2><p>
<div class="no_margin">
&emsp;
<input type="submit" name="sign" value="Sign" class="button1">
<input type="submit" value="Save" class="button1"></div>
</div>


<!-- action plan form -->
<div id="action_plan_body" class="body_div">

<div id="action_plan">
<table border="1">
	<tr >
		<td class="tr1_td2"><font><strong>Domain</strong></font></td>
		<td class="tr1_td3"><font><strong>Current Score</strong></font></td>
		<td class="tr1_td3"><font><strong>Future Score</strong></font></td>
		<td class="tr1_td4"><font><strong>Intervention</strong></font></td>
		<td class="tr1_td5"><font><strong>Responsibility</strong></font></td>
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
				int actionId = (Integer) actionIt.next();
				Action action = (Action) actionMap.get(actionId); %>

	<tr><!--  -->
	<%if(firstLine){ %>
		<!-- selecting domain name -->
		<td height="60px"><select style="height:50px;" name="domain_<%=actionEntry.getId()%>">
		<%
			if(actionEntry.getDomain()==null){
		%>
			<option selected></option>
		<%
			}
		%>
		<%
			Iterator allDomainIt = allDomainMap.keySet().iterator();
				while(allDomainIt.hasNext()){
			String tmpDomainId = (String) allDomainIt.next();
			Domain tempDomain = (Domain) allDomainMap.get(tmpDomainId);
		%>
			<option <%if(actionEntry.getDomain()!=null){ if(tmpDomainId.equals(actionEntry.getDomain().getId())){%>selected<%} }%> value="<%=tmpDomainId%>">
				<%=tempDomain.getName()%>
			</option>
		<%
			}
		%>
		</select></td>
	<!--current score and future score has default value -->
		<td><input type="text" name="cScore_<%=actionEntry.getId()%>" 
		<%if(actionEntry.getCscore()!=null){%>value="<%=actionEntry.getCscore()%>"<%}%>style="height:50px;" ></td>
		<td><input type="text" name="fScore_<%=actionEntry.getId()%>" 
		<%if(actionEntry.getFscore()!=null){%>value="<%=actionEntry.getFscore()%>"<%}%> style="height:50px;"></td>
	<!-- 
		<td><input type="text" name="cScore_<%=actionEntry.getId()%>" value=  ""></td>
		<td><input type="text" name="fScore_<%=actionEntry.getId()%>" value= "" ></td>
	-->
	<%
		} else{
	%>
		<!-- not the firstline of action entry -->
		<td colspan="3"></td>
	<%
		} 
		firstLine = false;
	%>
		<td><select style="height:50px;"name="intervention_<%=actionEntry.getId()%>_<%=action.getId()%>">
			<!-- need to add intervention list -->
			<%
				if(action.getIntervention()==null){
			%>
			<option selected></option>
			<%
				} else {
			%>
			<option value="<%=action.getIntervention()%>"><%=action.getIntervention()%></option>
			<%
				}
			%>
		</select></td>
		<%
			Map careProviderMap = patientInfo.getCareProviderMap();
				if(careProviderMap != null) {
		%>
		<td><select style="height:50px;"name="responsibility_<%=actionEntry.getId()%>_<%=action.getId()%>">
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
</div>
<input type="button" onclick="location.href='add_action_entry_servlet'" value="+">
</div>

</form>


</body>
</html>