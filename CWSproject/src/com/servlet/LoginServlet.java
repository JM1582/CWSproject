package com.servlet;
import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

public class LoginServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String userName = request.getParameter("userName");
		String passWord = request.getParameter("passWord");
		
		HttpSession session = request.getSession();
		UserSQL userSQL = new UserSQL();
		
		try {
			userSQL.connect();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Database connection failed, please retry.');");
			out.println("location='login_page.jsp';");
			out.println("</script>");
			return;
		}
		User user = userSQL.userLogin(new User(userName, passWord));
		userSQL.disconnect();
		if (user != null){
			if(user.getType()==UserType.ADMIN){
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Admin page not ready.');");
				out.println("location='login_page.jsp';");
				out.println("</script>");
				return;
			} else if(user.getType()==UserType.CAREPROVIDER) {
				CareProvider careProvider = user.toCareProvider();
				session.setAttribute("user", careProvider);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile_page.jsp");
				requestDispatcher.forward(request, response);
				return;
			}
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Username or password incorrect');");
			out.println("location='login_page.jsp';");
			out.println("</script>");
			return;
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request,response);
	}

}
