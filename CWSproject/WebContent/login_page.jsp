<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>Login Page</title>
<link rel="stylesheet" type="text/css" href="css/nav_bar.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_formats.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/background.css" charset="utf-8" >
<link rel="stylesheet" type="text/css" href="css/div_left_right.css" charset="utf-8" >
<script type="text/javascript" src="collapse.js" ></script>

</head>
<body class="picLUp2">
<div class = "hidden_above"></div>

        <div class="black2" align = "center">
        <form name="loginForm" method="post" action="login_servlet" onSubmit="return submitcheck()"  >
        <br><br><br><br>
 		<font color=white align="left" size = "6"> User name: </font>
 		
		<input type="text" name="userName" style="width:223px; height:30px;" >
		<br><br>
 	&nbsp;<font color="white" align="left" size = "6"> Password: </font>
		<input type="password" name="passWord" style="width:223px; height:30px;">
		<br><br><br>
			&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
			<input type="submit" value="Submit" 
				style="width:120px ;  font:Brandon; font-size:18px; color:#314a51;  
						background:#99d3e1 "  >
			
			&nbsp;
			<input type="reset" value="Reset" 
				style="width:120px;  font:Brandon; font-size:18px; color:#314a51; 
						background:#99d3e1 "/>		
						<br><br><br><br>	 
		</form>
		<button style="font:Brandon; font-size:18px; color:#314a51;background:#99d3e1;"
		type="button"  onclick="location.href='forget_password_page.jsp'" >Forget Password</button>

		<!--Footer --->
</div>


<!-- 
<div style="position:absolute; bottom:0; position:fixed;"><p style: color = blue; align="right";>On this webpage, image's copyright is TBD</p></body></div>-->
<!-- this footer does not support for IE6 -->
</html>