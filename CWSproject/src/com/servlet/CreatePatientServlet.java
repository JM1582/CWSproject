package com.servlet;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.FormTemplateSQL;

/**
 * Servlet implementation class CreatePatientServlet
 */
public class CreatePatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreatePatientServlet() {
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
		
		PatientInfo patientInfo = new PatientInfo(null, -1);

		session.setAttribute("patientInfo", patientInfo);
		
		Map<Integer, FormTemplate> formTemplateMap = new HashMap<Integer, FormTemplate>();
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		try {
			formTemplateSQL.connect();
			formTemplateMap = formTemplateSQL.getAllFormTemplate();
			formTemplateSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		session.setAttribute("formTemplateMap", formTemplateMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_info_page.jsp");
		requestDispatcher.forward(request, response);	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
