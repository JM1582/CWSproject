package com.servlet;

import java.io.*;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class EditPatientServlet
 */
public class EditPatientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPatientServlet() {
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
		
		int patientInfoId = Integer.valueOf(request.getParameter("patientInfoId"));

		PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
		PatientInfo patientInfo = null;
		try {
			patientInfoSQL.connect();
			patientInfo = patientInfoSQL.getPatientInfo(patientInfoId);
			patientInfoSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg.DataBaseConnectionError(response);
			return;
		}
		
		Map<Integer, FormTemplate> formTemplateMap = new HashMap<Integer, FormTemplate>();
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		try {
			formTemplateSQL.connect();
			formTemplateMap = formTemplateSQL.getAllFormTemplate();
			formTemplateSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg.DataBaseConnectionError(response);
			return;
		}
		
		Map<Integer, CareProvider> allCareProviderMap = new HashMap<Integer, CareProvider>();
		UserSQL userSQL = new UserSQL();
		try {
			userSQL.connect();
			allCareProviderMap = userSQL.getAllCareProvider();
			userSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			ErrorMsg.DataBaseConnectionError(response);
			return;
		}
		
		if(patientInfo!=null){
			session.setAttribute("allCareProviderMap", allCareProviderMap); 
			session.setAttribute("formTemplateMap", formTemplateMap);
			session.setAttribute("patientInfo", patientInfo);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_info_page.jsp");
			requestDispatcher.forward(request, response);
		}else{
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('PatientInfo do not exist.');");
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
