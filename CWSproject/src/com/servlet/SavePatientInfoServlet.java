package com.servlet;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class SavePatientInfoServlet
 */
public class SavePatientInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SavePatientInfoServlet() {
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
		
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		
		if(request.getParameter("CWSNumber")==null ){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('CWSNumber cannot be empty.');");
			out.println("location='account_page.jsp';");
			out.println("</script>");
			return;
		}
		
		patientInfo.setCWSNumber(request.getParameter("CWSNumber"));
		patientInfo.setIcon(Integer.valueOf(request.getParameter("icon")));
		
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		FormTemplate formTemplate = null;
		try {
			formTemplateSQL.connect();
			int formTemplateId = Integer.valueOf(request.getParameter("formTemplateId"));
			formTemplate= formTemplateSQL.getFormTemplate(formTemplateId);
			formTemplateSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Database connection failed!');");
			out.println("location='patient_info_page.jsp';");
			out.println("</script>");
			return;
		}
		
		patientInfo.setFormTemplate(formTemplate);
		
		PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
		try {
			patientInfoSQL.connect();
			patientInfoSQL.setCareProviderMap(patientInfo);
			patientInfoSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Patient saving failed!');");
			out.println("location='patient_info_page.jsp';");
			out.println("</script>");
			return;
		}
		
		session.setAttribute("patientInfo", patientInfo);

		PrintWriter out = response.getWriter();
		out.println("<script type=\"text/javascript\">");
		out.println("alert('Patient Saved.');");
		out.println("location='patient_info_page.jsp';");
		out.println("</script>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
