package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;


public class EditActionPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EditActionPlanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		UserVerification userVerify = new UserVerification();
		if(!userVerify.isLogin(session)) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(userVerify.logout(session));
			requestDispatcher.forward(request, response);
			return;
		}
		
		String actionPlanIdStr = (String) request.getParameter("actionPlanId");
		int actionPlanId = Integer.valueOf(actionPlanIdStr);
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		Map<Integer, ActionPlan> actionPlanMap = patientInfo.getActionPlanMap();
		ActionPlan actionPlan = (ActionPlan) actionPlanMap.get(actionPlanId);
		
		FormTemplate formTemplate = patientInfo.getFormTemplate();
		Map<String, Domain> allDomainMap = formTemplate.getAllDomainMap();
		
		session.setAttribute("allDomainMap", allDomainMap);
		
		if ( actionPlan != null) {
			session.setAttribute("actionPlan", actionPlan);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("action_plan_page.jsp");
			requestDispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Action plan do not exist.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
