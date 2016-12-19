package com.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class SaveAccountServlet
 */
public class SaveAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveAccountServlet() {
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
		
		User account = (User) session.getAttribute("account");
		
		account.setType(UserType.valueOf(request.getParameter("userType")));
		account.setUserName(request.getParameter("userName"));
		account.setPassWord(request.getParameter("passWord"));
		account.setTitle(request.getParameter("title"));
		account.setFirstName(request.getParameter("firstName"));
		account.setLastName(request.getParameter("lastName"));
		account.setEmail(request.getParameter("email"));
		account.setFacility(request.getParameter("facility"));
		
		UserSQL userSQL = new UserSQL();
		try {
			userSQL.connect();
			account = userSQL.setUser(account);
			userSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		};
		
		session.setAttribute("account", account);

		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Account Saved.');");
		out.println("location='account_page.jsp';");
		out.println("</script>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
