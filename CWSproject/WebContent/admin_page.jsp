<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.model.*,java.util.*;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>admin page</title>
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

<div>
<button type="button"  onclick="location.href='account_management_servlet">Account Management</button>
<button type="button"  onclick="location.href='patient_management_servlet'">Patient Management</button>
<button type="button"  onclick="location.href='template_management_servlet'">Template Management</button>
<button type="button"  onclick="location.href='document_management_servlet'">Document Management</button>
<button type="button"  onclick="location.href='action_plan_management_servlet'">Action plan Management</button>
</div>

</body>
</html>