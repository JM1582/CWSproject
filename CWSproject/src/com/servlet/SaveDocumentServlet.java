package com.servlet;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.model.*;
import com.sql.DocumentSQL;

public class SaveDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SaveDocumentServlet() {
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
		
		CareProvider careProvider = (CareProvider) session.getAttribute("user");
		PatientInfo patientInfo = (PatientInfo) session.getAttribute("patientInfo");
		Document document = (Document) session.getAttribute("document");
		
		if(!careProvider.getUserName().equals(document.getAuthor().getUserName())){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('You can not save, because you are not the author of this document.');");
			out.println("location='edit_document_page.jsp';");
			out.println("</script>");
			return;
		} else {
			if(document.getSign()){
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Document already signed, can not be edit.');");
				out.println("location='edit_document_page.jsp';");
				out.println("</script>");
				return;
			} else {
				document.setDateToday();
				document.setAuthor(careProvider);
				document.setCWSNumber(patientInfo.getCWSNumber());
				Map<String, OnePart> partMap = document.getFormTemplate().getPartsMap();
				Iterator<String> partIt = partMap.keySet().iterator();
				while(partIt.hasNext()){
					String partId = (String)partIt.next();
					OnePart part = (OnePart)partMap.get(partId);
					String scalarValue[][] = part.getScalarValue();
					Map<String, SubSet> subSetMap = part.getSubSetMap();
					Iterator<String> subSetIt = subSetMap.keySet().iterator();
					while(subSetIt.hasNext()){
						String subSetId = (String)subSetIt.next();
						SubSet subSet = (SubSet)subSetMap.get(subSetId);
						Map<String, Domain> domainMap = subSet.getDomainMap();
						Iterator<String> domainIt = domainMap.keySet().iterator();
						while(domainIt.hasNext()){
							String domainId = (String)domainIt.next();
							String[] domainValue = new String[scalarValue.length];
							for(int i=0;i<scalarValue.length;i++){
								if(request.getParameter(domainId+'_'+Integer.toString(i))!=null){
									domainValue[i] = request.getParameter(domainId+'_'+Integer.toString(i));
								} else {
									domainValue[i] = null;
								}
							}
							if (domainValue != null){
								document.addDomainValue(domainId, domainValue);
							}
						}
					}
				}
				if(request.getParameter("sign") != null){
					document.setSign(true);
				}

				DocumentSQL documentSQL = new DocumentSQL();
				try {
					documentSQL.connect();
					documentSQL.setDocument(document);
				} catch (Exception e) {
					e.printStackTrace();
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Document save failed!');");
					out.println("location='edit_document_page.jsp';");
					out.println("</script>");
					return;
				}

				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				if(document.getSign()){
					out.println("alert('Document signed by "+careProvider.getTitle()+" "+careProvider.getFirstName()+" "+careProvider.getLastName()+"');");
				} else {
					out.println("alert('Document Saved.');");
				}
				out.println("location='edit_document_page.jsp';");
				out.println("</script>");
				
				patientInfo.addDocument(document);
				session.setAttribute("document", document);
				session.setAttribute("patientInfo", patientInfo);
				
				//RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_document_page.jsp");
				//requestDispatcher.forward(request, response);
			}
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
