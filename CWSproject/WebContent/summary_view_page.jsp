<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Patient Info</title>
<link rel="stylesheet" type="text/css" href="css/nav_bar.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_formats.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/background.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_left_right.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/font_styles.css" charset="utf-8" >

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script type="text/javascript" src="collapse.js" ></script>

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
Map<Integer, Document> documentMap = patientInfo.getDocumentMap();
Map actionPlanMap = patientInfo.getActionPlanMap();
Map<String, Map<String, String[]>> summaryMap = (Map<String, Map<String, String[]>>)session.getAttribute("summaryMap");
FormTemplate formTemplate = patientInfo.getFormTemplate(); %>


<!-- banner -->
<div id="banner" align="right" class="banner2" >
<br>
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
  <li><a class="active" href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>"><font size=5 style="line-height:60px">SUMMARY VIEW</font></a></li>
  <li><a id="provider_input" onclick="expand('document_list')"><font size=5 style="line-height:60px">PROVIDER INPUT</font></a>
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
  <li><a id="action_plan" onclick="expand('action_plan_list')"><font size=5 style="line-height:60px">ACTION PLAN</font></a></li>
  	<ul id="action_plan_list" class="sublist_collapse">
<%if(actionPlanMap != null){
	Iterator it = actionPlanMap.keySet().iterator();
	while(it.hasNext()){
		int actionPlanId = (Integer) it.next();
		ActionPlan tmpActionPlan = (ActionPlan)actionPlanMap.get(actionPlanId);
		if (tmpActionPlan != null) { %>
	<li><a href="edit_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getId())%>"><small>
		<%=tmpActionPlan.getDateOnly() %>:<br>
		<%=tmpActionPlan.getAuthor().getFirstName() %> <%=tmpActionPlan.getAuthor().getLastName() %><br>
		<%=tmpActionPlan.getAuthor().getTitle() %>
	</small></a></li>
<%		}
	}
} %>
  	<li><a href="create_action_plan_servlet"><font size=4.5>New Action Plan</font></a></li>
  	</ul>
</ul>
</div>

<!-- summary body header -->
<div class="body_header">
<p><h2 class="table_header"> &nbsp; Collaborative Summary</h2><p>
</div>

<!-- summary form -->
<div id="summary_body" class="body_div">
<%if (summaryMap.size()==0){ %>
<p style="font-family:Arial; font-size:30px"> &emsp; Patient record empty, please to go "Provider Input" to create a new document!</p>
<%} else {
TreeMap partMap = new TreeMap(formTemplate.getPartsMap());
if(partMap != null){
	Iterator partIt = partMap.keySet().iterator();
	while(partIt.hasNext()){
		String partId = (String) partIt.next();
		OnePart part = (OnePart) partMap.get(partId);
		if(part != null){
			if(part.hasDomainValueWithSummaryMap(summaryMap)){
				int scalarValueAmount = part.getScalarValue().length; %>
<table id="part_<%=part.getId() %>" width="100%" border="1px" cellspacing="0px"  >
	<!-- parts -->
	<tr><td colspan="<%=scalarValueAmount+2 %>" ><h3><font class="input_part"><%=part.getName()%></font></h3></td></tr>
<%				TreeMap subSetMap = new TreeMap(part.getSubSetMap());
				if(subSetMap != null){
					Iterator subSetIt = subSetMap.keySet().iterator();
					while(subSetIt.hasNext()){
						String subSetId = (String) subSetIt.next();
						SubSet subSet = (SubSet) subSetMap.get(subSetId);			
						if(subSet != null){
							if(subSet.hasDomainValueWithSummaryMap(summaryMap)){ %>
						
	<!-- subsets -->
	<tr><td colspan="<%=scalarValueAmount+2 %>" ><h4><font class="input_subpart">&emsp;<%=subSet.getName()%></font></h4></td></tr>
<%						TreeMap domainMap = new TreeMap(subSet.getDomainMap());
						if(domainMap != null){
							Iterator domainIt = domainMap.keySet().iterator();
							while(domainIt.hasNext()){
								String domainId = (String) domainIt.next();
								Domain domain = (Domain) domainMap.get(domainId);
								if(domain != null){
									if(domain.hasDomainValueWithSummaryMap(summaryMap)){ %>
	<!-- domain -->
	<tr><td colspan="<%=scalarValueAmount+2 %>"><font class="input_domain"><strong>&emsp;&emsp;<%=domain.getId()%>. <%=domain.getName()%></strong></font></td></tr>
	<%Iterator<String> summaryIt = summaryMap.keySet().iterator(); 
	while(summaryIt.hasNext()){
		String authorUserName = (String) summaryIt.next();
		Map<String,String[]> domainValueMap = summaryMap.get(authorUserName);
		if(domainValueMap != null){
			String domainValue[] = (String[]) domainValueMap.get(domainId);
			if(domainValue!=null){
				CareProvider author = patientInfo.getCareProviderMap().get(authorUserName);
				boolean allNull = true;
				for(int i=0;i<domainValue.length;i++){
					if(domainValue[i]!=null && !domainValue[i].equals("") && !domainValue[i].equals("9")){
						allNull = false;
						break;
					}
				}
				if(!allNull){
			%>
	<!-- Title, Name, Domain Value -->
	<tr>
		<td height="50px" width="20%" align="center" class="summaryEntry"><%=author.getTitle() %></td>
		<td height="50px" align="center" class="summaryEntry"><%=author.getFirstName() %> <%=author.getLastName() %></td>
		<%for(int i=0;i<scalarValueAmount;i++){
			if(domainValue[i]!=null && !domainValue[i].equals("9")){ %>
		<td height="50px" width="20%" align="center" class="summaryEntry"<%if(domainValue[i].equals("4")||domainValue[i].equals("-4")){ %>bgcolor=#eb6878;<%} %>><%=domainValue[i] %></td>
		<%	}else{ %>
		<td height="50px" width="20%" align="center" class="summaryEntry">no data</td>
<%			}
		} %>
	</tr>
<%				}
			}
		}
	} %>
<%										}
									}
								}
							}
						}
					}
				}
			} %>
</table>		
<%			}
		}
	}
}
} %>

</div>

</body>
</html>