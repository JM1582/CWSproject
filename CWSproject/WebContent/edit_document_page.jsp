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
</style>
</head>

<body class="grayblue" >
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
FormTemplate formTemplate = patientInfo.getFormTemplate();
Map documentMap = patientInfo.getDocumentMap();
Document document = (Document)session.getAttribute("document");
Map domainValueMap = document.getDomainValueMap();
boolean editDisabled = false;
if (document.getSign()){
	editDisabled = true;
} else if (!careProvider.getUserName().equals(document.getAuthor().getUserName())){
	editDisabled = true;
}
 %>

<div id="header">
	<h3>CWS No.: <%=patientInfo.getCWSNumber() %>     <img src="cws_icon<%=(Integer)patientInfo.getIcon() %>.png" width="50" height="50"></h3>
</div>

<table><tr>

<td valign="top" width="15%" >
<div id="nav">
<ul>
  <li><a href="view_patient_summary_servlet?CWSNumber=<%=patientInfo.getCWSNumber() %>">SUMMARY VIEW</a></li>
  <li><a onclick="elementDisplay('document_list')">PROVIDER INPUT</a>
  	<ul id="document_list" class="sublist">
<%if(documentMap != null){
	Iterator it = documentMap.keySet().iterator();
	while(it.hasNext()){
		int documentId = (Integer)it.next();
		Document tmp_document = (Document)documentMap.get(documentId);
		if (tmp_document != null) { %>
	<li><a <%if(documentId==document.getDocumentId()){ %>class="active"<%} %> href="edit_document_servlet?documentId=<%=String.valueOf(tmp_document.getDocumentId()) %>"><small>
	<%=tmp_document.getDate() %>:<br><%=tmp_document.getAuthor().getFirstName() %> <%=tmp_document.getAuthor().getLastName() %></small></a></li>
<%		}
	}
} %>
  	<li><a href="create_document_servlet" ><small>New Document</small></a></li>
  	</ul>
  </li>
  <li><a href="view_action_plan_servlet">ACTION PLAN</a></li>
</ul>
</div>
</td>

<td valign="top" width="85%" >
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
			int additionColSpan = 1;
			if(scalarValue.length!=1){
				additionColSpan = 0;
			}
%>
<img id="collapse" align="left" onclick="elementDisplay('part_<%=part.getPartId() %>')" src="collapse.png" width="15" height="15" ><br>
<table id="part_<%=part.getPartId() %>" border="1" width="100%"><!-- change table format -->
	<tr><!-- The first row to show the part name and scalarName -->
		<td colspan="3" width="100%" ><strong><%=part.getPartName() %></strong><br><br><%=part.getPartDescription() %></td>
		<%for(int i=0;i<scalarName.length;i++){ %>
			 <th width="10" height="130"><span><small><%=scalarName[i] %></small></span></th>
		<%} %>
	</tr>
	
	<%for(int i=0;i<scalarValue.length;i++){ %>
	<tr><!-- The row(s) to show the scalar -->
		<td colspan="<%=2+additionColSpan %>"></td>
		<%if(additionColSpan==0){
			if(i==0){ %>
		<td class="width_for_2" >Performance</td>
<%			} else if(i==1){ %>
		<td class="width_for_2" >Capacity</td>
<%			}
		}
		for(int j=0;j<scalarValue[0].length;j++){ %>
			<td><%=scalarValue[i][j] %></td>
		<%} %>
	</tr>
	
	<%} 
	TreeMap subSetMap = new TreeMap(part.getSubSetMap());
	if(subSetMap != null){
		Iterator subSetIt = subSetMap.keySet().iterator();
		while(subSetIt.hasNext()){
			String subSetId = (String)subSetIt.next();
			SubSet subSet = (SubSet)subSetMap.get(subSetId);
			if(subSet != null){%>
	<!-- The row to show subset title -->
	<tr><td colspan="<%=3+scalarName.length %>">
		<a onclick="elementDisplay('subset_<%=subSet.getSubSetId() %>')">
		<%=subSet.getSubSetId() %>. <%=subSet.getSubSetName() %>
		</a>
	</td></tr>
	
	
	<%TreeMap domainMap = new TreeMap(subSet.getDomainMap());
	if(domainMap != null){
		Iterator domainIt = domainMap.keySet().iterator();
		while(domainIt.hasNext()){
			String domainId = (String)domainIt.next();
			Domain domain = (Domain)domainMap.get(domainId);
			if(domain != null){
				String domainValue[] = null;
				if(domainValueMap.containsKey(domain.getDomainId())){
					domainValue = (String[])domainValueMap.get(domain.getDomainId());
				}
				for(int i=0;i<scalarValue.length;i++){ %>
	<tr id="subset_<%=subSet.getSubSetId() %>" ><!-- domain row -->
	<%if(i==0){ %>
		<td rowspan="<%=scalarValue.length %>" width="5%"><%=domain.getDomainId() %></td>
		<td rowspan="<%=scalarValue.length %>" colspan="<%=additionColSpan+1 %>" ><%=domain.getDomainName() %></td>
	<%}
	if(additionColSpan==0){
		if(i==0){ %>
		<td class="width_for_2" >Performance</td>
<%			} else if(i==1){ %>
		<td class="width_for_2" >Capacity</td>
<%			}
	}
		for(int j=0;j<scalarValue[0].length;j++){  %>
		<td>
		<input type="radio" name="<%=domain.getDomainId()+'_'+Integer.toString(i) %>" value="<%=scalarValue[i][j] %>"/ 
		<%if(domainValue!=null){ if(domainValue[i].equals(scalarValue[i][j])){  %>checked<%} } %> <%if(editDisabled){ %>disabled=disabled<%} %>>
		</td>
		<%} %>
	</tr>
	<%} %>
	<!-- 
	<tr>
		<td style="width: 92px; ">Comment</td>
		<td colspan="<%=3+scalarName.length %>"></td>
	</tr>
	 -->
	<%} } } } } } %>
</table>
<%} } } %>
</div>
</form>
</td>

</tr></table>

</body>
</html>