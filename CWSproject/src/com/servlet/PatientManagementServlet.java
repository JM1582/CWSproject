package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class PatientManagementServlet
 */
public class PatientManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientManagementServlet() {
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
		
		Map<Integer, PatientInfo> patientInfoMap = new HashMap<Integer, PatientInfo>();
		
		PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
		try {
			patientInfoSQL.connect();
			patientInfoMap = patientInfoSQL.getAllPatientInfo();
			patientInfoSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		session.setAttribute("patientInfoMap", patientInfoMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_management_page.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
