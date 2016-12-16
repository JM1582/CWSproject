<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account Management Page</title>
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >
</head>
<body>
<!-- login verification, should always be in beginning -->
<%Admin admin = (Admin)session.getAttribute("user");
if(admin == null || admin.getType()!=UserType.ADMIN){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
<%return;
} %>

<div id=Content-Left3 align="right" >
<button class="button_logout" type="button"  onclick="location.href='admin_page.jsp'" >Home</button>
<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div>

<table>
	<tr>
		<td>User ID</td>
		<td>User Type</td>
		<td>User Name</td>
		<td>Position</td>
		<td>Fist Name</td>
		<td>Last Name</td>
		<td>E-mail</td>
		<td>Facility</td>
		<td></td>
	</tr>
<%Map<Integer, User> userMap = (Map<Integer, User>) session.getAttribute("userMap");
Iterator<Integer> userIt = userMap.keySet().iterator();
while (userIt.hasNext()){
	int userId = (Integer) userIt.next();
	User user = userMap.get(userId);
	String userType = "Unknow";
	if(user.getType()==UserType.ADMIN){
		userType = "Admin";
	} else if (user.getType()==UserType.CAREPROVIDER){
		userType = "Care Provider";
	} else if (user.getType()==UserType.PATIENT){
		userType = "Patient";
	}
	%>
	<tr>
		<td><%=user.getId() %></td>
		<td><%=userType %></td>
		<td><%=user.getUserName() %></td>
		<td><%=user.getTitle() %></td>
		<td><%=user.getFirstName() %></td>
		<td><%=user.getLastName() %></td>
		<td><%=user.getEmail() %></td>
		<td><%=user.getFacility() %></td>
		<td><button type="button"  onclick="location.href='edit_user_servlet?userId=<%=user.getId() %>'">Edit</button></td>
	</tr>
<%} %>
</table>

</body>
</html>