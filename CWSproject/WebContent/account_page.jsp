<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Account Page</title>
<link rel="stylesheet" type="text/css" href="css/buttons.css" charset="utf-8" >

</head>
<body>
<!-- login verification, should always be in beginning -->
<%Admin admin = (Admin)session.getAttribute("user");
if(admin == null || admin.getType()!=UserType.ADMIN){ %>
	<script language="javascript" type="text/javascript">
		window.location.href='logout_servlet';
	</script>
<%	return;
}
User account = (User) session.getAttribute("account");
%>

<div id=Content-Left3 align="right" >
<button class="button_logout" type="button"  onclick="location.href='admin_page.jsp'" >Home</button>
<button class="button_logout" type="button"  onclick="location.href='account_management_servlet'" >Back</button>
<button class="button_logout" type="button"  onclick="location.href='logout_servlet'">Logout</button>
</div>

<form id="acountForm" action="save_account_servlet">
<input type="submit" value="Save" class="button1">
<table>
	<tr>
		<td>User ID</td>
		<td>User Type</td>
		<td>User Name</td>
		<td>Password</td>
		<td>Position</td>
		<td>Fist Name</td>
		<td>Last Name</td>
		<td>E-mail</td>
		<td>Facility</td>
		<td></td>
	</tr>
	<tr>
		<td><%=Integer.toString(account.getId()) %></td>
		<td><select name="userType">
			<%for(int i=0; i<UserType.values().length;i++){
				String userType = null;
				if(UserType.values()[i]==UserType.ADMIN){
					userType = "Admin";
				} else if (UserType.values()[i]==UserType.CAREPROVIDER){
					userType = "Care Provider";
				} else if (UserType.values()[i]==UserType.PATIENT){
					userType = "Patient";
				} %>
				<option <%if(account.getType()==UserType.values()[i]){ %>selected<%} %> value="<%=UserType.values()[i] %>" >
					<%=userType %>
				</option>
			<%} %>
		</select></td>
		<td><input type="text" name="userName" 
			<%if(account.getUserName()!=null){ %>value="<%=account.getUserName() %>"<%} %>></td>
		<td><input type="text" name="passWord"
			<%if(account.getPassWord()!=null){ %>value="<%=account.getPassWord() %>"<%} %>></td>
		<td><input type="text" name="title"
			<%if(account.getTitle()!=null){ %>value="<%=account.getTitle() %>"<%} %>></td>
		<td><input type="text" name="firstName"
			<%if(account.getFirstName()!=null){ %>value="<%=account.getFirstName() %>"<%} %>></td>
		<td><input type="text" name="lastName"
			<%if(account.getLastName()!=null){ %>value="<%=account.getLastName() %>"<%} %>></td>
		<td><input type="text" name="email"
			<%if(account.getEmail()!=null){ %>value="<%=account.getEmail() %>"<%} %>></td>
		<td><input type="text" name="facility"
			<%if(account.getFacility()!=null){ %>value="<%=account.getFacility() %>"<%} %>></td>
	</tr>
</table>
</form>

</body>
</html>