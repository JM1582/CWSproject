<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info</title>
<link rel="stylesheet" type="text/css" href="nav_bar.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="div_formats.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="background.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="div_left_right.css" charset="utf-8" >

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="collapse.js" ></script>

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
if (patientInfo == null) {
}
Map documentMap = patientInfo.getDocumentMap();
Map actionPlanMap = patientInfo.getActionPlanMap();
Map summaryMap = (Map)session.getAttribute("summaryMap");
FormTemplate formTemplate = patientInfo.getFormTemplate();
 %>
<p>
<div id="header">
	<h3>CWS No.: <%=patientInfo.getCWSNumber() %>        <img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="50" height="50"></h3>
	
</div>

<table><tr>

<td valign="top" width="15%" >
<!-- navigation bar -->
<div id="nav">
<br>
<ul>
  <li><a class="active" href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a id="provider_input" onclick="expand('document_list')">PROVIDER INPUT</a>
  	<ul id="document_list" class="sublist_collapse">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document tmpDocument = (Document)documentMap.get(documentId);
		if (tmpDocument != null) { %>
	<li><a href="edit_document_servlet?documentId=<%=String.valueOf(tmpDocument.getDocumentId()) %>"><small>
		<%=tmpDocument.getDateOnly() %>:<br>
		<%=tmpDocument.getAuthor().getFirstName() %> <%=tmpDocument.getAuthor().getLastName() %><br>
		<%=tmpDocument.getAuthor().getTitle() %>
	</small></a></li>
<%		}
	}
} %>
  	<li><a href="create_document_servlet" ><small>New Document</small></a></li>
  	</ul>
  </li>
  <li><a id="action_plan" onclick="expand('action_plan_list')">ACTION PLAN</a></li>
  	<ul id="action_plan_list" class="sublist_collapse">
<%if(actionPlanMap != null){
	Iterator it = actionPlanMap.keySet().iterator();
	while(it.hasNext()){
		int actionPlanId = (Integer) it.next();
		ActionPlan tmpActionPlan = (ActionPlan)actionPlanMap.get(actionPlanId);
		int jsfklwef;
		if (tmpActionPlan != null) { %>
	<li><a href="view_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getActionPlanId()) %>">
	<small>Action Plan ID: <%=tmpActionPlan.getActionPlanId() %></small></a></li>
<%		}
	}
} %>
  	<li><a href="create_action_plan_servlet"><small>New Action Plan</small></a></li>
  	</ul>
</ul>
</div>

</td>
<!-- summary form -->
<td valign="top" width="85%" >
<div style="background:#97b9b8;">
<h2> &emsp; COLLABORATIVE SUMMARY</h2>
<%
if (summaryMap.size()==0){ %>
<p> &emsp;&emsp; Patient record empty, please create a new document!</p>
<%	} else {
TreeMap partMap = new TreeMap(formTemplate.getPartsMap());
if(partMap != null){
	Iterator partIt = partMap.keySet().iterator();
	while(partIt.hasNext()){
		String partId = (String) partIt.next();
		OnePart part = (OnePart) partMap.get(partId);
		if(part != null){
			int scalarValueNum = part.getScalarValue().length;
			%>
<table  width="100%" border="1px" cellspacing="0px"  >
	<!-- parts -->
	<tr><td colspan="<%=scalarValueNum+2 %>" ><h3><%=part.getPartName() %></h3></td></tr>
<%			TreeMap subSetMap = new TreeMap(part.getSubSetMap());
			if(subSetMap != null){
				Iterator subSetIt = subSetMap.keySet().iterator();
				while(subSetIt.hasNext()){
					String subSetId = (String) subSetIt.next();
					SubSet subSet = (SubSet) subSetMap.get(subSetId); %>
	<!-- subsets -->
	<tr><td colspan="<%=scalarValueNum+2 %>" ><h4><%=subSet.getSubSetName() %></h4></td></tr>				
<%					if(subSet != null){
						TreeMap domainMap = new TreeMap(subSet.getDomainMap());
						if(domainMap != null){
							Iterator domainIt = domainMap.keySet().iterator();
							while(domainIt.hasNext()){
								String domainId = (String) domainIt.next();
								Domain domain = (Domain) domainMap.get(domainId);
								if(domain != null){
									summaryMap.get(domainId); %>
	<!-- domain -->
	<tr><td colspan="<%=scalarValueNum+2 %>"><strong><%=domain.getDomainName() %></strong></td></tr>
	<%Iterator summaryIt = summaryMap.keySet().iterator(); 
	while(summaryIt.hasNext()){
		String userName = (String) summaryIt.next();
		Document document = (Document) summaryMap.get(userName);
		Map domainValueMap = document.getDomainValueMap();
		if(domainValueMap != null){
			String domainValue[] = (String[]) domainValueMap.get(domainId); %>
	<!-- Title, Name, Domain Value -->
	<tr>
		<td height="50px" width="20%"><%=document.getAuthor().getTitle() %></td>
		<td height="50px"><%=document.getAuthor().getFirstName() %> <%=document.getAuthor().getLastName() %></td>
		<%for(int i=0;i<domainValue.length;i++){ %>
		<td height="50px" width="20%" <%if(domainValue[i].equals("4")||domainValue[i].equals("-4")){ %>bgcolor=#eb6878<%} %>><%=domainValue[i] %></td>
		<%} %>
	</tr>		
<%		}
	}
								}
							}
						}
					}
				}
			} %>
</table>		
<%		}
	}
}
} %>

</div>
</td></tr></table>

</body>
</html>