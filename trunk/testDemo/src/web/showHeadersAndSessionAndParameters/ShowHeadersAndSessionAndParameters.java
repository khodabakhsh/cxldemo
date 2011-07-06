package web.showHeadersAndSessionAndParameters;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * show request headers ,session info , session contents and parameters
 * @author caixl
 *
 */
public class ShowHeadersAndSessionAndParameters extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        PrintStream out =  System.out;
        
		//show request headers
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			out.println(headerName + "\t" + headerValue);
		}
		// Print session info  
		HttpSession session = request.getSession(true);  
        Date createdDate = new Date(session.getCreationTime());  
        Date lastAccessedTime= new Date(session.getLastAccessedTime());  
        out.println("Jsessionid " + session.getId());//Jsessionid  
        out.println("Created: " + createdDate );  
        out.println("Last Accessed: " + lastAccessedTime);  
        
        // Print session contents          
        Enumeration attributeNames = session.getAttributeNames();  
        while (attributeNames.hasMoreElements()) {  
            String name = (String)attributeNames.nextElement();  
            String value = session.getAttribute(name).toString();  
            out.println(name + " = " + value);  
        }  
        //print parameter values
        Enumeration parameterNames = request.getParameterNames();  
        while(parameterNames.hasMoreElements()) {  
          String str = (String)parameterNames.nextElement();  
          String value = request.getParameter(str);  
          out.println(str + "\t" +  value);    
        }  
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}