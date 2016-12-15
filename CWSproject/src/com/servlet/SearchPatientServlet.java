package com.servlet;

import java.io.*;
import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

public class SearchPatientServlet extends HttpServlet{
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String CWSNumber = request.getParameter("CWSNumber");
		
		HttpSession session = request.getSession();
		
		UserVerification userVerify = new UserVerification();
		if(!userVerify.isLogin(session)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(userVerify.logout(session));
			requestDispatcher.forward(request, response);
			return;
		}

		PatientInfo patientInfo = null;
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
		if (patientInfo != null){
			session.setAttribute("patientInfo", patientInfo);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("view_patient_summary_servlet?CWSNumber="+patientInfo.getCWSNumber());
			requestDispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('No match');");
			out.println("location='profile_page.jsp';");
			out.println("</script>");
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		doGet(request,response);
	}
}
