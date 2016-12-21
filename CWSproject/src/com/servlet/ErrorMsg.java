package com.servlet;

import java.io.*;

import javax.servlet.http.*;

public class ErrorMsg {
	public static void DataBaseConnectionError(HttpServletResponse response, String location) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Database connection failed, please retry.');");
		out.println("location='"+location+"';");
		out.println("</script>");
	}
	

	public static void NullCareProviderError(HttpServletResponse response, String location) throws IOException{
		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Please select one care provider to add.');");
		out.println("location='"+location+"';");
		out.println("</script>");
	}
}
