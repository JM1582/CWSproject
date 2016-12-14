package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.*;

/**
 * Servlet implementation class SaveActionPlanServlet
 */
public class SaveActionPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveActionPlanServlet() {
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
		Map allDomainMap = formTemplate.getAllDomainMap();
		
		ActionPlan actionPlan = (ActionPlan) session.getAttribute("actionPlan");
		if(actionPlan.getId()==-1){
			DocumentSQL documentSQL = new DocumentSQL();
			actionPlan.setId(documentSQL.fakeGetNewDocumentId());
		}

		if(!careProvider.getUserName().equals(actionPlan.getAuthor().getUserName())){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('You can not save, because you are not the author of this action plan.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
		} else if(actionPlan.getSign()){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Action plan already signed, can not be edit.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
		} else{
			Map actionEntryMap = actionPlan.getActionEntryMap();
			if(actionEntryMap!=null){
				Iterator actionEntryIt = actionEntryMap.keySet().iterator();
				while(actionEntryIt.hasNext()){
					String actionEntryId = (String) actionEntryIt.next(); 
					ActionEntry actionEntry = (ActionEntry) actionEntryMap.get(actionEntryId);
					String domainId = request.getParameter("domain_"+actionEntryId);
					actionEntry.setDomain((Domain) allDomainMap.get(domainId));
					actionEntry.setCscore(request.getParameter("cScore_"+actionEntryId));
					actionEntry.setFscore(request.getParameter("fScore_"+actionEntryId));
					Map actionMap = actionEntry.getActionMap();
					if(actionMap!=null){
						Iterator actionIt = actionMap.keySet().iterator();
						while(actionIt.hasNext()){
							String actionId = (String) actionIt.next();
							Action action = (Action) actionMap.get(actionId);
							action.setIntervention(request.getParameter("intervention_"+actionEntry.getId()+"_"+action.getId()));
							String userName = request.getParameter("responsibility_"+actionEntry.getId()+"_"+action.getId());
							if(userName!=null && !userName.equals("")){
								FakeSQL fakeSQL = new FakeSQL();
								CareProvider tmpCareProvidre = fakeSQL.getUser(userName).toCareProvider();
								action.setCareProvider(tmpCareProvidre); 
							}
						}
					}
				}
			}
			patientInfo.addActionPlan(actionPlan);
		}
		if(request.getParameter("sign") != null){
			if(actionPlan.getSign()){
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Action Plan already signed.');");
				out.println("location='action_plan_page.jsp';");
				out.println("</script>");
				
			} else {
				actionPlan.setSign(true);
				
				session.setAttribute("document", actionPlan);
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Action plan signed by "+careProvider.getTitle()+" "+careProvider.getFirstName()+" "+careProvider.getLastName()+"');");
				out.println("location='action_plan_page.jsp';");
				out.println("</script>");
			}
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Action plan Saved.');");
			out.println("location='action_plan_page.jsp';");
			out.println("</script>");
		}
		
		session.setAttribute("patientInfo", patientInfo);
		session.setAttribute("actionPlan", actionPlan);
		session.setAttribute("allDomainMap", allDomainMap);
		
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("action_plan_page.jsp");
		//requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
