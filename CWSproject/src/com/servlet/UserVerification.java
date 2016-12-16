package com.servlet;

import javax.servlet.http.HttpSession;

import com.model.*;

public class UserVerification {
	
	public boolean isLogin(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null){
			return true;
		}
		return false;
	}

	public boolean isAdmin(HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null && user.getType()==UserType.ADMIN){
			return true;
		} 
		return false;
	}
	
	public String logout(HttpSession session){
		session.setAttribute("user", null);
		session.setAttribute("document", null);
		session.setAttribute("patientInfo", null);
		session.setAttribute("formTemplate", null);
		session.setAttribute("actionPlan", null);
		session.setAttribute("summaryMap", null);
		session.setAttribute("allDomainMap", null);
		return "logout_page.jsp";
	}
}
