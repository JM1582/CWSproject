package com.servlet;

import java.io.*;

import javax.servlet.http.*;

public class ErrorMsg {
	public static void DataBaseConnectionError(HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Database connection failed, please retry.');");
		out.println("location='login_page.jsp';");
		out.println("</script>");
	}

}
