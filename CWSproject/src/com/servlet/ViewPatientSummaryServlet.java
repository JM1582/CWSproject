package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

public class ViewPatientSummaryServlet extends HttpServlet{
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
			PatientInfoSQL patientInforSQL = new PatientInfoSQL();
			patientInfo = patientInforSQL.fakeSearchPatient(CWSNumber);
		}
		
		if (patientInfo != null) {
			session.setAttribute("patientInfo", patientInfo);
			Map summaryMap = new HashMap();
			Map documentMap = patientInfo.getDocumentMap();
			Iterator it = documentMap.keySet().iterator();
			while(it.hasNext()){
				int documentId = (Integer) it.next();
				Document documentFromPatientInfo = (Document) documentMap.get(documentId);
				String userName = documentFromPatientInfo.getAuthor().getUserName();
				Document documentFromSummary = (Document) summaryMap.get(userName);
				if(documentFromSummary == null){
					summaryMap.put(userName, documentFromPatientInfo);
				} else {
					if(documentFromPatientInfo.laterThan(documentFromSummary)){
						summaryMap.put(userName, documentFromPatientInfo);
					}
				}
			}
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
