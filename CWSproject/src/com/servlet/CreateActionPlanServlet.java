package com.servlet;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;

/**
 * Servlet implementation class CreateActionPlanServlet
 */
public class CreateActionPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateActionPlanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		UserVerification userVerify = new UserVerification();
		if(!userVerify.isLogin(session)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(userVerify.logout(session));
			requestDispatcher.forward(request, response);
			return;
		}
		
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		CareProvider careProvider = (CareProvider) session.getAttribute("user");
		
		ActionPlan actionPlan = new ActionPlan(patientInfo.getCWSNumber(), careProvider);
		actionPlan.setDateToday();
		session.setAttribute("actionPlan", actionPlan);
		
		FormTemplate formTemplate = patientInfo.getFormTemplate();
		Map allDomainMap = formTemplate.getAllDomainMap();
		session.setAttribute("allDomainMap", allDomainMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("action_plan_page.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
