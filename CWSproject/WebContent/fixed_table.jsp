<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

	<title>Table</title>
	<style type="text/css">
		.table{
			width: 100%;
			border-collapse:collapse; 
			border-spacing:0; 
		}
		.fixedThead{
			display: block;
			width: 100%;
		}
		.scrollTbody{
			display: block;
			height: 262px;
			overflow: auto;
			width: 100%;
		}
		.table td,.table th {
			width: 200px;
			border-bottom: none;
			border-left: none;
			border-right: 1px solid #CCC;
			border-top: 1px solid #DDD;
			padding: 2px 3px 3px 4px
		}
		.table tr{
			border-left: 1px solid #EB8;
			border-bottom: 1px solid #B74;
		}
		thead.fixedThead tr th:last-child {
			color:#FF0000;
			width: 218px;
		}
	</style>
</head>
<body >
	<table class="table">
		<thead class="fixedThead">
			<tr><th>header</th><th>header two</th></tr>
		</thead>
		<tbody class="scrollTbody">
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>
			<tr><td>love 1</td><td>love 2</td></tr>

		</tbody>
	</table>
</body>
</html>