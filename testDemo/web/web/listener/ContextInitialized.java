package web.listener;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public  class ContextInitialized implements ServletContextListener {
  private ServletContext context = null;

  /*This method is invoked when the Web Application has been removed 
  and is no longer able to accept requests
  */

  public void contextDestroyed(ServletContextEvent event)
  {
    //Output a simple message to the server's console
    System.out.println("The Simple Web App. Has Been Removed");
    this.context = null;

  }


  //This method is invoked when the Web Application
  //is ready to service requests

  public void contextInitialized(ServletContextEvent event)
  {
    this.context = event.getServletContext();

    //Output a simple message to the server's console
    System.out.println("The Simple Web App. Is Ready");

  }
}
