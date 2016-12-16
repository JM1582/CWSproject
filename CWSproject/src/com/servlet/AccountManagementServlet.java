package com.servlet;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.UserSQL;

/**
 * Servlet implementation class AccountManagementServlet
 */
public class AccountManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountManagementServlet() {
        super();
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

		Admin admin = (Admin) session.getAttribute("user");
		
		Map<Integer, User> userMap = new HashMap<Integer, User>();
		
		UserSQL userSQL = new UserSQL();
		try {
			userSQL.connect();
			userMap = userSQL.getAllUser();
			userSQL.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		session.setAttribute("userMap", userMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("account_management_page.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
