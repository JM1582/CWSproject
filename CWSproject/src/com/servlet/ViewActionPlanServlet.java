package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;


public class ViewActionPlanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public ViewActionPlanServlet() {
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
		
		String actionPlanIdStr = (String) session.getAttribute("actionPlanId");
		int actionPlanId = Integer.valueOf((String) session.getAttribute("actionPlanId"));
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		Map actionPlanMap = patientInfo.getActionPlanMap();
		ActionPlan actionPlan = (ActionPlan) actionPlanMap.get(actionPlanId);
		Map allDomainMap = new HashMap();
		
		FormTemplate formTemplate = patientInfo.getFormTemplate();
		Map partsMap = formTemplate.getPartsMap();
		Iterator partIt = partsMap.keySet().iterator();
		while(partIt.hasNext()){
			String partId = (String) partIt.next();
			OnePart part = (OnePart) partsMap.get(partId);
			Map subSetMap = part.getSubSetMap();
			Iterator subSetIt = subSetMap.keySet().iterator();
			while(subSetIt.hasNext()){
				String subSetId = (String) subSetIt.next();
				SubSet subSet = (SubSet) subSetMap.get(subSetId);
				Map domainMap = subSet.getDomainMap();
				Iterator domainIt = domainMap.keySet().iterator();
				while(domainIt.hasNext()){
					String domainId = (String) domainIt.next();
					Domain domain = (Domain) domainMap.get(domainId);
					allDomainMap.put(domainId, domain);
				}
			}
		}
		
		session.setAttribute(Integer.toString(actionPlanId), actionPlan);
		session.setAttribute("allDomainMap", allDomainMap);
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("action_plan_page.jsp");
		requestDispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
