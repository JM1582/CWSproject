package com.servlet;

import java.io.*;
import java.sql.SQLException;
import java.util.Iterator;

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
		
		String CWSNumber = request.getParameter("CWSNumber");
		if(CWSNumber==null || CWSNumber.equals("")){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('CWSNumber cannot be empty.');");
			out.println("location='patient_info_page.jsp';");
			out.println("</script>");
			return;
		}
		
		int icon = -1;
		String iconStr = (String) request.getParameter("icon");
		if(iconStr!=null && !iconStr.equals("")){
			icon = Integer.valueOf(iconStr);
		}
		
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		FormTemplate formTemplate = null;
		String formTemplateIdStr = request.getParameter("formTemplateId");
		if(formTemplateIdStr!=null && !formTemplateIdStr.equals("")){
			int formTemplateId = Integer.valueOf(formTemplateIdStr);
			try {
				formTemplateSQL.connect();
				formTemplate= formTemplateSQL.getFormTemplate(formTemplateId);
				formTemplateSQL.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				ErrorMsg.DataBaseConnectionError(response, "patient_info_page.jsp");
				return;
			}
		}

		patientInfo.setCWSNumber(CWSNumber);
		patientInfo.setIcon(icon);
		patientInfo.setFormTemplate(formTemplate);

		session.setAttribute("patientInfo", patientInfo);
		
		Iterator<String> careProviderIt = patientInfo.getCareProviderMap().keySet().iterator();
		while(careProviderIt.hasNext()){
			String userName = (String) careProviderIt.next();
			if(request.getParameter("remove_"+userName) != null){
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("remove_care_provider_servlet?removedUserName="+userName);
				requestDispatcher.forward(request, response);
				return;
			}
		}
		
		if(request.getParameter("add") != null){
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("add_care_provider_servlet");
			requestDispatcher.forward(request, response);
		}else{
			PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
			try {
				patientInfoSQL.connect();
				int existPatientInfoId = patientInfoSQL.getPatientIdByCWSNumber(patientInfo.getCWSNumber());
				if(existPatientInfoId!=patientInfo.getId()){
					ErrorMsg.CWSNumberAlreadyTakenError(response, "patient_info_page.jsp");
					return;
				}
				patientInfo = patientInfoSQL.setPatientInfo(patientInfo);
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
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Patient Saved.');");
			out.println("location='patient_info_page.jsp';");
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
