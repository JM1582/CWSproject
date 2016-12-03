package com.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;

public class EditDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EditDocumentServlet() {
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
		
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		Integer documentId = Integer.valueOf(request.getParameter("documentId"));
		Document document = (Document) patientInfo.getDocumentMap().get(documentId);
		
		if (document != null) {
			session.setAttribute("document", document);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_document_page.jsp");
			requestDispatcher.forward(request, response);
		} else {
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Document do not exist.');");
			out.println("location='documents_page.jsp';");
			out.println("</script>");
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
