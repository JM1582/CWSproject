package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;

/**
 * Servlet implementation class AddDomainServlet
 */
public class AddActionEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddActionEntryServlet() {
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
		
		CareProvider careProvider = (CareProvider) session.getAttribute("user");
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		FormTemplate formTemplate = patientInfo.getFormTemplate();
		Map<String, Domain> allDomainMap = formTemplate.getAllDomainMap();
		
		ActionPlan actionPlan = (ActionPlan) session.getAttribute("actionPlan");
		
		if(!careProvider.getUserName().equals(actionPlan.getAuthor().getUserName())){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('You can not edit, because you are not the author of this action plan.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
			return;
		} else if(actionPlan.getSign()){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Action plan already signed, can not be edit.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
			return;
		} else{
			Map<Integer, ActionEntry> actionEntryMap = actionPlan.getActionEntryMap();
			int actionEntryId = -1;
			while(actionEntryMap.containsKey(actionEntryId)){
				actionEntryId+=-1;
			}
			ActionEntry actionEntry = new ActionEntry(actionEntryId);
			Map<Integer, Action> actionMap = actionEntry.getActionMap();
			int actionId=-1;
			while(actionMap.containsKey(actionId)){
				actionId+=-1;
			}
			Action action = new Action(actionId);
			actionEntry.addAction(action);
			actionEntry.setActionMap(actionMap);
			actionPlan.addActionEntry(actionEntry);
			/* add action entry would not save action plan
			if(actionPlan.getActionPlanId()!=-1){
				patientInfo.addActionPlan(actionPlan);
			}
			*/
			session.setAttribute("patientInfo", patientInfo);
			session.setAttribute("actionPlan", actionPlan);
			session.setAttribute("allDomainMap", allDomainMap);
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("action_plan_page.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
