package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.CareProvider;
import com.model.FormTemplate;
import com.model.PatientInfo;
import com.sql.FormTemplateSQL;
import com.sql.PatientInfoSQL;
import com.sql.UserSQL;

/**
 * Servlet implementation class RemoveCareProviderServlet
 */
@WebServlet("/RemoveCareProviderServlet")
public class RemoveCareProviderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveCareProviderServlet() {
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
		
		String removedUserName = request.getParameter("removedUserName");
		CareProvider careProvider = new CareProvider(removedUserName, null);
		patientInfo.removeCareProvider(careProvider);
		
		session.setAttribute("patientInfo", patientInfo);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("patient_info_page.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
