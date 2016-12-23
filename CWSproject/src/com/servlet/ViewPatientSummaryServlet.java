package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

public class ViewPatientSummaryServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String CWSNumber = request.getParameter("CWSNumber");

		HttpSession session = request.getSession();
		
		UserVerification userVerify = new UserVerification();
		if(!userVerify.isLogin(session)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(userVerify.logout(session));
			requestDispatcher.forward(request, response);
			return;
		}
		
		//!!!need to be changed
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		if(patientInfo==null){
			PatientInfoSQL patientInfoSQL = new PatientInfoSQL();
			try {
				patientInfoSQL.connect();
				patientInfo = patientInfoSQL.getPatientInfoByCWSNumber(CWSNumber);
				patientInfoSQL.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Database connection failed, please retry.');");
				out.println("location='login_page.jsp';");
				out.println("</script>");
				return;
			}
		}
		
		if (patientInfo != null) {
			session.setAttribute("patientInfo", patientInfo);
			Map<String, Map<String, String[]>> summaryMap = patientInfo.getSummaryMap();
			session.setAttribute("summaryMap", summaryMap);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("summary_view_page.jsp");
			requestDispatcher.forward(request, response);
		} else {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("profile_page.jsp");
			requestDispatcher.forward(request, response);
		}
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request,response);
	}
}
