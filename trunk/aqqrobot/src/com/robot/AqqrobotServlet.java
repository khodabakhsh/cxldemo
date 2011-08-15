package com.robot;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class AqqrobotServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("application/vnd.ms-excel");  
		resp.addHeader("Cache-Control",  
                "no-store, no-cache, must-revalidate");  
		resp.addHeader("Cache-Control", "post-check=0, pre-check=0");  
        resp.setHeader("Pragma", "No-cache");  
        resp.setDateHeader("Expires", 0);  
        resp.setHeader("Content-Disposition",  
                "attachment;filename=export.js");  
        
		FileInputStream iStream =  new FileInputStream(new File(this.getClass().getClassLoader().getResource("qqrobot/1.js").getPath()));
		byte [] b = new byte[1024];
		int length = 0;
		while((length = iStream.read(b))!=-1){
			resp.getOutputStream().write(b, 0, length);
		}
		
//		resp.setContentType("text/plain");
//		resp.getWriter().println("Hello, world");
	}
}
