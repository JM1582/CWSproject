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
		if(document.getDocumentId()==-1){
			DocumentSQL documentSQL = new DocumentSQL();
			document.setDocumentId(documentSQL.fakeGetNewDocumentId());
		}
		
		if(!careProvider.getUserName().equals(document.getAuthor().getUserName())){
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('You can not save, because you are not the author of this document.');");
			out.println("location='edit_document_page.jsp';");
			out.println("</script>");
		} else {
			if(document.getSign()){
				PrintWriter out = response.getWriter();
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Document already signed, can not be edit.');");
				out.println("location='edit_document_page.jsp';");
				out.println("</script>");
			} else {
				document.setDateToday();
				document.setAuthor(careProvider);
				document.setCWSNumber(patientInfo.getCWSNumber());
				Map partMap = document.getFormTemplate().getPartsMap();
				Iterator partIt = partMap.keySet().iterator();
				while(partIt.hasNext()){
					String partId = (String)partIt.next();
					OnePart part = (OnePart)partMap.get(partId);
					String scalarValue[][] = part.getScalarValue();
					Map subSetMap = part.getSubSetMap();
					Iterator subSetIt = subSetMap.keySet().iterator();
					while(subSetIt.hasNext()){
						String subSetId = (String)subSetIt.next();
						SubSet subSet = (SubSet)subSetMap.get(subSetId);
						Map domainMap = subSet.getDomainMap();
						Iterator domainIt = domainMap.keySet().iterator();
						while(domainIt.hasNext()){
							String domainId = (String)domainIt.next();
							String[] domainValue = new String[scalarValue.length];
							for(int i=0;i<scalarValue.length;i++){
								if(request.getParameter(domainId+'_'+Integer.toString(i))!=null){
									domainValue[i] = request.getParameter(domainId+'_'+Integer.toString(i));
								} else {
									domainValue[i] = "no data";
								}
							}
							if (domainValue != null){
								document.addDomainValue(domainId, domainValue);
							}
						}
					}
				}
				if(request.getParameter("sign") != null){
					if(document.getSign()){
						PrintWriter out = response.getWriter();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Document already signed.');");
						out.println("location='edit_document_page.jsp';");
						out.println("</script>");
						
					} else {
						document.setSign(true);
						
						session.setAttribute("document", document);
						PrintWriter out = response.getWriter();
						out.println("<script type=\"text/javascript\">");
						out.println("alert('Document signed by "+careProvider.getTitle()+" "+careProvider.getFirstName()+" "+careProvider.getLastName()+"');");
						out.println("location='edit_document_page.jsp';");
						out.println("</script>");
					}
				} else {
					PrintWriter out = response.getWriter();
					out.println("<script type=\"text/javascript\">");
					out.println("alert('Document Saved.');");
					out.println("location='edit_document_page.jsp';");
					out.println("</script>");
				}
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
