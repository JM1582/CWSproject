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
<link rel="stylesheet" type="text/css" href="documents.css">
<link rel="stylesheet" type="text/css" href="buttons.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="font_styles.css" charset="utf-8" >
<script type="text/javascript" src="collapse.js" ></script>
<style type="text/css">
th {
  border: 1px solid #000;
  position: relative;
  padding: 10px;
}

th span {
  transform-origin: 0 50%;
  transform: rotate(-90deg); 
  white-space: nowrap; 
  display: block;
  position: absolute;
  bottom: 0;
  left: 50%;
}

.width_for_2{
	width: 50px;
	width: 10%;
}

.scalar_colume{
	width: 3%;
}

</style>

</head>

<body class="grayblue" >
<!-- banner 
<div class = "backgroundwhite">
<div class = "green1">
<h1 ><font face = Brandon size = "11" color = "white" ><p align="center">Collaborative Workflow Solutions</p></font></h1>
</div>
<!-- the div to hold 2 buttons: close file and logout
<div align="right">
	<button type="button"  onclick="location.href='profile_page.jsp'">Close File</button>
	<button type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div></div>
 banner end -->
<div align="right" class="banner2" >
<div class="hidden_above60" ><br>
	<button class="button_logout" type="button"  onclick="location.href='profile_page.jsp'" >Close File</button>
	<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>&emsp;&emsp;
</div></div>



<!-- login verification -->
<%CareProvider careProvider = (CareProvider)session.getAttribute("user");
if(careProvider == null){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
	<%return;
} %>

<%PatientInfo patientInfo = (PatientInfo)session.getAttribute("patientInfo");
FormTemplate formTemplate = patientInfo.getFormTemplate();
Map documentMap = patientInfo.getDocumentMap();
Map actionPlanMap = patientInfo.getActionPlanMap();
Document document = (Document)session.getAttribute("document");
Map domainValueMap = document.getDomainValueMap();
boolean editDisabled = false;
if (document.getSign()){
	editDisabled = true;
} else if (!careProvider.getUserName().equals(document.getAuthor().getUserName())){
	editDisabled = true;
} %>


 <div>
<div class="header">
	<p class="engrave60">&nbsp;<img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="80" height="80">
	<strong><%=patientInfo.getCWSNumber() %></strong></p>
</div>
<br><br><br><br><br><br><br>
</div>

<!-- the table to hold nav bar in left and document in right -->
<table><tr>

<!-- the table cell to hold nav bar -->
<td valign="top" width="15%" >

<!-- navigation bar -->
<div id="nav">
<br>
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a class="active" id="provider_input" onclick="expand('document_list')">PROVIDER INPUT</a>
  	<ul id="document_list" class="sublist_collapse">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document tmpDocument = (Document)documentMap.get(documentId);
		if (tmpDocument != null) { %>
	<li><a <%if(documentId==document.getDocumentId()){ %>class="active"<%} %> href="edit_document_servlet?documentId=<%=String.valueOf(tmpDocument.getDocumentId()) %>"><small>
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
		if (tmpActionPlan != null) { %>
	<li><a href="view_action_plan_servlet?actionPlanId=<%=Integer.toString(tmpActionPlan.getActionPlanId()) %>"><small>
		<%=tmpActionPlan.getDateOnly() %>:<br>
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

<!-- the table cell to hold the document -->
<td valign="top" width="85%" >
<div style="background:#e5e8d5;">
<h2 class="form_instruction"> &nbsp;Please fill out the form base on your knowledge about the patient.</h2>
<form name="documentForm" action="save_document_servlet">

<div align="right">
<!-- <button type="button" onclick="location.href='sign_document_servlet'" >Sign</button> -->
<input type="submit" name="sign" value="Sign">
<input type="submit" value="Save">
<!--<button type="button" onclick="location.href='create_document_servlet'" >New</button>  -->
</div>

<div id="document" >
<% TreeMap partMap = new TreeMap(formTemplate.getPartsMap());
if (partMap != null) {
	Iterator partIt = partMap.keySet().iterator();
	while(partIt.hasNext()){
		String partId = (String)partIt.next();
		OnePart part = (OnePart)partMap.get(partId);
		if(part != null){
			String scalarName[] = part.getScalarName();
			String scalarValue[][] = part.getScalarValue();
			int scalarValueAmount = scalarValue.length;
%>

<!-- the table to hold 1 part -->
<table id="part_<%=part.getPartId() %>" width="100%" cellspacing="0" cellpadding="0"><!-- change table format -->
	
	<!-- the table to hold 1 part title -->
	<tr><td><table id="partTitle_<%=part.getPartId() %>" width="100%" border="1">
		<tr><!-- The first row to show the part name and scalarName -->
			<td>
				<img id="collapse_icon" align="left" onclick="collapse('partContent_<%=part.getPartId() %>')" src="collapse.png" width="15" height="15" >
				<strong><%=part.getPartName() %></strong><br><br><%=part.getPartDescription() %>
			</td>
			<%for(int i=0;i<scalarName.length;i++){ %>
			<th class="scalar_colume" height="150"><span><small><%=scalarName[i] %></small></span></th>
			<%} %>
		</tr>
		<%for(int i=0;i<scalarValue.length;i++){ %>
		<tr><!-- The second or third row to show the scalar -->
			<td></td>
			<%for(int j=0;j<scalarValue[0].length;j++){ %>
			<td class="scalar_colume" align="center"><%=scalarValue[i][j] %></td>
			<%} %>
		</tr>
		<%} %>
	</table></td></tr>
	
	<!-- the table to hold 1 part content -->
	<tr><td><table id="partContent_<%=part.getPartId() %>" width="100%" cellspacing="0" cellpadding="0" >
	<%TreeMap subSetMap = new TreeMap(part.getSubSetMap());
	if(subSetMap != null){
		Iterator subSetIt = subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String)subSetIt.next();
			SubSet subSet = (SubSet)subSetMap.get(subSetId);
			if(subSet != null){%>
		
		<!-- the table to hold 1 subset -->
		<tr><td><table id="subSet_<%=subSet.getSubSetId() %>" width="100%" cellspacing="0" cellpadding="0" >
			
			<!-- the table to hold subset title -->
			<tr><td><table id="subSetTitle_<%=subSet.getSubSetId() %>" width="100%" border="1">
				<!-- The row to show subset title -->
				<tr><td with="100%">
					<img id="collapse_icon" align="left" onclick="collapse('subSetContent_<%=subSet.getSubSetId() %>')" src="collapse.png" width="15" height="15" >
					<%=subSet.getSubSetName() %>
				</td></tr>
			</table></td></tr>
			
			<!-- the table to hold subset content -->
			<tr><td><table id="subSetContent_<%=subSet.getSubSetId() %>" width="100%" cellspacing="0" cellpadding="0">
			<%TreeMap domainMap = new TreeMap(subSet.getDomainMap());
			if(domainMap != null){
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String)domainIt.next();
					Domain domain = (Domain)domainMap.get(domainId); 
					if(domain != null){ %>
				
				<!-- the table to hold domain -->
				<tr><td><table id="domain_<%=domain.getDomainId() %>" width="100%" border="1">

<%					String domainValue[] = null;
					if(domainValueMap.containsKey(domain.getDomainId())){
						domainValue = (String[])domainValueMap.get(domain.getDomainId());
					}
					for(int i=0;i<scalarValue.length;i++){ %>
					<tr>
						<%if(i==0){ %>
							<!-- <td rowspan="<%=scalarValue.length %>" width="5%"><%=domain.getDomainId() %></td> -->
							<td rowspan="<%=scalarValue.length %>" ><%=domain.getDomainName() %></td>
						<%} %>
					
					<!-- label for scalar if scalar has 2 values -->
						<%if(scalarValueAmount==2){
							if(i==0){ %>
						<td class="width_for_2" >Performance</td>
						<%	} else if(i==1){ %>
						<td class="width_for_2" >Capacity</td>
						<%	}
						} %>
					
						<%for(int j=0;j<scalarValue[i].length;j++){  %>
						<td class="scalar_colume">
							<input type="radio" name="<%=domain.getDomainId()+'_'+Integer.toString(i) %>" value="<%=scalarValue[i][j] %>"/
							<%if(domainValue!=null){ if(domainValue[i].equals(scalarValue[i][j])){  %>checked<%} } %> <%if(editDisabled){ %>disabled=disabled<%} %>>
						</td>
					<%	}
					}%>
					</tr>
				</table></td></tr>
				
					<%}
				}
			} %>
			</table></td></tr>
		</table></td></tr>
			<%}
		}
	} %>
	</table></td></tr>
</table>
<br>
<%		}
	}
} %>
</div>
</form>
</div>
</td>

</tr></table>

</body>
</html>