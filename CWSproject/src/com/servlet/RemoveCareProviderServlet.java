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
		
		String CWSNumber = request.getParameter("CWSNumber");
		
		int icon = -1;
		String iconStr = (String) request.getParameter("icon");
		if(iconStr!=null && !iconStr.equals("")){
			icon = Integer.valueOf(iconStr);
		}
		
		FormTemplateSQL formTemplateSQL = new FormTemplateSQL();
		FormTemplate formTemplate = null;
		try {
			formTemplateSQL.connect();
			int formTemplateId = Integer.valueOf(request.getParameter("formTemplateId"));
			formTemplate= formTemplateSQL.getFormTemplate(formTemplateId);
			formTemplateSQL.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			PrintWriter out = response.getWriter();
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Database connection failed!');");
			out.println("location='patient_info_page.jsp';");
			out.println("</script>");
			return;
		}
		
		patientInfo.setCWSNumber(CWSNumber);
		patientInfo.setIcon(icon);
		patientInfo.setFormTemplate(formTemplate);
		
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
