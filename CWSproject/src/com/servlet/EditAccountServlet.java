package com.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class EditUserServlet
 */
public class EditAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		UserVerification userVerify = new UserVerification();
		if(!userVerify.isAdmin(session)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(userVerify.logout(session));
			requestDispatcher.forward(request, response);
			return;
		}
		
		int userId = Integer.valueOf(request.getParameter("userId"));

		UserSQL userSQL = new UserSQL();
		User account = null;
		try {
			userSQL.connect();
			account = userSQL.getUser(userId);
			userSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(account!=null){
			session.setAttribute("account", account);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("account_page.jsp");
			requestDispatcher.forward(request, response);
		}else{
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Account do not exist.');");
			out.println("location='account_management_servlet';");
			out.println("</script>");
			
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
